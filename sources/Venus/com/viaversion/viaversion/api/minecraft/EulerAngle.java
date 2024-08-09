/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.minecraft;

public class EulerAngle {
    private final float x;
    private final float y;
    private final float z;

    public EulerAngle(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
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

    @Deprecated
    public float getX() {
        return this.x;
    }

    @Deprecated
    public float getY() {
        return this.y;
    }

    @Deprecated
    public float getZ() {
        return this.z;
    }
}

