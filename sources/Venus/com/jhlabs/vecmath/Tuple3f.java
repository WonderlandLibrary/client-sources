/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

public class Tuple3f {
    public float x;
    public float y;
    public float z;

    public Tuple3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Tuple3f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
    }

    public Tuple3f(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public Tuple3f(Tuple3f tuple3f) {
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }

    public void absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
    }

    public void absolute(Tuple3f tuple3f) {
        this.x = Math.abs(tuple3f.x);
        this.y = Math.abs(tuple3f.y);
        this.z = Math.abs(tuple3f.z);
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
    }

    public void set(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public void set(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
    }

    public void set(Tuple3f tuple3f) {
        this.x = tuple3f.x;
        this.y = tuple3f.y;
        this.z = tuple3f.z;
    }

    public void get(Tuple3f tuple3f) {
        tuple3f.x = this.x;
        tuple3f.y = this.y;
        tuple3f.z = this.z;
    }

    public void get(float[] fArray) {
        fArray[0] = this.x;
        fArray[1] = this.y;
        fArray[2] = this.z;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public void negate(Tuple3f tuple3f) {
        this.x = -tuple3f.x;
        this.y = -tuple3f.y;
        this.z = -tuple3f.z;
    }

    public void interpolate(Tuple3f tuple3f, float f) {
        float f2 = 1.0f - f;
        this.x = f2 * this.x + f * tuple3f.x;
        this.y = f2 * this.y + f * tuple3f.y;
        this.z = f2 * this.z + f * tuple3f.z;
    }

    public void scale(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
    }

    public void add(Tuple3f tuple3f) {
        this.x += tuple3f.x;
        this.y += tuple3f.y;
        this.z += tuple3f.z;
    }

    public void add(Tuple3f tuple3f, Tuple3f tuple3f2) {
        this.x = tuple3f.x + tuple3f2.x;
        this.y = tuple3f.y + tuple3f2.y;
        this.z = tuple3f.z + tuple3f2.z;
    }

    public void sub(Tuple3f tuple3f) {
        this.x -= tuple3f.x;
        this.y -= tuple3f.y;
        this.z -= tuple3f.z;
    }

    public void sub(Tuple3f tuple3f, Tuple3f tuple3f2) {
        this.x = tuple3f.x - tuple3f2.x;
        this.y = tuple3f.y - tuple3f2.y;
        this.z = tuple3f.z - tuple3f2.z;
    }

    public void scaleAdd(float f, Tuple3f tuple3f) {
        this.x += f * tuple3f.x;
        this.y += f * tuple3f.y;
        this.z += f * tuple3f.z;
    }

    public void scaleAdd(float f, Tuple3f tuple3f, Tuple3f tuple3f2) {
        this.x = f * tuple3f.x + tuple3f2.x;
        this.y = f * tuple3f.y + tuple3f2.y;
        this.z = f * tuple3f.z + tuple3f2.z;
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }
}

