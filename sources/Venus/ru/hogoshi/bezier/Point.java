/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.hogoshi.bezier;

public class Point {
    private double x;
    private double y;

    public Point(double d, double d2) {
        this.setX(d);
        this.setY(d2);
    }

    public Point(Point point) {
        this.setX(point.getX());
        this.setY(point.getY());
    }

    public Point copy() {
        return new Point(this);
    }

    public Point move(double d, double d2) {
        this.setX(d);
        this.setY(d2);
        return this;
    }

    public Point scale(double d, double d2) {
        this.setX(this.getX() * d);
        this.setY(this.getY() * d2);
        return this;
    }

    public Point scale(double d) {
        this.setX(this.getX() * d);
        this.setY(this.getY() * d);
        return this;
    }

    public Point add(double d, double d2) {
        this.setX(this.getX() + d);
        this.setY(this.getY() + d2);
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

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double d) {
        this.x = d;
    }

    public void setY(double d) {
        this.y = d;
    }
}

