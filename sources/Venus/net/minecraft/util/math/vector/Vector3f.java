/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.vector;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;

public final class Vector3f {
    public static Vector3f XN = new Vector3f(-1.0f, 0.0f, 0.0f);
    public static Vector3f XP = new Vector3f(1.0f, 0.0f, 0.0f);
    public static Vector3f YN = new Vector3f(0.0f, -1.0f, 0.0f);
    public static Vector3f YP = new Vector3f(0.0f, 1.0f, 0.0f);
    public static Vector3f ZN = new Vector3f(0.0f, 0.0f, -1.0f);
    public static Vector3f ZP = new Vector3f(0.0f, 0.0f, 1.0f);
    public float x;
    public float y;
    public float z;

    public Vector3f() {
    }

    public Vector3f(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public Vector3f(Vector3d vector3d) {
        this((float)vector3d.x, (float)vector3d.y, (float)vector3d.z);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            Vector3f vector3f = (Vector3f)object;
            if (Float.compare(vector3f.x, this.x) != 0) {
                return true;
            }
            if (Float.compare(vector3f.y, this.y) != 0) {
                return true;
            }
            return Float.compare(vector3f.z, this.z) == 0;
        }
        return true;
    }

    public int hashCode() {
        int n = Float.floatToIntBits(this.x);
        n = 31 * n + Float.floatToIntBits(this.y);
        return 31 * n + Float.floatToIntBits(this.z);
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

    public void mul(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
    }

    public void mul(float f, float f2, float f3) {
        this.x *= f;
        this.y *= f2;
        this.z *= f3;
    }

    public void clamp(float f, float f2) {
        this.x = MathHelper.clamp(this.x, f, f2);
        this.y = MathHelper.clamp(this.y, f, f2);
        this.z = MathHelper.clamp(this.z, f, f2);
    }

    public void set(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
    }

    public void add(float f, float f2, float f3) {
        this.x += f;
        this.y += f2;
        this.z += f3;
    }

    public void add(Vector3f vector3f) {
        this.x += vector3f.x;
        this.y += vector3f.y;
        this.z += vector3f.z;
    }

    public void sub(Vector3f vector3f) {
        this.x -= vector3f.x;
        this.y -= vector3f.y;
        this.z -= vector3f.z;
    }

    public void setY(float f) {
        this.y = f;
    }

    public float dot(Vector3f vector3f) {
        return this.x * vector3f.x + this.y * vector3f.y + this.z * vector3f.z;
    }

    public boolean normalize() {
        float f = this.x * this.x + this.y * this.y + this.z * this.z;
        if ((double)f < 1.0E-5) {
            return true;
        }
        float f2 = MathHelper.fastInvSqrt(f);
        this.x *= f2;
        this.y *= f2;
        this.z *= f2;
        return false;
    }

    public void cross(Vector3f vector3f) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.z;
        float f4 = vector3f.getX();
        float f5 = vector3f.getY();
        float f6 = vector3f.getZ();
        this.x = f2 * f6 - f3 * f5;
        this.y = f3 * f4 - f * f6;
        this.z = f * f5 - f2 * f4;
    }

    public void transform(Matrix3f matrix3f) {
        float f = this.x;
        float f2 = this.y;
        float f3 = this.z;
        this.x = matrix3f.m00 * f + matrix3f.m01 * f2 + matrix3f.m02 * f3;
        this.y = matrix3f.m10 * f + matrix3f.m11 * f2 + matrix3f.m12 * f3;
        this.z = matrix3f.m20 * f + matrix3f.m21 * f2 + matrix3f.m22 * f3;
    }

    public void transform(Quaternion quaternion) {
        Quaternion quaternion2 = new Quaternion(quaternion);
        quaternion2.multiply(new Quaternion(this.getX(), this.getY(), this.getZ(), 0.0f));
        Quaternion quaternion3 = new Quaternion(quaternion);
        quaternion3.conjugate();
        quaternion2.multiply(quaternion3);
        this.set(quaternion2.getX(), quaternion2.getY(), quaternion2.getZ());
    }

    public void lerp(Vector3f vector3f, float f) {
        float f2 = 1.0f - f;
        this.x = this.x * f2 + vector3f.x * f;
        this.y = this.y * f2 + vector3f.y * f;
        this.z = this.z * f2 + vector3f.z * f;
    }

    public Quaternion rotation(float f) {
        return new Quaternion(this, f, false);
    }

    public Quaternion rotationDegrees(float f) {
        return new Quaternion(this, f, true);
    }

    public Vector3f copy() {
        return new Vector3f(this.x, this.y, this.z);
    }

    public void apply(Float2FloatFunction float2FloatFunction) {
        this.x = float2FloatFunction.get(this.x);
        this.y = float2FloatFunction.get(this.y);
        this.z = float2FloatFunction.get(this.z);
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }
}

