package fr.hypario.raycasting.environment;

import fr.hypario.raycasting.math.Point3D;
import fr.hypario.raycasting.math.Vector3D;

public class Camera {
    private Point3D position = new Point3D(0, 0, -5);
    private Vector3D lookAt = new Vector3D();
    private Vector3D up = new Vector3D(0, 1, 0);
    private double fov = 45;

    private final double maxLength = 582;

    /**
     * @return pixel pixelDim
     */
    public double[] calculatePixelDimension(int imageWidth, int imageHeight) {
        double[] dimensions = new double[2];

        double fovr = this.getFov() * Math.PI / 180;

        dimensions[1] = Math.tan(fovr / 2); // height
        dimensions[0] = dimensions[1] * ((double) imageWidth / (double) imageHeight); // width

        return dimensions;
    }

    /**
     * Calculate an orthonormal coordinate system
     *
     * @return an array containing u,v,w
     */
    public Vector3D[] calculateCoordinateSystem() {
        Vector3D[] ret = new Vector3D[3];

        Vector3D w = this.calculateAxe();
        Vector3D u = this.getUpperEye().cross(w).normalize();
        Vector3D v = w.cross(u).normalize();

        ret[0] = u;
        ret[1] = v;
        ret[2] = w;

        return ret;
    }

    /**
     * Calculate the axe that passes through the eye and the point looked
     *
     * @return a Vector3D
     */
    private Vector3D calculateAxe() {
        Point3D lookFrom = this.getPosition();
        Vector3D lookAt = this.getLookAt();

        return lookFrom.sub(lookAt).normalize();
    }

    public Point3D getPosition() {
        return position;
    }

    public Vector3D getLookAt() {
        return lookAt;
    }

    public Vector3D getUpperEye() {
        return up;
    }

    public double getFov() {
        return fov;
    }

    public void setPosition(Point3D p) {
        this.position = p;
    }

    void setLookAt(Vector3D v) {
        lookAt = v;
    }

    void setUp(Vector3D v) {
        up = v;
    }

    void setFov(double d) {
        this.fov = d;
    }

    public double getMaxLength() {
        return maxLength;
    }
}
