package me.superskidder.lune.utils.math;


import me.superskidder.lune.utils.gl.GLUtils;

public final class Vec3f {
    private double x;
    private double y;
    private double z;

    public Vec3f() {
        this(0.0, 0.0, 0.0);
    }

    public Vec3f(Vec3f vec) {
        this(vec.x, vec.y, vec.z);
    }

    public Vec3f(double x2, double y2, double z2) {
        this.x = x2;
        this.y = y2;
        this.z = z2;
    }

    public final Vec3f setX(double x2) {
        this.x = x2;
        return this;
    }

    public final Vec3f setY(double y2) {
        this.y = y2;
        return this;
    }

    public final Vec3f setZ(double z2) {
        this.z = z2;
        return this;
    }

    public final double getX() {
        return this.x;
    }

    public final double getY() {
        return this.y;
    }

    public final double getZ() {
        return this.z;
    }

    public final Vec3f add(Vec3f vec) {
        return this.add(vec.x, vec.y, vec.z);
    }

    public final Vec3f add(double x2, double y2, double z2) {
        return new Vec3f(this.x + x2, this.y + y2, this.z + z2);
    }

    public final Vec3f sub(Vec3f vec) {
        return new Vec3f(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }

    public final Vec3f sub(double x2, double y2, double z2) {
        return new Vec3f(this.x - x2, this.y - y2, this.z - z2);
    }

    public final Vec3f scale(float scale) {
        return new Vec3f(this.x * (double)scale, this.y * (double)scale, this.z * (double)scale);
    }

    public final Vec3f copy() {
        return new Vec3f(this);
    }

    public final Vec3f transfer(Vec3f vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        return this;
    }

    public final double distanceTo(Vec3f vec) {
        double dx2 = this.x - vec.x;
        double dy2 = this.y - vec.y;
        double dz2 = this.z - vec.z;
        return Math.sqrt(dx2 * dx2 + dy2 * dy2 + dz2 * dz2);
    }

    public final Vec2f rotationsTo(Vec3f vec) {
        double[] diff = new double[]{vec.x - this.x, vec.y - this.y, vec.z - this.z};
        double hDist = Math.sqrt(diff[0] * diff[0] + diff[2] * diff[2]);
        return new Vec2f(Math.toDegrees(Math.atan2(diff[2], diff[0])) - 90.0, - Math.toDegrees(Math.atan2(diff[1], hDist)));
    }

    public final Vec3f toScreen() {
        return GLUtils.toScreen(this);
    }

    public String toString() {
        return "Vec3{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
    }
}

