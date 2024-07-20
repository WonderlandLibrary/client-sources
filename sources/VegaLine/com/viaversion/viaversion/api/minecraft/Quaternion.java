/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

public final class Quaternion {
    private final float x;
    private final float y;
    private final float z;
    private final float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float x() {
        return this.x;
    }

    public float y() {
        return this.y;
    }

    public float z() {
        return this.z;
    }

    public float w() {
        return this.w;
    }

    public String toString() {
        return "Quaternion{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", w=" + this.w + '}';
    }
}

