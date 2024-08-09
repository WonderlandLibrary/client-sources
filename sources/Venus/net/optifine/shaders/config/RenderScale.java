/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.config;

public class RenderScale {
    private float scale = 1.0f;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;

    public RenderScale(float f, float f2, float f3) {
        this.scale = f;
        this.offsetX = f2;
        this.offsetY = f3;
    }

    public float getScale() {
        return this.scale;
    }

    public float getOffsetX() {
        return this.offsetX;
    }

    public float getOffsetY() {
        return this.offsetY;
    }

    public String toString() {
        return this.scale + ", " + this.offsetX + ", " + this.offsetY;
    }
}

