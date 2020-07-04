package fr.hypario.raycasting.math;

public class BoundingBox {

    private final Point3D min;
    private final Point3D max;

    public BoundingBox(Point3D min, Point3D max) {
        this.min = min;
        this.max = max;
    }

    public boolean intersect(Ray ray) {
        Vector3D rayDir = ray.getDirection();
        Point3D origin = ray.getOrigin();
        double tmp;

        /* x */
        double txMin = (this.min.getX() - origin.getX()) / rayDir.getX();
        double txMax = (this.max.getX() - origin.getX()) / rayDir.getX();
        if (txMax < txMin) {
            tmp = txMax;
            txMax = txMin;
            txMin = tmp;
        }

        /* Y */
        double tyMin = (this.min.getY() - origin.getY()) / rayDir.getY();
        double tyMax = (this.max.getY() - origin.getY()) / rayDir.getY();
        if (tyMax < tyMin) {
            tmp = tyMax;
            tyMax = tyMin;
            tyMin = tmp;
        }

        /* Z */
        double tzMin = (this.min.getZ() - origin.getZ()) / rayDir.getZ();
        double tzMax = (this.max.getZ() - origin.getZ()) / rayDir.getZ();
        if (tzMax < tzMin) {
            tmp = tzMax;
            tzMax = tzMin;
            tzMin = tmp;
        }

        double tMin = Math.max(txMin, tyMin); // get greatest min
        double tMax = Math.max(txMax, tyMax); // get smallest max

        if (txMin > tyMax || tyMin > txMax) return false;
        if (tMin > tzMax || tzMin > tMax) return false;

        return true;

        /*
        if (tzMin > tMin) tMin = tzMin; // can be 0 if ray is casted from inside box
        if (tzMax < tMax) tMax = tzMax;
         */
    }
}
