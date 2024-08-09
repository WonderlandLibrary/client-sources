/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.vector;

import java.util.EnumSet;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;

public class Vector3d
implements IPosition {
    public static final Vector3d ZERO = new Vector3d(0.0, 0.0, 0.0);
    public double x;
    public double y;
    public double z;

    public static Vector3d unpack(int n) {
        double d = (double)(n >> 16 & 0xFF) / 255.0;
        double d2 = (double)(n >> 8 & 0xFF) / 255.0;
        double d3 = (double)(n & 0xFF) / 255.0;
        return new Vector3d(d, d2, d3);
    }

    public static Vector3d copyCentered(Vector3i vector3i) {
        return new Vector3d((double)vector3i.getX() + 0.5, (double)vector3i.getY() + 0.5, (double)vector3i.getZ() + 0.5);
    }

    public static Vector3d copy(Vector3i vector3i) {
        return new Vector3d(vector3i.getX(), vector3i.getY(), vector3i.getZ());
    }

    public static Vector3d copyCenteredHorizontally(Vector3i vector3i) {
        return new Vector3d((double)vector3i.getX() + 0.5, vector3i.getY(), (double)vector3i.getZ() + 0.5);
    }

    public static Vector3d copyCenteredWithVerticalOffset(Vector3i vector3i, double d) {
        return new Vector3d((double)vector3i.getX() + 0.5, (double)vector3i.getY() + d, (double)vector3i.getZ() + 0.5);
    }

    public Vector3d(double d, double d2, double d3) {
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public Vector3d(Vector3f vector3f) {
        this(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    public Vector3d subtractReverse(Vector3d vector3d) {
        return new Vector3d(vector3d.x - this.x, vector3d.y - this.y, vector3d.z - this.z);
    }

    public Vector3d normalize() {
        double d = MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        return d < 1.0E-4 ? ZERO : new Vector3d(this.x / d, this.y / d, this.z / d);
    }

    public double dotProduct(Vector3d vector3d) {
        return this.x * vector3d.x + this.y * vector3d.y + this.z * vector3d.z;
    }

    public Vector3d crossProduct(Vector3d vector3d) {
        return new Vector3d(this.y * vector3d.z - this.z * vector3d.y, this.z * vector3d.x - this.x * vector3d.z, this.x * vector3d.y - this.y * vector3d.x);
    }

    public Vector3d subtract(Vector3d vector3d) {
        return this.subtract(vector3d.x, vector3d.y, vector3d.z);
    }

    public Vector3d subtract(double d, double d2, double d3) {
        return this.add(-d, -d2, -d3);
    }

    public Vector3d add(Vector3d vector3d) {
        return this.add(vector3d.x, vector3d.y, vector3d.z);
    }

    public Vector3d add(double d, double d2, double d3) {
        return new Vector3d(this.x + d, this.y + d2, this.z + d3);
    }

    public boolean isWithinDistanceOf(IPosition iPosition, double d) {
        return this.squareDistanceTo(iPosition.getX(), iPosition.getY(), iPosition.getZ()) < d * d;
    }

    public double distanceTo(Vector3d vector3d) {
        double d = vector3d.x - this.x;
        double d2 = vector3d.y - this.y;
        double d3 = vector3d.z - this.z;
        return MathHelper.sqrt(d * d + d2 * d2 + d3 * d3);
    }

    public double squareDistanceTo(Vector3d vector3d) {
        double d = vector3d.x - this.x;
        double d2 = vector3d.y - this.y;
        double d3 = vector3d.z - this.z;
        return d * d + d2 * d2 + d3 * d3;
    }

    public double squareDistanceTo(double d, double d2, double d3) {
        double d4 = d - this.x;
        double d5 = d2 - this.y;
        double d6 = d3 - this.z;
        return d4 * d4 + d5 * d5 + d6 * d6;
    }

    public Vector3d scale(double d) {
        return this.mul(d, d, d);
    }

    public Vector3d inverse() {
        return this.scale(-1.0);
    }

    public Vector3d mul(Vector3d vector3d) {
        return this.mul(vector3d.x, vector3d.y, vector3d.z);
    }

    public Vector3d mul(double d, double d2, double d3) {
        return new Vector3d(this.x * d, this.y * d2, this.z * d3);
    }

    public double length() {
        return MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Vector3d)) {
            return true;
        }
        Vector3d vector3d = (Vector3d)object;
        if (Double.compare(vector3d.x, this.x) != 0) {
            return true;
        }
        if (Double.compare(vector3d.y, this.y) != 0) {
            return true;
        }
        return Double.compare(vector3d.z, this.z) == 0;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.x);
        int n = (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.y);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.z);
        return 31 * n + (int)(l ^ l >>> 32);
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public Vector3d rotatePitch(float f) {
        float f2 = MathHelper.cos(f);
        float f3 = MathHelper.sin(f);
        double d = this.x;
        double d2 = this.y * (double)f2 + this.z * (double)f3;
        double d3 = this.z * (double)f2 - this.y * (double)f3;
        return new Vector3d(d, d2, d3);
    }

    public Vector3d rotateYaw(float f) {
        float f2 = MathHelper.cos(f);
        float f3 = MathHelper.sin(f);
        double d = this.x * (double)f2 + this.z * (double)f3;
        double d2 = this.y;
        double d3 = this.z * (double)f2 - this.x * (double)f3;
        return new Vector3d(d, d2, d3);
    }

    public Vector3d rotateRoll(float f) {
        float f2 = MathHelper.cos(f);
        float f3 = MathHelper.sin(f);
        double d = this.x * (double)f2 + this.y * (double)f3;
        double d2 = this.y * (double)f2 - this.x * (double)f3;
        double d3 = this.z;
        return new Vector3d(d, d2, d3);
    }

    public static Vector3d fromPitchYaw(Vector2f vector2f) {
        return Vector3d.fromPitchYaw(vector2f.x, vector2f.y);
    }

    public static Vector3d fromPitchYaw(float f, float f2) {
        float f3 = MathHelper.cos(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f5 = -MathHelper.cos(-f * ((float)Math.PI / 180));
        float f6 = MathHelper.sin(-f * ((float)Math.PI / 180));
        return new Vector3d(f4 * f5, f6, f3 * f5);
    }

    public Vector3d align(EnumSet<Direction.Axis> enumSet) {
        double d = enumSet.contains(Direction.Axis.X) ? (double)MathHelper.floor(this.x) : this.x;
        double d2 = enumSet.contains(Direction.Axis.Y) ? (double)MathHelper.floor(this.y) : this.y;
        double d3 = enumSet.contains(Direction.Axis.Z) ? (double)MathHelper.floor(this.z) : this.z;
        return new Vector3d(d, d2, d3);
    }

    public double getCoordinate(Direction.Axis axis) {
        return axis.getCoordinate(this.x, this.y, this.z);
    }

    @Override
    public final double getX() {
        return this.x;
    }

    @Override
    public final double getY() {
        return this.y;
    }

    @Override
    public final double getZ() {
        return this.z;
    }
}

