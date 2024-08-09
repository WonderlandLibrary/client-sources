package dev.waveycapes.math;

import net.minecraft.util.math.MathHelper;

public class Vector3
{
    public float x;
    public float y;
    public float z;

    public Vector3(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 clone() {
        return new Vector3(this.x, this.y, this.z);
    }

    public void copy(final Vector3 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }

    public Vector3 add(final Vector3 vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        return this;
    }

    public Vector3 subtract(final Vector3 vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        return this;
    }

    public Vector3 div(final float amount) {
        this.x /= amount;
        this.y /= amount;
        this.z /= amount;
        return this;
    }

    public Vector3 mul(final float amount) {
        this.x *= amount;
        this.y *= amount;
        this.z *= amount;
        return this;
    }

    public Vector3 normalize() {
        final float f = MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        if (f < 1.0E-4f) {
            this.x = 0.0f;
            this.y = 0.0f;
            this.z = 0.0f;
        }
        else {
            this.x /= f;
            this.y /= f;
            this.z /= f;
        }
        return this;
    }

    public Vector3 rotateDegrees(float deg) {
        final float ox = this.x;
        final float oy = this.y;
        deg = (float)Math.toRadians(deg);
        this.x = MathHelper.cos(deg) * ox - MathHelper.sin(deg) * oy;
        this.y = MathHelper.sin(deg) * ox + MathHelper.cos(deg) * oy;
        return this;
    }

    @Override
    public String toString() {
        return "Vector2 [x=" + this.x + ", y=" + this.y + "]";
    }

    public float sqrMagnitude() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
}
