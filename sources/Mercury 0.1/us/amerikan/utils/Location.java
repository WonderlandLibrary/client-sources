/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

public class Location {
    public static double x;
    public static double y;
    public static double z;

    public Location(double x2, double y2, double z2) {
        x2 = x;
        y2 = y;
        z2 = z;
    }

    public void offset(double xoff, double yoff, double zoff) {
        x += xoff;
        y += yoff;
        z += zoff;
    }

    public double getX() {
        return x;
    }

    public void setX(double x2) {
        x2 = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y2) {
        y2 = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z2) {
        z2 = z;
    }
}

