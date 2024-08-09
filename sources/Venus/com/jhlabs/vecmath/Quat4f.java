/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

import com.jhlabs.vecmath.AxisAngle4f;
import com.jhlabs.vecmath.Matrix4f;
import com.jhlabs.vecmath.Tuple4f;

public class Quat4f
extends Tuple4f {
    public Quat4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Quat4f(float[] fArray) {
        this.x = fArray[0];
        this.y = fArray[1];
        this.z = fArray[2];
        this.w = fArray[3];
    }

    public Quat4f(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public Quat4f(Quat4f quat4f) {
        this.x = quat4f.x;
        this.y = quat4f.y;
        this.z = quat4f.z;
        this.w = quat4f.w;
    }

    public Quat4f(Tuple4f tuple4f) {
        this.x = tuple4f.x;
        this.y = tuple4f.y;
        this.z = tuple4f.z;
        this.w = tuple4f.w;
    }

    public void set(AxisAngle4f axisAngle4f) {
        float f = axisAngle4f.angle * 0.5f;
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        this.x = axisAngle4f.x * f3;
        this.y = axisAngle4f.y * f3;
        this.z = axisAngle4f.z * f3;
        this.w = f2;
    }

    public void normalize() {
        float f = 1.0f / (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
        this.x *= f;
        this.y *= f;
        this.z *= f;
        this.w *= f;
    }

    public void set(Matrix4f matrix4f) {
        float f = matrix4f.m00 + matrix4f.m11 + matrix4f.m22;
        if ((double)f > 0.0) {
            float f2 = (float)Math.sqrt(f + 1.0f);
            this.w = f2 / 2.0f;
            f2 = 0.5f / f2;
            this.x = (matrix4f.m12 - matrix4f.m21) * f2;
            this.y = (matrix4f.m20 - matrix4f.m02) * f2;
            this.z = (matrix4f.m01 - matrix4f.m10) * f2;
        } else {
            int n = 0;
            if (matrix4f.m11 > matrix4f.m00) {
                n = 1;
                if (matrix4f.m22 > matrix4f.m11) {
                    n = 2;
                }
            } else if (matrix4f.m22 > matrix4f.m00) {
                n = 2;
            }
            switch (n) {
                case 0: {
                    float f3 = (float)Math.sqrt(matrix4f.m00 - (matrix4f.m11 + matrix4f.m22) + 1.0f);
                    this.x = f3 * 0.5f;
                    if ((double)f3 != 0.0) {
                        f3 = 0.5f / f3;
                    }
                    this.w = (matrix4f.m12 - matrix4f.m21) * f3;
                    this.y = (matrix4f.m01 + matrix4f.m10) * f3;
                    this.z = (matrix4f.m02 + matrix4f.m20) * f3;
                    break;
                }
                case 1: {
                    float f4 = (float)Math.sqrt(matrix4f.m11 - (matrix4f.m22 + matrix4f.m00) + 1.0f);
                    this.y = f4 * 0.5f;
                    if ((double)f4 != 0.0) {
                        f4 = 0.5f / f4;
                    }
                    this.w = (matrix4f.m20 - matrix4f.m02) * f4;
                    this.z = (matrix4f.m12 + matrix4f.m21) * f4;
                    this.x = (matrix4f.m10 + matrix4f.m01) * f4;
                    break;
                }
                case 2: {
                    float f5 = (float)Math.sqrt(matrix4f.m00 - (matrix4f.m11 + matrix4f.m22) + 1.0f);
                    this.z = f5 * 0.5f;
                    if ((double)f5 != 0.0) {
                        f5 = 0.5f / f5;
                    }
                    this.w = (matrix4f.m01 - matrix4f.m10) * f5;
                    this.x = (matrix4f.m20 + matrix4f.m02) * f5;
                    this.y = (matrix4f.m21 + matrix4f.m12) * f5;
                }
            }
        }
    }
}

