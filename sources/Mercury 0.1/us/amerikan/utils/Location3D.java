/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

public class Location3D {
    double x;
    double y;
    double z;

    public Location3D(double x2, double y2, double z2) {
        this.x = x2;
        this.y = y2;
        this.z = z2;
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

    public void addX(double x2) {
        this.x += x2;
    }

    public void addY(double y2) {
        this.y += y2;
    }

    public void addZ(double z2) {
        this.z += z2;
    }

    public void add(double x2, double y2, double z2) {
        this.y += y2;
        this.x += x2;
        this.z += z2;
    }

    public double distance(Location3D Location2) {
        double a2 = Math.abs(Location2.getY() - this.y);
        double ba2 = Math.abs(Location2.getZ() - this.z);
        double bb2 = Math.abs(Location2.getX() - this.x);
        double b2 = Math.sqrt(ba2 * ba2 + bb2 * bb2);
        return Math.sqrt(a2 * a2 + b2 * b2);
    }

    public String toPointString() {
        return "(" + this.x + " | " + this.y + " | " + this.z + ")";
    }
}

