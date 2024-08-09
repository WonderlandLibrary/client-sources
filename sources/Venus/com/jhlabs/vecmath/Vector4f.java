/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

import com.jhlabs.vecmath.Tuple4f;

public class Vector4f
extends Tuple4f {
    public Vector4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Vector4f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
        this.w = fArray[2];
    }

    public Vector4f(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public Vector4f(Vector4f vector4f) {
        this.x = vector4f.x;
        this.y = vector4f.y;
        this.z = vector4f.z;
        this.w = vector4f.w;
    }

    public Vector4f(Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }

    public float dot(Vector4f vector4f) {
        return vector4f.x * this.x + vector4f.y * this.y + vector4f.z * this.z + vector4f.w * this.w;
    }

    public float length() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
    }

    public void normalize() {
        float f = 1.0f / (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
        this.x *= f;
        this.y *= f;
        this.z *= f;
        this.w *= f;
    }
}

