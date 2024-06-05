package me.superskidder.lune.utils.math;


import me.superskidder.lune.utils.gl.GLUtils;

public final class Vec2f {
    private float x;
    private float y;

    public Vec2f() {
        this(0.0f, 0.0f);
    }

    public Vec2f(Vec2f vec) {
        this(vec.x, vec.y);
    }

    public Vec2f(double x2, double y2) {
        this((float)x2, (float)y2);
    }

    public Vec2f(float x2, float y2) {
        this.x = x2;
        this.y = y2;
    }

    public final Vec2f setX(float x2) {
        this.x = x2;
        return this;
    }

    public final Vec2f setY(float y2) {
        this.y = y2;
        return this;
    }

    public final float getX() {
        return this.x;
    }

    public final float getY() {
        return this.y;
    }

    public final Vec2f add(Vec2f vec) {
        return new Vec2f(this.x + vec.x, this.y + vec.y);
    }

    public final Vec2f add(double x2, double y2) {
        return this.add(new Vec2f(x2, y2));
    }

    public final Vec2f add(float x2, float y2) {
        return this.add(new Vec2f(x2, y2));
    }

    public final Vec2f sub(Vec2f vec) {
        return new Vec2f(this.x - vec.x, this.y - vec.y);
    }

    public final Vec2f sub(double x2, double y2) {
        return this.sub(new Vec2f(x2, y2));
    }

    public final Vec2f sub(float x2, float y2) {
        return this.sub(new Vec2f(x2, y2));
    }

    public final Vec2f scale(float scale) {
        return new Vec2f(this.x * scale, this.y * scale);
    }

    public final Vec3f toVec3() {
        return new Vec3f(this.x, this.y, 0.0);
    }

    public final Vec2f copy() {
        return new Vec2f(this);
    }

    public final Vec2f transfer(Vec2f vec) {
        this.x = vec.x;
        this.y = vec.y;
        return this;
    }

    public final float distanceTo(Vec2f vec) {
        double dx2 = this.x - vec.x;
        double dy2 = this.y - vec.y;
        return (float)Math.sqrt(dx2 * dx2 + dy2 * dy2);
    }

    public final Vec3f toScreen() {
        return GLUtils.toWorld(this.toVec3());
    }

    public String toString() {
        return "Vec2{x=" + this.x + ", y=" + this.y + '}';
    }
}

