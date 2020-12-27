package model;

public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double angle) {
        this.x = Math.cos(angle);
        this.y = Math.sin(angle);
    }

    public Vector(Vector that) {
        this.x = that.getX();
        this.y = that.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void add(Vector vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public void multiplyBy(double d) {
        this.x *= d;
        this.y *= d;
    }

}
