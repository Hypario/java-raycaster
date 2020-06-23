package fr.hypario.raycasting.environment;

import java.util.ArrayList;
import java.util.List;

import fr.hypario.raycasting.exceptions.InvalidEntryException;

import fr.hypario.raycasting.math.*;

public class Scene {

    private String output;
    private final Integer[] size;
    private Integer maxverts;
    private Color ambient;
    private Color specular;
    private Color diffuse;
    private Integer shininess;

    private final List<Light> lights;
    private final List<BasicObject> props;
    private final List<Point3D> vertexes;
    private boolean shadow;
    private boolean antialiasing;
    private int maxDepth = 1;

    private final Camera camera;

    public Scene() {
        size = new Integer[2];
        this.camera = new Camera();

        this.lights = new ArrayList<>();
        this.props = new ArrayList<>();
        this.vertexes = new ArrayList<>();

        this.ambient = new Color();
        this.specular = new Color();
        this.diffuse = new Color();
        this.shininess = 1;

        this.shadow = false;
        this.antialiasing = false;
    }

    // output of scene
    public void output(String name) {
        this.output = name;
    }

    // size of scene
    public void size(String args) {
        String[] tmp = args.split(" ");
        this.size[0] = Integer.parseInt(tmp[0]);
        this.size[1] = Integer.parseInt(tmp[1]);
    }

    // set up camera
    public void camera(String args) {
        Double[] data = toDouble(args);

        Point3D pos = new Point3D(data[0], data[1], data[2]); // position
        Vector3D rot = new Vector3D(data[3], data[4], data[5]); // where the camera look
        Vector3D up = new Vector3D(data[6], data[7], data[8]); // upper eye direction

        camera.setPosition(pos);
        camera.setLookAt(rot);
        camera.setUp(up);
        camera.setFov(data[9]);
    }

    // set up ambient color
    public void ambient(String args) throws InvalidEntryException {
        Double[] data = toDouble(args);

        if (diffuse != null && (this.diffuse.getX() + data[0] > 1 || this.diffuse.getY() + data[1] > 1 || this.diffuse.getZ() + data[2] > 1)) {
            throw new InvalidEntryException("Invalid entry");
        }

        ambient = new Color(data[0], data[1], data[2]);
    }

    // set up diffuse color
    public void diffuse(String args) throws InvalidEntryException {
        Double[] data = toDouble(args);

        if (ambient != null && (this.ambient.getX() + data[0] > 1 || this.ambient.getY() + data[1] > 1 || this.ambient.getZ() + data[2] > 1)) {
            throw new InvalidEntryException("Invalid entry");
        }

        diffuse = new Color(data[0], data[1], data[2]);
    }

    public void specular(String args) {
        Double[] data = toDouble(args);

        specular = new Color(data[0], data[1], data[2]);
    }

    // set up shininess of light
    public void shininess(String args) {
        String[] number = args.split(" ");
        shininess = Integer.parseInt(number[0]);
    }

    // set up directional light
    public void directional(String args) {
        Double[] data = toDouble(args);

        Vector3D direction = new Vector3D(data[0], data[1], data[2]);
        Color col = new Color(data[3], data[4], data[5]);
        this.lights.add(new DirectionalLight(direction, col));
    }

    // set up point light
    public void point(String args) {
        Double[] data = toDouble(args);

        Point3D pos = new Point3D(data[0], data[1], data[2]);
        Color col = new Color(data[3], data[4], data[5]);
        this.lights.add(new PointLight(pos, col));
    }

    public void maxverts(String args) {
        this.maxverts = Integer.parseInt(args.trim());
    }

    public void vertex(String args) {
        Double[] data = toDouble(args);

        Point3D pos = new Point3D(data[0], data[1], data[2]);
        this.vertexes.add(pos);
    }

    public void tri(String args) {
        String[] number = args.split(" ");
        if (Integer.parseInt(number[0]) < this.maxverts && Integer.parseInt(number[1]) < this.maxverts && Integer.parseInt(number[2]) < this.maxverts) {
            int a = Integer.parseInt(number[0]);
            int b = Integer.parseInt(number[1]);
            int c = Integer.parseInt(number[2]);
            this.props.add(new Triangle(this.vertexes.get(a), this.vertexes.get(b), this.vertexes.get(c), this.diffuse, this.specular, this.shininess));
        }
    }

    // create a sphere
    public void sphere(String args) {
        Double[] data = toDouble(args);

        Point3D pos = new Point3D(data[0], data[1], data[2]);
        this.props.add(new Sphere(pos, data[3], this.diffuse, this.specular, this.shininess));
    }

    public void plane(String args) {
        Double[] data = toDouble(args);

        Point3D q = new Point3D(data[0], data[1], data[2]);
        Vector3D n = new Vector3D(data[3], data[4], data[5]);
        this.props.add(new Plane(q, n, this.diffuse, this.specular, this.shininess));
    }

    public void shadow(String args) {
        this.shadow = Boolean.parseBoolean(args);
    }

    public void antialiasing(String args) {
        this.antialiasing = Boolean.parseBoolean(args);
    }

    public void maxdepth(String args) {
        this.maxDepth = Integer.parseInt(args);
    }

    private Double[] toDouble(String args) {
        String[] number = args.split("\\s+");
        Double[] res = new Double[args.length() / 2 + 1];
        for (int i = 0; i < number.length; i++)
            res[i] = Double.parseDouble(number[i]);
        return res;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public IntersectedObject intersect(Ray ray, boolean closest) {
        IntersectedObject intersectedObject = new IntersectedObject();
        for (BasicObject object : this.getProps()) {
            Double t = object.hit(ray, 0.0001, Double.POSITIVE_INFINITY);

            if (t != null && (t < intersectedObject.t || !intersectedObject.hasIntersected)) {
                intersectedObject.object = object;
                intersectedObject.t = t;
                intersectedObject.hasIntersected = true;
                if (!closest) return intersectedObject;
            }
        }
        return intersectedObject;
    }

    public List<BasicObject> getProps() {
        return this.props;
    }

    public String getOutput() {
        return this.output;
    }

    public Integer[] getSize() {
        return this.size;
    }

    public Integer getMaxverts() {
        return this.maxverts;
    }

    public List<Light> getLights() {
        return this.lights;
    }

    public Color getAmbient() {
        return ambient;
    }

    public List<Point3D> getVertexes() {
        return this.vertexes;
    }

    public int getMaxDepth() {
        return this.maxDepth;
    }

    public boolean isShadowEnabled() {
        return shadow;
    }

    public boolean isAntialiasingEnabled() {
        return antialiasing;
    }
}
