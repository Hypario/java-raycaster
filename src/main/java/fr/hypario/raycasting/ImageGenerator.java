package fr.hypario.raycasting;

import fr.hypario.raycasting.environment.*;

import fr.hypario.raycasting.math.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGenerator {

    private final String sceneFile;

    private int image_width;
    private int image_height;

    private Scene world;
    private Vector3D[] system;
    private Point3D origin;
    private double[] pixelDimension;

    private int samples_per_pixel = 3;

    ImageGenerator(String sceneFile) {
        this.sceneFile = sceneFile;
    }

    public void generate() throws IOException {
        SceneReader reader = new SceneReader(this.sceneFile);
        System.err.println("Starting reading scene");
        this.world = reader.read();

        System.err.println("Scene reading over.");

        this.image_width = world.getSize()[0]; // the scene shouldn't have a size
        this.image_height = world.getSize()[1]; // later it will be moved to camera

        this.samples_per_pixel = this.world.isAntialiasingEnabled() ? 100 : 1;

        BufferedImage render = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_RGB);

        Camera camera = world.getCamera();
        system = camera.calculateCoordinateSystem();
        pixelDimension = camera.calculatePixelDimension(image_width, image_height);
        origin = camera.getPosition();

        for (int y = 0; y < image_height; y++) {
            for (int x = 0; x < image_width; x++) {
                Color pixelColor = calculatePixelColor(x, y);
                this.writeColor(x, this.image_height - y - 1, render, pixelColor);
            }
        }

        // write the image
        ImageIO.write(render, "PNG", new File(world.getOutput()));
    }

    private void writeColor(int x, int y, BufferedImage render, Color color) {

        double scale = 1.0 / samples_per_pixel;

        double r = color.getX();
        double g = color.getY();
        double b = color.getZ();

        r *= scale;
        g *= scale;
        b *= scale;

        // it is useful because we need to round
        java.awt.Color javaColor = new java.awt.Color((float) r, (float) g, (float) b);

        render.setRGB(x, y, javaColor.getRGB());
    }

    private Color calculatePixelColor(int x, int y) {
        Color pixelColor = new Color();
        for (int s = 0; s < samples_per_pixel; s++) {
            Vector3D d = this.calculateDVector(new Vector3D(x, y, 1), this.pixelDimension, this.system);
            Ray ray = new Ray(this.origin, d);

            IntersectedObject intersectedObject = this.world.intersect(ray, true);
            if (intersectedObject.hasIntersected) {
                pixelColor = pixelColor.add(this.rayColor(ray, intersectedObject, 0));
            }
        }
        if (pixelColor.equals(0 ,0, 0)) {
            return this.background(x, y).cap();
        }
        return pixelColor.cap();
    }

    private Color rayColor(Ray ray, IntersectedObject intersectedObject, int depth) {
        if (this.world.getMaxDepth() == depth) return new Color();

        Point3D p = ray.at(intersectedObject.t);
        Vector3D direction = ray.getDirection();
        BasicObject object = intersectedObject.object;

        // debugging tool
        if (this.world.displayNormals()) {
            Vector3D n = object.getNormal();
            return new Color(n.getX(), n.getY(), n.getZ()).add(new Color(1, 1, 1)).mul(0.5);
        } else if (this.world.areLightsEnabled()) {
            return object.getDiffuse();
        }

        Color sum = new Color();

        for (Light light : this.world.getLights()) {
            sum = sum.add(light.getColor(this.world, p, direction.mul(-1), object));
        }

        Color result = this.world.getAmbient().add(sum);

        if (!object.getSpecular().equals(0, 0, 0)) {
            // calculate reflected ray with recursive call
            // r = direction + 2 * (normal . -direction) * normal
            Vector3D r = direction.add(
                    object.getNormal().mul(
                            2 * object.getNormal().dot(direction.mul(-1))
                    )
            ).normalize();

            IntersectedObject intersected = this.world.intersect(new Ray(p, r), true);
            if (intersected.hasIntersected) {
                Color cp = this.rayColor(new Ray(p, r), intersected, ++depth); // c'
                result = result.add(object.getSpecular().times(cp)); // c = c + specular * c'
            }
        }
        return result;
    }

    private Color background(int x, int y) {
        Vector3D unit_direction = this.calculateDVector(new Vector3D(x, y, 1), this.pixelDimension, this.system).normalize();
        double t = 0.5 * (unit_direction.getY() + 1.0);
        return new Color(1.0, 1.0, 1.0).mul(1.0 - t).add(new Color(0.5, 0.7, 1.0).mul(t));
    }

    /**
     * Calculate the director vector that goes from the eye to the pixel
     *
     * @param ij vector going to the pixel
     * @return the director vector that goes from the eye to the pixel
     */
    private Vector3D calculateDVector(Vector3D ij, double[] pixelDim, Vector3D[] system) {
        double pixelwidth = pixelDim[0];
        double pixelHeight = pixelDim[1];

        double na = pixelwidth / (this.image_width / 2.0);
        double nb = pixelHeight / (this.image_height / 2.0);

        double a = na * (ij.getX() - (this.image_width / 2.0) + (this.world.isAntialiasingEnabled() ? Math.random() : 0.5));
        double b = nb * (ij.getY() - (this.image_height / 2.0) + (this.world.isAntialiasingEnabled() ? Math.random() : 0.5));

        Vector3D u = system[0];
        Vector3D v = system[1];
        Vector3D w = system[2];

        return u.mul(a).add(v.mul(b)).sub(w).normalize();
    }

}
