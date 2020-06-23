package fr.hypario.raycasting.math;

public class Triplet {

    private final double x;
    private final double y;
    private final double z;

    Triplet(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Triplet() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public boolean equals(Triplet t) {
        return this.getX() == t.getX() && this.getY() == t.getY() && this.getZ() == t.getZ();
    }

    public boolean equals(double x, double y, double z) {
        return this.getX() == x && this.getY() == y && this.getZ() == z;
    }

}
