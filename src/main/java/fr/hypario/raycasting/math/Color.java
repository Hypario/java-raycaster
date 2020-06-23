package fr.hypario.raycasting.math;

public class Color extends Triplet {

    public Color() {
        super();
    }

    public Color(double r, double g, double b) {
        super(r, g, b);
    }

    public Color mul(double d) {
        return new Color(this.getX() * d, this.getY() * d, this.getZ() * d);
    }

    public Color add(Color c) {
        return new Color(this.getX() + c.getX(), this.getY() + c.getY(), this.getZ() + c.getZ());
    }

    public Color times(Color c) {
        return new Color(this.getX() * c.getX(), this.getY() * c.getY(), this.getZ() * c.getZ());
    }

    public Integer toInteger() {
        return (int) (this.getX() * 255 + 0.5) << 16 | (int) (this.getY() * 255 + 0.5) << 8 | (int) (this.getZ() * 255 + 0.5);
    }

    public String toString() {
        return "C " + this.getX() + " " + this.getY() + " " + this.getZ();
    }

    public Color cap() {
        double r;
        double g;
        double b;
        r = this.getX() > 1 ? 1 : this.getX();
        g = this.getY() > 1 ? 1 : this.getY();
        b = this.getZ() > 1 ? 1 : this.getZ();
        return new Color(r, g, b);
    }
}
