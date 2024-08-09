/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

import com.jhlabs.vecmath.Vector3f;

public class AxisAngle4f {
    public float x;
    public float y;
    public float z;
    public float angle;

    public AxisAngle4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public AxisAngle4f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
        this.angle = fArray[2];
    }

    public AxisAngle4f(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.angle = f4;
    }

    public AxisAngle4f(AxisAngle4f axisAngle4f) {
        this.x = axisAngle4f.x;
        this.y = axisAngle4f.y;
        this.z = axisAngle4f.z;
        this.angle = axisAngle4f.angle;
    }

    public AxisAngle4f(Vector3f vector3f, float f) {
        this.x = vector3f.x;
        this.y = vector3f.y;
        this.z = vector3f.z;
        this.angle = f;
    }

    public void set(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.angle = f4;
    }

    public void set(AxisAngle4f axisAngle4f) {
        this.x = axisAngle4f.x;
        this.y = axisAngle4f.y;
        this.z = axisAngle4f.z;
        this.angle = axisAngle4f.angle;
    }

    public void get(AxisAngle4f axisAngle4f) {
        axisAngle4f.x = this.x;
        axisAngle4f.y = this.y;
        axisAngle4f.z = this.z;
        axisAngle4f.angle = this.angle;
    }

    public void get(float[] fArray) {
        fArray[0] = this.x;
        fArray[1] = this.y;
        fArray[2] = this.z;
        fArray[3] = this.angle;
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + "]";
    }
}

