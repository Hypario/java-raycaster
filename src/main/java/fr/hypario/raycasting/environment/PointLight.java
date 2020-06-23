package fr.hypario.raycasting.environment;

import fr.hypario.raycasting.math.*;

public class PointLight extends Light {

    private Point3D position;

    PointLight(Point3D position, Color col) {
        super(col);
        this.position = position;
    }

    @Override
    protected boolean isHidden(Scene world, Point3D p) {
        if (world.isShadowEnabled()) {
            double distance = this.position.sub(p).length();
            Vector3D lightDir = this.getLightDir(p);
            IntersectedObject intersected = world.intersect(new Ray(p, lightDir), false);
            double res = intersected.t;
            return intersected.hasIntersected && lightDir.mul(res).length() < distance;
        }
        return false;
    }

    @Override
    public Vector3D getLightDir(Point3D p) {
        return this.position.sub(p).normalize();
    }
}
