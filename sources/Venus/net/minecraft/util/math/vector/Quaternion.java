/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.vector;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public final class Quaternion {
    public static final Quaternion ONE = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    public Quaternion(Vector3f vector3f, float f, boolean bl) {
        if (bl) {
            f *= (float)Math.PI / 180;
        }
        float f2 = Quaternion.sin(f / 2.0f);
        this.x = vector3f.getX() * f2;
        this.y = vector3f.getY() * f2;
        this.z = vector3f.getZ() * f2;
        this.w = Quaternion.cos(f / 2.0f);
    }

    public Quaternion(float f, float f2, float f3, boolean bl) {
        if (bl) {
            f *= (float)Math.PI / 180;
            f2 *= (float)Math.PI / 180;
            f3 *= (float)Math.PI / 180;
        }
        float f4 = Quaternion.sin(0.5f * f);
        float f5 = Quaternion.cos(0.5f * f);
        float f6 = Quaternion.sin(0.5f * f2);
        float f7 = Quaternion.cos(0.5f * f2);
        float f8 = Quaternion.sin(0.5f * f3);
        float f9 = Quaternion.cos(0.5f * f3);
        this.x = f4 * f7 * f9 + f5 * f6 * f8;
        this.y = f5 * f6 * f9 - f4 * f7 * f8;
        this.z = f4 * f6 * f9 + f5 * f7 * f8;
        this.w = f5 * f7 * f9 - f4 * f6 * f8;
    }

    public Quaternion(Quaternion quaternion) {
        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            Quaternion quaternion = (Quaternion)object;
            if (Float.compare(quaternion.x, this.x) != 0) {
                return true;
            }
            if (Float.compare(quaternion.y, this.y) != 0) {
                return true;
            }
            if (Float.compare(quaternion.z, this.z) != 0) {
                return true;
            }
            return Float.compare(quaternion.w, this.w) == 0;
        }
        return true;
    }

    public int hashCode() {
        int n = Float.floatToIntBits(this.x);
        n = 31 * n + Float.floatToIntBits(this.y);
        n = 31 * n + Float.floatToIntBits(this.z);
        return 31 * n + Float.floatToIntBits(this.w);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Quaternion[").append(this.getW()).append(" + ");
        stringBuilder.append(this.getX()).append("i + ");
        stringBuilder.append(this.getY()).append("j + ");
        stringBuilder.append(this.getZ()).append("k]");
        return stringBuilder.toString();
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

    public void multiply(Quaternion quaternion) {
        float f = this.getX();
        float f2 = this.getY();
        float f3 = this.getZ();
        float f4 = this.getW();
        float f5 = quaternion.getX();
        float f6 = quaternion.getY();
        float f7 = quaternion.getZ();
        float f8 = quaternion.getW();
        this.x = f4 * f5 + f * f8 + f2 * f7 - f3 * f6;
        this.y = f4 * f6 - f * f7 + f2 * f8 + f3 * f5;
        this.z = f4 * f7 + f * f6 - f2 * f5 + f3 * f8;
        this.w = f4 * f8 - f * f5 - f2 * f6 - f3 * f7;
    }

    public void multiply(float f) {
        this.x *= f;
        this.y *= f;
        this.z *= f;
        this.w *= f;
    }

    public void conjugate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public void set(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.w = f4;
    }

    private static float cos(float f) {
        return (float)Math.cos(f);
    }

    private static float sin(float f) {
        return (float)Math.sin(f);
    }

    public void normalize() {
        float f = this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ() + this.getW() * this.getW();
        if (f > 1.0E-6f) {
            float f2 = MathHelper.fastInvSqrt(f);
            this.x *= f2;
            this.y *= f2;
            this.z *= f2;
            this.w *= f2;
        } else {
            this.x = 0.0f;
            this.y = 0.0f;
            this.z = 0.0f;
            this.w = 0.0f;
        }
    }

    public Quaternion copy() {
        return new Quaternion(this);
    }
}

