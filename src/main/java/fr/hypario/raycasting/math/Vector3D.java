package fr.hypario.raycasting.math;

public class Vector3D extends Triplet {
    public Vector3D() {
        super();
    }

    public Vector3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector3D add(Vector3D v) {
        return new Vector3D(this.getX() + v.getX(), this.getY() + v.getY(), this.getZ() + v.getZ());
    }

    public Point3D add(Point3D p) {
        return new Point3D(this.getX() + p.getX(), this.getY() + p.getY(), this.getZ() + p.getZ());
    }

    public Vector3D sub(Vector3D v) {
        return new Vector3D(this.getX() - v.getX(), this.getY() - v.getY(), this.getZ() - v.getZ());
    }

    public Vector3D mul(double d) {
        return new Vector3D(this.getX() * d, this.getY() * d, this.getZ() * d);
    }

    public Vector3D div(Vector3D v) {
        return new Vector3D(this.getX() / v.getX(), this.getY() / v.getY(), this.getZ() / v.getZ());
    }

    public boolean collinear(Vector3D v) {
        return this.cross(v).equals(new Vector3D(0, 0, 0));
    }

    public double dot(Vector3D v) {
        return this.getX() * v.getX() + this.getY() * v.getY() + this.getZ() * v.getZ();
    }

    public Vector3D cross(Vector3D v) {
        return new Vector3D(this.getY() * v.getZ() - this.getZ() * v.getY(), this.getZ() * v.getX() - this.getX() * v.getZ(), this.getX() * v.getY() - this.getY() * v.getX());
    }

    public boolean equals(Vector3D v) {
        return this.getX() == v.getX() && this.getY() == v.getY() && this.getZ() == v.getZ();
    }

    public Vector3D normalize() {
        double length = this.length();
        return new Vector3D(this.getX() / length, this.getY() / length, this.getZ() / length);
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared() {
        return this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ();
    }

    @Override
    public String toString() {
        return "V " + this.getX() + " " + this.getY() + " " + this.getZ();
    }

}
