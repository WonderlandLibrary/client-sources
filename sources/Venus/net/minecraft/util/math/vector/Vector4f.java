/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.vector;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class Vector4f {
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4f() {
    }

    public Vector4f(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public Vector4f(Vector3f vector3f) {
        this(vector3f.getX(), vector3f.getY(), vector3f.getZ(), 1.0f);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            Vector4f vector4f = (Vector4f)object;
            if (Float.compare(vector4f.x, this.x) != 0) {
                return true;
            }
            if (Float.compare(vector4f.y, this.y) != 0) {
                return true;
            }
            if (Float.compare(vector4f.z, this.z) != 0) {
                return true;
            }
            return Float.compare(vector4f.w, this.w) == 0;
        }
        return true;
    }

    public int hashCode() {
        int n = Float.floatToIntBits(this.x);
        n = 31 * n + Float.floatToIntBits(this.y);
        n = 31 * n + Float.floatToIntBits(this.z);
        return 31 * n + Float.floatToIntBits(this.w);
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public float getW() {
        return this.w;
    }

    public void scale(Vector3f vector3f) {
        this.x *= vector3f.getX();
        this.y *= vector3f.getY();
        this.z *= vector3f.getZ();
    }

    public void set(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public float dot(Vector4f vector4f) {
        return this.x * vector4f.x + this.y * vector4f.y + this.z * vector4f.z + this.w * vector4f.w;
    }

    public boolean normalize() {
        float f = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
        if ((double)f < 1.0E-5) {
            return true;
        }
        float f2 = MathHelper.fastInvSqrt(f);
        this.x *= f2;
        this.y *= f2;
        this.z *= f2;
        this.w *= f2;
        return false;
    }

    public void transform(Matrix4f matrix4f) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.z;
        float f4 = this.w;
        this.x = matrix4f.m00 * f + matrix4f.m01 * f2 + matrix4f.m02 * f3 + matrix4f.m03 * f4;
        this.y = matrix4f.m10 * f + matrix4f.m11 * f2 + matrix4f.m12 * f3 + matrix4f.m13 * f4;
        this.z = matrix4f.m20 * f + matrix4f.m21 * f2 + matrix4f.m22 * f3 + matrix4f.m23 * f4;
        this.w = matrix4f.m30 * f + matrix4f.m31 * f2 + matrix4f.m32 * f3 + matrix4f.m33 * f4;
    }

    public void transform(Quaternion quaternion) {
        Quaternion quaternion2 = new Quaternion(quaternion);
        quaternion2.multiply(new Quaternion(this.getX(), this.getY(), this.getZ(), 0.0f));
        Quaternion quaternion3 = new Quaternion(quaternion);
        quaternion3.conjugate();
        quaternion2.multiply(quaternion3);
        this.set(quaternion2.getX(), quaternion2.getY(), quaternion2.getZ(), this.getW());
    }

    public void perspectiveDivide() {
        this.x /= this.w;
        this.y /= this.w;
        this.z /= this.w;
        this.w = 1.0f;
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + "]";
    }
}

