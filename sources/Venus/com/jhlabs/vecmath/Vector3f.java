/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

import com.jhlabs.vecmath.Tuple3f;

public class Vector3f
extends Tuple3f {
    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
    }

    public Vector3f(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public Vector3f(Vector3f vector3f) {
        this.x = vector3f.x;
        this.y = vector3f.y;
        this.z = vector3f.z;
    }

    public Vector3f(Tuple3f tuple3f) {
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }

    public float angle(Vector3f vector3f) {
        return (float)Math.acos(this.dot(vector3f) / (this.length() * vector3f.length()));
    }

    public float dot(Vector3f vector3f) {
        return vector3f.x * this.x + vector3f.y * this.y + vector3f.z * this.z;
    }

    public void cross(Vector3f vector3f, Vector3f vector3f2) {
        this.x = vector3f.y * vector3f2.z - vector3f.z * vector3f2.y;
        this.y = vector3f.z * vector3f2.x - vector3f.x * vector3f2.z;
        this.z = vector3f.x * vector3f2.y - vector3f.y * vector3f2.x;
    }

    public float length() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public void normalize() {
        float f = 1.0f / (float)Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        this.x *= f;
        this.y *= f;
        this.z *= f;
    }
}

