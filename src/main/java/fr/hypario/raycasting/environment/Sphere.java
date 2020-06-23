package fr.hypario.raycasting.environment;

import fr.hypario.raycasting.math.Color;
import fr.hypario.raycasting.math.Point3D;
import fr.hypario.raycasting.math.Ray;
import fr.hypario.raycasting.math.Vector3D;

public class Sphere extends BasicObject {
    private final Point3D center;
    private final double radius;

    Sphere(Point3D ce, double r, Color diffuse, Color specular, int shininess) {
        this.center = ce;
        this.radius = r;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
        this.id = ++counter;
    }

    public Double hit(Ray ray, double t_min, double t_max) {
        Vector3D oc = ray.getOrigin().sub(center);
        double a = ray.getDirection().lengthSquared();
        double halfB = oc.dot(ray.getDirection());
        double c = oc.lengthSquared() - radius * radius;

        double discriminant = halfB * halfB - a * c;

        if (discriminant > 0) {
            double t1 = (-halfB + Math.sqrt(discriminant)) / a;
            double t2 = (-halfB - Math.sqrt(discriminant)) / a;

            if (t2 < t_max && t2 > t_min) {
                Vector3D outward_normal = ray.at(t2).sub(center).mul(1 / radius);
                this.setFaceNormal(ray, outward_normal);
                return t2;
            } else if (t1 < t_max && t1 > t_min) {
                Vector3D outward_normal = ray.at(t2).sub(center).mul(1 / radius);
                this.setFaceNormal(ray, outward_normal);
                return t1;
            }
        }
        return null;
    }

    @Override
    public Vector3D getNormal() {
        return this.normal.normalize();
    }
}
