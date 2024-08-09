/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.vector;

import com.mojang.datafixers.util.Pair;
import java.util.Random;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import org.apache.commons.lang3.tuple.Triple;

public final class Matrix3f {
    private static final float G = 3.0f + 2.0f * (float)Math.sqrt(2.0);
    private static final float CS = (float)Math.cos(0.39269908169872414);
    private static final float SS = (float)Math.sin(0.39269908169872414);
    private static final float SQ2 = 1.0f / (float)Math.sqrt(2.0);
    protected float m00;
    protected float m01;
    protected float m02;
    protected float m10;
    protected float m11;
    protected float m12;
    protected float m20;
    protected float m21;
    protected float m22;

    public Matrix3f() {
    }

    public Matrix3f(Quaternion quaternion) {
        float f = quaternion.getX();
        float f2 = quaternion.getY();
        float f3 = quaternion.getZ();
        float f4 = quaternion.getW();
        float f5 = 2.0f * f * f;
        float f6 = 2.0f * f2 * f2;
        float f7 = 2.0f * f3 * f3;
        this.m00 = 1.0f - f6 - f7;
        this.m11 = 1.0f - f7 - f5;
        this.m22 = 1.0f - f5 - f6;
        float f8 = f * f2;
        float f9 = f2 * f3;
        float f10 = f3 * f;
        float f11 = f * f4;
        float f12 = f2 * f4;
        float f13 = f3 * f4;
        this.m10 = 2.0f * (f8 + f13);
        this.m01 = 2.0f * (f8 - f13);
        this.m20 = 2.0f * (f10 - f12);
        this.m02 = 2.0f * (f10 + f12);
        this.m21 = 2.0f * (f9 + f11);
        this.m12 = 2.0f * (f9 - f11);
    }

    public static Matrix3f makeScaleMatrix(float f, float f2, float f3) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.m00 = f;
        matrix3f.m11 = f2;
        matrix3f.m22 = f3;
        return matrix3f;
    }

    public Matrix3f(Matrix4f matrix4f) {
        this.m00 = matrix4f.m00;
        this.m01 = matrix4f.m01;
        this.m02 = matrix4f.m02;
        this.m10 = matrix4f.m10;
        this.m11 = matrix4f.m11;
        this.m12 = matrix4f.m12;
        this.m20 = matrix4f.m20;
        this.m21 = matrix4f.m21;
        this.m22 = matrix4f.m22;
    }

    public Matrix3f(Matrix3f matrix3f) {
        this.m00 = matrix3f.m00;
        this.m01 = matrix3f.m01;
        this.m02 = matrix3f.m02;
        this.m10 = matrix3f.m10;
        this.m11 = matrix3f.m11;
        this.m12 = matrix3f.m12;
        this.m20 = matrix3f.m20;
        this.m21 = matrix3f.m21;
        this.m22 = matrix3f.m22;
    }

    private static Pair<Float, Float> approxGivensQuat(float f, float f2, float f3) {
        float f4 = 2.0f * (f - f3);
        if (G * f2 * f2 < f4 * f4) {
            float f5 = MathHelper.fastInvSqrt(f2 * f2 + f4 * f4);
            return Pair.of(Float.valueOf(f5 * f2), Float.valueOf(f5 * f4));
        }
        return Pair.of(Float.valueOf(SS), Float.valueOf(CS));
    }

    private static Pair<Float, Float> qrGivensQuat(float f, float f2) {
        float f3;
        float f4 = (float)Math.hypot(f, f2);
        float f5 = f4 > 1.0E-6f ? f2 : 0.0f;
        float f6 = Math.abs(f) + Math.max(f4, 1.0E-6f);
        if (f < 0.0f) {
            f3 = f5;
            f5 = f6;
            f6 = f3;
        }
        f3 = MathHelper.fastInvSqrt(f6 * f6 + f5 * f5);
        return Pair.of(Float.valueOf(f5 *= f3), Float.valueOf(f6 *= f3));
    }

    private static Quaternion stepJacobi(Matrix3f matrix3f) {
        float f;
        float f2;
        float f3;
        Quaternion quaternion;
        Float f4;
        Float f5;
        Pair<Float, Float> pair;
        Matrix3f matrix3f2 = new Matrix3f();
        Quaternion quaternion2 = Quaternion.ONE.copy();
        if (matrix3f.m01 * matrix3f.m01 + matrix3f.m10 * matrix3f.m10 > 1.0E-6f) {
            pair = Matrix3f.approxGivensQuat(matrix3f.m00, 0.5f * (matrix3f.m01 + matrix3f.m10), matrix3f.m11);
            f5 = pair.getFirst();
            f4 = pair.getSecond();
            quaternion = new Quaternion(0.0f, 0.0f, f5.floatValue(), f4.floatValue());
            f3 = f4.floatValue() * f4.floatValue() - f5.floatValue() * f5.floatValue();
            f2 = -2.0f * f5.floatValue() * f4.floatValue();
            f = f4.floatValue() * f4.floatValue() + f5.floatValue() * f5.floatValue();
            quaternion2.multiply(quaternion);
            matrix3f2.setIdentity();
            matrix3f2.m00 = f3;
            matrix3f2.m11 = f3;
            matrix3f2.m10 = -f2;
            matrix3f2.m01 = f2;
            matrix3f2.m22 = f;
            matrix3f.mul(matrix3f2);
            matrix3f2.transpose();
            matrix3f2.mul(matrix3f);
            matrix3f.set(matrix3f2);
        }
        if (matrix3f.m02 * matrix3f.m02 + matrix3f.m20 * matrix3f.m20 > 1.0E-6f) {
            pair = Matrix3f.approxGivensQuat(matrix3f.m00, 0.5f * (matrix3f.m02 + matrix3f.m20), matrix3f.m22);
            float f6 = -pair.getFirst().floatValue();
            f4 = pair.getSecond();
            quaternion = new Quaternion(0.0f, f6, 0.0f, f4.floatValue());
            f3 = f4.floatValue() * f4.floatValue() - f6 * f6;
            f2 = -2.0f * f6 * f4.floatValue();
            f = f4.floatValue() * f4.floatValue() + f6 * f6;
            quaternion2.multiply(quaternion);
            matrix3f2.setIdentity();
            matrix3f2.m00 = f3;
            matrix3f2.m22 = f3;
            matrix3f2.m20 = f2;
            matrix3f2.m02 = -f2;
            matrix3f2.m11 = f;
            matrix3f.mul(matrix3f2);
            matrix3f2.transpose();
            matrix3f2.mul(matrix3f);
            matrix3f.set(matrix3f2);
        }
        if (matrix3f.m12 * matrix3f.m12 + matrix3f.m21 * matrix3f.m21 > 1.0E-6f) {
            pair = Matrix3f.approxGivensQuat(matrix3f.m11, 0.5f * (matrix3f.m12 + matrix3f.m21), matrix3f.m22);
            f5 = pair.getFirst();
            f4 = pair.getSecond();
            quaternion = new Quaternion(f5.floatValue(), 0.0f, 0.0f, f4.floatValue());
            f3 = f4.floatValue() * f4.floatValue() - f5.floatValue() * f5.floatValue();
            f2 = -2.0f * f5.floatValue() * f4.floatValue();
            f = f4.floatValue() * f4.floatValue() + f5.floatValue() * f5.floatValue();
            quaternion2.multiply(quaternion);
            matrix3f2.setIdentity();
            matrix3f2.m11 = f3;
            matrix3f2.m22 = f3;
            matrix3f2.m21 = -f2;
            matrix3f2.m12 = f2;
            matrix3f2.m00 = f;
            matrix3f.mul(matrix3f2);
            matrix3f2.transpose();
            matrix3f2.mul(matrix3f);
            matrix3f.set(matrix3f2);
        }
        return quaternion2;
    }

    public void transpose() {
        float f = this.m01;
        this.m01 = this.m10;
        this.m10 = f;
        f = this.m02;
        this.m02 = this.m20;
        this.m20 = f;
        f = this.m12;
        this.m12 = this.m21;
        this.m21 = f;
    }

    public Triple<Quaternion, Vector3f, Quaternion> svdDecompose() {
        Quaternion quaternion = Quaternion.ONE.copy();
        Quaternion quaternion2 = Quaternion.ONE.copy();
        Matrix3f matrix3f = this.copy();
        matrix3f.transpose();
        matrix3f.mul(this);
        for (int i = 0; i < 5; ++i) {
            quaternion2.multiply(Matrix3f.stepJacobi(matrix3f));
        }
        quaternion2.normalize();
        Matrix3f matrix3f2 = new Matrix3f(this);
        matrix3f2.mul(new Matrix3f(quaternion2));
        float f = 1.0f;
        Pair<Float, Float> pair = Matrix3f.qrGivensQuat(matrix3f2.m00, matrix3f2.m10);
        Float f2 = pair.getFirst();
        Float f3 = pair.getSecond();
        float f4 = f3.floatValue() * f3.floatValue() - f2.floatValue() * f2.floatValue();
        float f5 = -2.0f * f2.floatValue() * f3.floatValue();
        float f6 = f3.floatValue() * f3.floatValue() + f2.floatValue() * f2.floatValue();
        Quaternion quaternion3 = new Quaternion(0.0f, 0.0f, f2.floatValue(), f3.floatValue());
        quaternion.multiply(quaternion3);
        Matrix3f matrix3f3 = new Matrix3f();
        matrix3f3.setIdentity();
        matrix3f3.m00 = f4;
        matrix3f3.m11 = f4;
        matrix3f3.m10 = f5;
        matrix3f3.m01 = -f5;
        matrix3f3.m22 = f6;
        f *= f6;
        matrix3f3.mul(matrix3f2);
        pair = Matrix3f.qrGivensQuat(matrix3f3.m00, matrix3f3.m20);
        float f7 = -pair.getFirst().floatValue();
        Float f8 = pair.getSecond();
        float f9 = f8.floatValue() * f8.floatValue() - f7 * f7;
        float f10 = -2.0f * f7 * f8.floatValue();
        float f11 = f8.floatValue() * f8.floatValue() + f7 * f7;
        Quaternion quaternion4 = new Quaternion(0.0f, f7, 0.0f, f8.floatValue());
        quaternion.multiply(quaternion4);
        Matrix3f matrix3f4 = new Matrix3f();
        matrix3f4.setIdentity();
        matrix3f4.m00 = f9;
        matrix3f4.m22 = f9;
        matrix3f4.m20 = -f10;
        matrix3f4.m02 = f10;
        matrix3f4.m11 = f11;
        f *= f11;
        matrix3f4.mul(matrix3f3);
        pair = Matrix3f.qrGivensQuat(matrix3f4.m11, matrix3f4.m21);
        Float f12 = pair.getFirst();
        Float f13 = pair.getSecond();
        float f14 = f13.floatValue() * f13.floatValue() - f12.floatValue() * f12.floatValue();
        float f15 = -2.0f * f12.floatValue() * f13.floatValue();
        float f16 = f13.floatValue() * f13.floatValue() + f12.floatValue() * f12.floatValue();
        Quaternion quaternion5 = new Quaternion(f12.floatValue(), 0.0f, 0.0f, f13.floatValue());
        quaternion.multiply(quaternion5);
        Matrix3f matrix3f5 = new Matrix3f();
        matrix3f5.setIdentity();
        matrix3f5.m11 = f14;
        matrix3f5.m22 = f14;
        matrix3f5.m21 = f15;
        matrix3f5.m12 = -f15;
        matrix3f5.m00 = f16;
        f *= f16;
        matrix3f5.mul(matrix3f4);
        f = 1.0f / f;
        quaternion.multiply((float)Math.sqrt(f));
        Vector3f vector3f = new Vector3f(matrix3f5.m00 * f, matrix3f5.m11 * f, matrix3f5.m22 * f);
        return Triple.of(quaternion, vector3f, quaternion2);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            Matrix3f matrix3f = (Matrix3f)object;
            return Float.compare(matrix3f.m00, this.m00) == 0 && Float.compare(matrix3f.m01, this.m01) == 0 && Float.compare(matrix3f.m02, this.m02) == 0 && Float.compare(matrix3f.m10, this.m10) == 0 && Float.compare(matrix3f.m11, this.m11) == 0 && Float.compare(matrix3f.m12, this.m12) == 0 && Float.compare(matrix3f.m20, this.m20) == 0 && Float.compare(matrix3f.m21, this.m21) == 0 && Float.compare(matrix3f.m22, this.m22) == 0;
        }
        return true;
    }

    public int hashCode() {
        int n = this.m00 != 0.0f ? Float.floatToIntBits(this.m00) : 0;
        n = 31 * n + (this.m01 != 0.0f ? Float.floatToIntBits(this.m01) : 0);
        n = 31 * n + (this.m02 != 0.0f ? Float.floatToIntBits(this.m02) : 0);
        n = 31 * n + (this.m10 != 0.0f ? Float.floatToIntBits(this.m10) : 0);
        n = 31 * n + (this.m11 != 0.0f ? Float.floatToIntBits(this.m11) : 0);
        n = 31 * n + (this.m12 != 0.0f ? Float.floatToIntBits(this.m12) : 0);
        n = 31 * n + (this.m20 != 0.0f ? Float.floatToIntBits(this.m20) : 0);
        n = 31 * n + (this.m21 != 0.0f ? Float.floatToIntBits(this.m21) : 0);
        return 31 * n + (this.m22 != 0.0f ? Float.floatToIntBits(this.m22) : 0);
    }

    public void set(Matrix3f matrix3f) {
        this.m00 = matrix3f.m00;
        this.m01 = matrix3f.m01;
        this.m02 = matrix3f.m02;
        this.m10 = matrix3f.m10;
        this.m11 = matrix3f.m11;
        this.m12 = matrix3f.m12;
        this.m20 = matrix3f.m20;
        this.m21 = matrix3f.m21;
        this.m22 = matrix3f.m22;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Matrix3f:\n");
        stringBuilder.append(this.m00);
        stringBuilder.append(" ");
        stringBuilder.append(this.m01);
        stringBuilder.append(" ");
        stringBuilder.append(this.m02);
        stringBuilder.append("\n");
        stringBuilder.append(this.m10);
        stringBuilder.append(" ");
        stringBuilder.append(this.m11);
        stringBuilder.append(" ");
        stringBuilder.append(this.m12);
        stringBuilder.append("\n");
        stringBuilder.append(this.m20);
        stringBuilder.append(" ");
        stringBuilder.append(this.m21);
        stringBuilder.append(" ");
        stringBuilder.append(this.m22);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public void setIdentity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m02 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m20 = 0.0f;
        this.m21 = 0.0f;
        this.m22 = 1.0f;
    }

    public float adjugateAndDet() {
        float f = this.m11 * this.m22 - this.m12 * this.m21;
        float f2 = -(this.m10 * this.m22 - this.m12 * this.m20);
        float f3 = this.m10 * this.m21 - this.m11 * this.m20;
        float f4 = -(this.m01 * this.m22 - this.m02 * this.m21);
        float f5 = this.m00 * this.m22 - this.m02 * this.m20;
        float f6 = -(this.m00 * this.m21 - this.m01 * this.m20);
        float f7 = this.m01 * this.m12 - this.m02 * this.m11;
        float f8 = -(this.m00 * this.m12 - this.m02 * this.m10);
        float f9 = this.m00 * this.m11 - this.m01 * this.m10;
        float f10 = this.m00 * f + this.m01 * f2 + this.m02 * f3;
        this.m00 = f;
        this.m10 = f2;
        this.m20 = f3;
        this.m01 = f4;
        this.m11 = f5;
        this.m21 = f6;
        this.m02 = f7;
        this.m12 = f8;
        this.m22 = f9;
        return f10;
    }

    public boolean invert() {
        float f = this.adjugateAndDet();
        if (Math.abs(f) > 1.0E-6f) {
            this.mul(f);
            return false;
        }
        return true;
    }

    public void func_232605_a_(int n, int n2, float f) {
        if (n == 0) {
            if (n2 == 0) {
                this.m00 = f;
            } else if (n2 == 1) {
                this.m01 = f;
            } else {
                this.m02 = f;
            }
        } else if (n == 1) {
            if (n2 == 0) {
                this.m10 = f;
            } else if (n2 == 1) {
                this.m11 = f;
            } else {
                this.m12 = f;
            }
        } else if (n2 == 0) {
            this.m20 = f;
        } else if (n2 == 1) {
            this.m21 = f;
        } else {
            this.m22 = f;
        }
    }

    public void mul(Matrix3f matrix3f) {
        float f = this.m00 * matrix3f.m00 + this.m01 * matrix3f.m10 + this.m02 * matrix3f.m20;
        float f2 = this.m00 * matrix3f.m01 + this.m01 * matrix3f.m11 + this.m02 * matrix3f.m21;
        float f3 = this.m00 * matrix3f.m02 + this.m01 * matrix3f.m12 + this.m02 * matrix3f.m22;
        float f4 = this.m10 * matrix3f.m00 + this.m11 * matrix3f.m10 + this.m12 * matrix3f.m20;
        float f5 = this.m10 * matrix3f.m01 + this.m11 * matrix3f.m11 + this.m12 * matrix3f.m21;
        float f6 = this.m10 * matrix3f.m02 + this.m11 * matrix3f.m12 + this.m12 * matrix3f.m22;
        float f7 = this.m20 * matrix3f.m00 + this.m21 * matrix3f.m10 + this.m22 * matrix3f.m20;
        float f8 = this.m20 * matrix3f.m01 + this.m21 * matrix3f.m11 + this.m22 * matrix3f.m21;
        float f9 = this.m20 * matrix3f.m02 + this.m21 * matrix3f.m12 + this.m22 * matrix3f.m22;
        this.m00 = f;
        this.m01 = f2;
        this.m02 = f3;
        this.m10 = f4;
        this.m11 = f5;
        this.m12 = f6;
        this.m20 = f7;
        this.m21 = f8;
        this.m22 = f9;
    }

    public void mul(Quaternion quaternion) {
        float f = quaternion.getX();
        float f2 = quaternion.getY();
        float f3 = quaternion.getZ();
        float f4 = quaternion.getW();
        float f5 = 2.0f * f * f;
        float f6 = 2.0f * f2 * f2;
        float f7 = 2.0f * f3 * f3;
        float f8 = f * f2;
        float f9 = f2 * f3;
        float f10 = f3 * f;
        float f11 = f * f4;
        float f12 = f2 * f4;
        float f13 = f3 * f4;
        float f14 = 1.0f - f6 - f7;
        float f15 = 2.0f * (f8 - f13);
        float f16 = 2.0f * (f10 + f12);
        float f17 = 2.0f * (f8 + f13);
        float f18 = 1.0f - f7 - f5;
        float f19 = 2.0f * (f9 - f11);
        float f20 = 2.0f * (f10 - f12);
        float f21 = 2.0f * (f9 + f11);
        float f22 = 1.0f - f5 - f6;
        float f23 = this.m00 * f14 + this.m01 * f17 + this.m02 * f20;
        float f24 = this.m00 * f15 + this.m01 * f18 + this.m02 * f21;
        float f25 = this.m00 * f16 + this.m01 * f19 + this.m02 * f22;
        float f26 = this.m10 * f14 + this.m11 * f17 + this.m12 * f20;
        float f27 = this.m10 * f15 + this.m11 * f18 + this.m12 * f21;
        float f28 = this.m10 * f16 + this.m11 * f19 + this.m12 * f22;
        float f29 = this.m20 * f14 + this.m21 * f17 + this.m22 * f20;
        float f30 = this.m20 * f15 + this.m21 * f18 + this.m22 * f21;
        float f31 = this.m20 * f16 + this.m21 * f19 + this.m22 * f22;
        this.m00 = f23;
        this.m01 = f24;
        this.m02 = f25;
        this.m10 = f26;
        this.m11 = f27;
        this.m12 = f28;
        this.m20 = f29;
        this.m21 = f30;
        this.m22 = f31;
    }

    public void mul(float f) {
        this.m00 *= f;
        this.m01 *= f;
        this.m02 *= f;
        this.m10 *= f;
        this.m11 *= f;
        this.m12 *= f;
        this.m20 *= f;
        this.m21 *= f;
        this.m22 *= f;
    }

    public Matrix3f copy() {
        return new Matrix3f(this);
    }

    public float getTransformX(float f, float f2, float f3) {
        return this.m00 * f + this.m01 * f2 + this.m02 * f3;
    }

    public float getTransformY(float f, float f2, float f3) {
        return this.m10 * f + this.m11 * f2 + this.m12 * f3;
    }

    public float getTransformZ(float f, float f2, float f3) {
        return this.m20 * f + this.m21 * f2 + this.m22 * f3;
    }

    public void setRandom(Random random2) {
        this.m00 = random2.nextFloat();
        this.m01 = random2.nextFloat();
        this.m02 = random2.nextFloat();
        this.m10 = random2.nextFloat();
        this.m11 = random2.nextFloat();
        this.m12 = random2.nextFloat();
        this.m20 = random2.nextFloat();
        this.m21 = random2.nextFloat();
        this.m22 = random2.nextFloat();
    }

    public void multiplyBackward(Matrix3f matrix3f) {
        Matrix3f matrix3f2 = matrix3f.copy();
        matrix3f2.mul(this);
        this.set(matrix3f2);
    }
}

