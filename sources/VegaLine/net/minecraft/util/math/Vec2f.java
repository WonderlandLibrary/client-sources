/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

public class Vec2f {
    public float x;
    public float y;

    public Vec2f(float xIn, float yIn) {
        this.x = xIn;
        this.y = yIn;
    }

    public Vec2f add(float xOut, float yOut) {
        Vec2f vec = this;
        vec.x += xOut;
        vec.y += yOut;
        return vec;
    }
}

