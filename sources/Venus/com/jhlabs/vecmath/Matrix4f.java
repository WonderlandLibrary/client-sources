/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.vecmath;

import com.jhlabs.vecmath.AxisAngle4f;
import com.jhlabs.vecmath.Point3f;
import com.jhlabs.vecmath.Quat4f;
import com.jhlabs.vecmath.Vector3f;

public class Matrix4f {
    public float m00;
    public float m01;
    public float m02;
    public float m03;
    public float m10;
    public float m11;
    public float m12;
    public float m13;
    public float m20;
    public float m21;
    public float m22;
    public float m23;
    public float m30;
    public float m31;
    public float m32;
    public float m33;

    public Matrix4f() {
        this.setIdentity();
    }

    public Matrix4f(Matrix4f matrix4f) {
        this.set(matrix4f);
    }

    public Matrix4f(float[] fArray) {
        this.set(fArray);
    }

    public void set(Matrix4f matrix4f) {
        this.m00 = matrix4f.m00;
        this.m01 = matrix4f.m01;
        this.m02 = matrix4f.m02;
        this.m03 = matrix4f.m03;
        this.m10 = matrix4f.m10;
        this.m11 = matrix4f.m11;
        this.m12 = matrix4f.m12;
        this.m13 = matrix4f.m13;
        this.m20 = matrix4f.m20;
        this.m21 = matrix4f.m21;
        this.m22 = matrix4f.m22;
        this.m23 = matrix4f.m23;
        this.m30 = matrix4f.m30;
        this.m31 = matrix4f.m31;
        this.m32 = matrix4f.m32;
        this.m33 = matrix4f.m33;
    }

    public void set(float[] fArray) {
        this.m00 = fArray[0];
        this.m01 = fArray[1];
        this.m02 = fArray[2];
        this.m03 = fArray[3];
        this.m10 = fArray[4];
        this.m11 = fArray[5];
        this.m12 = fArray[6];
        this.m13 = fArray[7];
        this.m20 = fArray[8];
        this.m21 = fArray[9];
        this.m22 = fArray[10];
        this.m23 = fArray[11];
        this.m30 = fArray[12];
        this.m31 = fArray[13];
        this.m32 = fArray[14];
        this.m33 = fArray[15];
    }

    public void get(Matrix4f matrix4f) {
        matrix4f.m00 = this.m00;
        matrix4f.m01 = this.m01;
        matrix4f.m02 = this.m02;
        matrix4f.m03 = this.m03;
        matrix4f.m10 = this.m10;
        matrix4f.m11 = this.m11;
        matrix4f.m12 = this.m12;
        matrix4f.m13 = this.m13;
        matrix4f.m20 = this.m20;
        matrix4f.m21 = this.m21;
        matrix4f.m22 = this.m22;
        matrix4f.m23 = this.m23;
        matrix4f.m30 = this.m30;
        matrix4f.m31 = this.m31;
        matrix4f.m32 = this.m32;
        matrix4f.m33 = this.m33;
    }

    public void get(float[] fArray) {
        fArray[0] = this.m00;
        fArray[1] = this.m01;
        fArray[2] = this.m02;
        fArray[3] = this.m03;
        fArray[4] = this.m10;
        fArray[5] = this.m11;
        fArray[6] = this.m12;
        fArray[7] = this.m13;
        fArray[8] = this.m20;
        fArray[9] = this.m21;
        fArray[10] = this.m22;
        fArray[11] = this.m23;
        fArray[12] = this.m30;
        fArray[13] = this.m31;
        fArray[14] = this.m32;
        fArray[15] = this.m33;
    }

    public void setIdentity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }

    public void mul(Matrix4f matrix4f) {
        float f = this.m00;
        float f2 = this.m01;
        float f3 = this.m02;
        float f4 = this.m03;
        float f5 = this.m10;
        float f6 = this.m11;
        float f7 = this.m12;
        float f8 = this.m13;
        float f9 = this.m20;
        float f10 = this.m21;
        float f11 = this.m22;
        float f12 = this.m23;
        float f13 = this.m30;
        float f14 = this.m31;
        float f15 = this.m32;
        float f16 = this.m33;
        this.m00 = f * matrix4f.m00 + f5 * matrix4f.m01 + f9 * matrix4f.m02 + f13 * matrix4f.m03;
        this.m01 = f2 * matrix4f.m00 + f6 * matrix4f.m01 + f10 * matrix4f.m02 + f14 * matrix4f.m03;
        this.m02 = f3 * matrix4f.m00 + f7 * matrix4f.m01 + f11 * matrix4f.m02 + f15 * matrix4f.m03;
        this.m03 = f4 * matrix4f.m00 + f8 * matrix4f.m01 + f12 * matrix4f.m02 + f16 * matrix4f.m03;
        this.m10 = f * matrix4f.m10 + f5 * matrix4f.m11 + f9 * matrix4f.m12 + f13 * matrix4f.m13;
        this.m11 = f2 * matrix4f.m10 + f6 * matrix4f.m11 + f10 * matrix4f.m12 + f14 * matrix4f.m13;
        this.m12 = f3 * matrix4f.m10 + f7 * matrix4f.m11 + f11 * matrix4f.m12 + f15 * matrix4f.m13;
        this.m13 = f4 * matrix4f.m10 + f8 * matrix4f.m11 + f12 * matrix4f.m12 + f16 * matrix4f.m13;
        this.m20 = f * matrix4f.m20 + f5 * matrix4f.m21 + f9 * matrix4f.m22 + f13 * matrix4f.m23;
        this.m21 = f2 * matrix4f.m20 + f6 * matrix4f.m21 + f10 * matrix4f.m22 + f14 * matrix4f.m23;
        this.m22 = f3 * matrix4f.m20 + f7 * matrix4f.m21 + f11 * matrix4f.m22 + f15 * matrix4f.m23;
        this.m23 = f4 * matrix4f.m20 + f8 * matrix4f.m21 + f12 * matrix4f.m22 + f16 * matrix4f.m23;
        this.m30 = f * matrix4f.m30 + f5 * matrix4f.m31 + f9 * matrix4f.m32 + f13 * matrix4f.m33;
        this.m31 = f2 * matrix4f.m30 + f6 * matrix4f.m31 + f10 * matrix4f.m32 + f14 * matrix4f.m33;
        this.m32 = f3 * matrix4f.m30 + f7 * matrix4f.m31 + f11 * matrix4f.m32 + f15 * matrix4f.m33;
        this.m33 = f4 * matrix4f.m30 + f8 * matrix4f.m31 + f12 * matrix4f.m32 + f16 * matrix4f.m33;
    }

    public void invert() {
        Matrix4f matrix4f = new Matrix4f(this);
        this.invert(matrix4f);
    }

    public void invert(Matrix4f matrix4f) {
        this.m00 = matrix4f.m00;
        this.m01 = matrix4f.m10;
        this.m02 = matrix4f.m20;
        this.m03 = matrix4f.m03;
        this.m10 = matrix4f.m01;
        this.m11 = matrix4f.m11;
        this.m12 = matrix4f.m21;
        this.m13 = matrix4f.m13;
        this.m20 = matrix4f.m02;
        this.m21 = matrix4f.m12;
        this.m22 = matrix4f.m22;
        this.m23 = matrix4f.m23;
        this.m30 *= -1.0f;
        this.m31 *= -1.0f;
        this.m32 *= -1.0f;
        this.m33 = matrix4f.m33;
    }

    public void set(AxisAngle4f axisAngle4f) {
        float f = axisAngle4f.angle * 0.5f;
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        this.set(new Quat4f(axisAngle4f.x * f3, axisAngle4f.y * f3, axisAngle4f.z * f3, f2));
    }

    public void set(Quat4f quat4f) {
        float f = quat4f.x + quat4f.x;
        float f2 = quat4f.y + quat4f.y;
        float f3 = quat4f.z + quat4f.z;
        float f4 = quat4f.x * f;
        float f5 = quat4f.x * f2;
        float f6 = quat4f.x * f3;
        float f7 = quat4f.y * f2;
        float f8 = quat4f.y * f3;
        float f9 = quat4f.z * f3;
        float f10 = quat4f.w * f;
        float f11 = quat4f.w * f2;
        float f12 = quat4f.w * f3;
        this.m00 = 1.0f - (f7 + f9);
        this.m01 = f5 - f12;
        this.m02 = f6 + f11;
        this.m03 = 0.0f;
        this.m10 = f5 + f12;
        this.m11 = 1.0f - (f4 + f9);
        this.m12 = f8 - f10;
        this.m13 = 0.0f;
        this.m20 = f6 - f11;
        this.m21 = f8 + f10;
        this.m22 = 1.0f - (f4 + f7);
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }

    public void transform(Point3f point3f) {
        float f = point3f.x * this.m00 + point3f.y * this.m10 + point3f.z * this.m20 + this.m30;
        float f2 = point3f.x * this.m01 + point3f.y * this.m11 + point3f.z * this.m21 + this.m31;
        float f3 = point3f.x * this.m02 + point3f.y * this.m12 + point3f.z * this.m22 + this.m32;
        point3f.x = f;
        point3f.y = f2;
        point3f.z = f3;
    }

    public void transform(Vector3f vector3f) {
        float f = vector3f.x * this.m00 + vector3f.y * this.m10 + vector3f.z * this.m20;
        float f2 = vector3f.x * this.m01 + vector3f.y * this.m11 + vector3f.z * this.m21;
        float f3 = vector3f.x * this.m02 + vector3f.y * this.m12 + vector3f.z * this.m22;
        vector3f.x = f;
        vector3f.y = f2;
        vector3f.z = f3;
    }

    public void setTranslation(Vector3f vector3f) {
        this.m30 = vector3f.x;
        this.m31 = vector3f.y;
        this.m32 = vector3f.z;
    }

    public void set(float f) {
        this.m00 = f;
        this.m11 = f;
        this.m22 = f;
    }

    public void rotX(float f) {
        float f2 = (float)Math.sin(f);
        float f3 = (float)Math.cos(f);
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = f3;
        this.m12 = f2;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = -f2;
        this.m22 = f3;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }

    public void rotY(float f) {
        float f2;
        float f3 = (float)Math.sin(f);
        this.m00 = f2 = (float)Math.cos(f);
        this.m01 = 0.0f;
        this.m02 = -f3;
        this.m03 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = f3;
        this.m21 = 0.0f;
        this.m22 = f2;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }

    public void rotZ(float f) {
        float f2;
        float f3 = (float)Math.sin(f);
        this.m00 = f2 = (float)Math.cos(f);
        this.m01 = f3;
        this.m02 = 0.0f;
        this.m03 = 0.0f;
        this.m10 = -f3;
        this.m11 = f2;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m30 = 0.0f;
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
    }
}

