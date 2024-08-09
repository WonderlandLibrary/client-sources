/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

import com.jhlabs.vecmath.Tuple3f;

public class Point3f
extends Tuple3f {
    public Point3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Point3f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
    }

    public Point3f(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public Point3f(Point3f point3f) {
        this.x = point3f.x;
        this.y = point3f.y;
        this.z = point3f.z;
    }

    public Point3f(Tuple3f tuple3f) {
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }

    public float distanceL1(Point3f point3f) {
        return Math.abs(this.x - point3f.x) + Math.abs(this.y - point3f.y) + Math.abs(this.z - point3f.z);
    }

    public float distanceSquared(Point3f point3f) {
        float f = this.x - point3f.x;
        float f2 = this.y - point3f.y;
        float f3 = this.z - point3f.z;
        return f * f + f2 * f2 + f3 * f3;
    }

    public float distance(Point3f point3f) {
        float f = this.x - point3f.x;
        float f2 = this.y - point3f.y;
        float f3 = this.z - point3f.z;
        return (float)Math.sqrt(f * f + f2 * f2 + f3 * f3);
    }
}

