package me.superskidder.lune.utils.math;

public class Vec4f {
    public float x;
    public float y;
    public float w;
    public float h;

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getW() {
        return this.w;
    }

    public float getH() {
        return this.h;
    }

    public void setX(float x2) {
        this.x = x2;
    }

    public void setY(float y2) {
        this.y = y2;
    }

    public void setW(float w2) {
        this.w = w2;
    }

    public void setH(float h2) {
        this.h = h2;
    }

    public Vec4f(float x2, float y2, float w2, float h2) {
        this.x = x2;
        this.y = y2;
        this.w = w2;
        this.h = h2;
    }
}

