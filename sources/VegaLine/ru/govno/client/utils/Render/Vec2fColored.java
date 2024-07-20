/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Render;

public class Vec2fColored {
    public static final Vec2fColored ZERO = new Vec2fColored(0.0f, 0.0f, -1);
    public float x;
    public float y;
    public int color = -1;

    public Vec2fColored(float xIn, float yIn, int color) {
        this.x = xIn;
        this.y = yIn;
        this.color = color;
    }

    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float[] getXY() {
        return new float[]{this.x, this.y};
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public int getColor() {
        return this.color;
    }
}

