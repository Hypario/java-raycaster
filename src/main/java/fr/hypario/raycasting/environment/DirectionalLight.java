package fr.hypario.raycasting.environment;

import fr.hypario.raycasting.math.Color;
import fr.hypario.raycasting.math.Point3D;
import fr.hypario.raycasting.math.Vector3D;

public class DirectionalLight extends Light {

    // light direction
    private Vector3D direction;

    DirectionalLight(Vector3D direction, Color col) {
        super(col);
        this.direction = direction;
    }

    @Override
    public Vector3D getLightDir(Point3D p) {
        return this.direction.normalize();
    }

}
