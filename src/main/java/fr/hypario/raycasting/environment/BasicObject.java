package fr.hypario.raycasting.environment;

import fr.hypario.raycasting.math.Color;
import fr.hypario.raycasting.math.Ray;
import fr.hypario.raycasting.math.Vector3D;

public abstract class BasicObject {

    protected Color diffuse; // color of an object when illuminated by ambient light
    protected Color specular; // color of the light from a shiny surface
    protected int shininess; // how much it shines

    public int id;
    public static int counter = 0;

    protected Vector3D normal;

    public abstract Double hit(Ray ray, double t_min, double t_max);

    public void setFaceNormal(Ray ray, Vector3D outward_normal) {
        boolean front_face = ray.getDirection().dot(outward_normal) < 0;
        this.normal = front_face ? outward_normal : outward_normal.mul(-1);
    }

    /**
     * get normal
     * @return the normal
     */
    public abstract Vector3D getNormal();

    public Color getDiffuse() {
        return this.diffuse;
    }

    public Color getSpecular() {
        return this.specular;
    }

    public int getShininess() {
        return this.shininess;
    }
}
