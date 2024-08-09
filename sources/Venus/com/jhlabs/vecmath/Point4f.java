/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

import com.jhlabs.vecmath.Tuple4f;

public class Point4f
extends Tuple4f {
    public Point4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Point4f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
        this.w = fArray[3];
    }

    public Point4f(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public Point4f(Point4f point4f) {
        this.x = point4f.x;
        this.y = point4f.y;
        this.z = point4f.z;
        this.w = point4f.w;
    }

    public Point4f(Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }

    public float distanceL1(Point4f point4f) {
        return Math.abs(this.x - point4f.x) + Math.abs(this.y - point4f.y) + Math.abs(this.z - point4f.z) + Math.abs(this.w - point4f.w);
    }

    public float distanceSquared(Point4f point4f) {
        float f = this.x - point4f.x;
        float f2 = this.y - point4f.y;
        float f3 = this.z - point4f.z;
        float f4 = this.w - point4f.w;
        return f * f + f2 * f2 + f3 * f3 + f4 * f4;
    }

    public float distance(Point4f point4f) {
        float f = this.x - point4f.x;
        float f2 = this.y - point4f.y;
        float f3 = this.z - point4f.z;
        float f4 = this.w - point4f.w;
        return (float)Math.sqrt(f * f + f2 * f2 + f3 * f3 + f4 * f4);
    }
}

