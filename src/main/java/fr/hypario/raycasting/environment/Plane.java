package fr.hypario.raycasting.environment;

import fr.hypario.raycasting.math.Color;
import fr.hypario.raycasting.math.Point3D;
import fr.hypario.raycasting.math.Ray;
import fr.hypario.raycasting.math.Vector3D;

public class Plane extends BasicObject {

    private Point3D q;

    Plane(Point3D q, Vector3D normal, Color color, Color specular, int shininess) {
        this.q = q;
        this.normal = normal;
        this.diffuse = color;
        this.specular = specular;
        this.shininess = shininess;
        this.id = ++counter;
    }

    @Override
    public Double hit(Ray ray, double t_min, double t_max) {
        double factor = ray.getDirection().dot(this.getNormal());
        if (factor == 0) return null;

        double t = this.q.sub(ray.getOrigin()).dot(this.getNormal()) / factor;
        return t < t_max && t > t_min ? t : null;
    }

    @Override
    public Vector3D getNormal() {
        return this.normal;
    }
}
