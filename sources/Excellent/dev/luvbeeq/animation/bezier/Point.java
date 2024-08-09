package dev.luvbeeq.animation.bezier;

import lombok.Getter;

@Getter
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public Point(Point point) {
        this.setX(point.getX());
        this.setY(point.getY());
    }

    public Point copy() {
        return new Point(this);
    }

    public Point move(double x, double y) {
        this.setX(x);
        this.setY(y);
        return this;
    }

    public Point scale(double x, double y) {
        this.setX(this.getX() * x);
        this.setY(this.getY() * y);
        return this;
    }

    public Point scale(double scale) {
        this.setX(this.getX() * scale);
        this.setY(this.getY() * scale);
        return this;
    }

    public Point add(double x, double y) {
        this.setX(this.getX() + x);
        this.setY(this.getY() + y);
        return this;
    }

    public Point set(Point point) {
        this.setX(point.getX());
        this.setY(point.getY());
        return this;
    }

    public Point add(Point point) {
        this.setX(this.getX() + point.getX());
        this.setY(this.getY() + point.getY());
        return this;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
 