/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

public class Tuple4f {
    public float x;
    public float y;
    public float z;
    public float w;

    public Tuple4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Tuple4f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
        this.w = fArray[2];
    }

    public Tuple4f(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public Tuple4f(Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }

    public void absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
        this.w = Math.abs(this.w);
    }

    public void absolute(Tuple4f tuple4f) {
        this.x = Math.abs(tuple4f.x);
        this.y = Math.abs(tuple4f.y);
        this.z = Math.abs(tuple4f.z);
        this.w = Math.abs(tuple4f.w);
    }

    public void clamp(float f, float f2) {
        if (this.x < f) {
            this.x = f;
        } else if (this.x > f2) {
            this.x = f2;
        }
        if (this.y < f) {
            this.y = f;
        } else if (this.y > f2) {
            this.y = f2;
        }
        if (this.z < f) {
            this.z = f;
        } else if (this.z > f2) {
            this.z = f2;
        }
        if (this.w < f) {
            this.w = f;
        } else if (this.w > f2) {
            this.w = f2;
        }
    }

    public void set(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public void set(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
        this.w = fArray[2];
    }

    public void set(Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }

    public void get(Tuple4f tuple4f) {
        tuple4f.x = this.x;
        tuple4f.y = this.y;
        tuple4f.z = this.z;
        tuple4f.w = this.w;
    }

    public void get(float[] fArray) {
        fArray[0] = this.x;
        fArray[1] = this.y;
        fArray[2] = this.z;
        fArray[3] = this.w;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        this.w = -this.w;
    }

    public void negate(Tuple4f tuple4f) {
        this.x = -tuple4f.x;
        this.y = -tuple4f.y;
        this.z = -tuple4f.z;
        this.w = -tuple4f.w;
    }

    public void interpolate(Tuple4f tuple4f, float f) {
        float f2 = 1.0f - f;
        this.x = f2 * this.x + f * tuple4f.x;
        this.y = f2 * this.y + f * tuple4f.y;
        this.z = f2 * this.z + f * tuple4f.z;
        this.w = f2 * this.w + f * tuple4f.w;
    }

    public void scale(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
        this.w *= f;
    }

    public void add(Tuple4f tuple4f) {
        this.x += tuple4f.x;
        this.y += tuple4f.y;
        this.z += tuple4f.z;
        this.w += tuple4f.w;
    }

    public void add(Tuple4f tuple4f, Tuple4f tuple4f2) {
        this.x = tuple4f.x + tuple4f2.x;
        this.y = tuple4f.y + tuple4f2.y;
        this.z = tuple4f.z + tuple4f2.z;
        this.w = tuple4f.w + tuple4f2.w;
    }

    public void sub(Tuple4f tuple4f) {
        this.x -= tuple4f.x;
        this.y -= tuple4f.y;
        this.z -= tuple4f.z;
        this.w -= tuple4f.w;
    }

    public void sub(Tuple4f tuple4f, Tuple4f tuple4f2) {
        this.x = tuple4f.x - tuple4f2.x;
        this.y = tuple4f.y - tuple4f2.y;
        this.z = tuple4f.z - tuple4f2.z;
        this.w = tuple4f.w - tuple4f2.w;
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + "]";
    }
}

