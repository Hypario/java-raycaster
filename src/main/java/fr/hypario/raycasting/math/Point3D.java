package fr.hypario.raycasting.math;

public class Point3D extends Triplet {
    public Point3D() {
        super();
    }

    public Point3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Point3D add(Vector3D v) {
        return new Point3D(this.getX() + v.getX(), this.getY() + v.getY(), this.getZ() + v.getZ());
    }

    public Vector3D sub(Point3D p) {
        return new Vector3D(this.getX() - p.getX(), this.getY() - p.getY(), this.getZ() - p.getZ());
    }

    public Vector3D sub(Vector3D v) {
        return new Vector3D(this.getX() - v.getX(), this.getY() - v.getY(), this.getZ() - v.getZ());
    }

    public Vector3D sub(double x, double y, double z) {
        return new Vector3D(this.getX() - x, this.getY() - y, this.getZ() - z);
    }

    public Point3D mul(double d) {
        return new Point3D(this.getX() * d, this.getY() * d, this.getZ() * d);
    }

    public double length(Point3D p) {
        return Math.sqrt((p.getX() - this.getX()) * (p.getX() - this.getX()) + (p.getY() - this.getY()) * (p.getY() - this.getY()) + (p.getX() - this.getX()) * (p.getX() - this.getX()));
    }

    @Override
    public String toString() {
        return "P " + this.getX() + " " + this.getY() + " " + this.getZ();
    }
}
