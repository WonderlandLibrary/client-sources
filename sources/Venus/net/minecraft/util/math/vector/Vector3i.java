/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.vector;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.stream.IntStream;
import javax.annotation.concurrent.Immutable;
import net.minecraft.dispenser.IPosition;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

@Immutable
public class Vector3i
implements Comparable<Vector3i> {
    public static final Codec<Vector3i> CODEC = Codec.INT_STREAM.comapFlatMap(Vector3i::lambda$static$1, Vector3i::lambda$static$2);
    public static final Vector3i NULL_VECTOR = new Vector3i(0, 0, 0);
    private int x;
    private int y;
    private int z;

    public Vector3i(int n, int n2, int n3) {
        this.x = n;
        this.y = n2;
        this.z = n3;
    }

    public Vector3i(double d, double d2, double d3) {
        this(MathHelper.floor(d), MathHelper.floor(d2), MathHelper.floor(d3));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Vector3i)) {
            return true;
        }
        Vector3i vector3i = (Vector3i)object;
        if (this.getX() != vector3i.getX()) {
            return true;
        }
        if (this.getY() != vector3i.getY()) {
            return true;
        }
        return this.getZ() == vector3i.getZ();
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    @Override
    public int compareTo(Vector3i vector3i) {
        if (this.getY() == vector3i.getY()) {
            return this.getZ() == vector3i.getZ() ? this.getX() - vector3i.getX() : this.getZ() - vector3i.getZ();
        }
        return this.getY() - vector3i.getY();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    protected void setX(int n) {
        this.x = n;
    }

    protected void setY(int n) {
        this.y = n;
    }

    protected void setZ(int n) {
        this.z = n;
    }

    public Vector3i up() {
        return this.up(1);
    }

    public Vector3i up(int n) {
        return this.offset(Direction.UP, n);
    }

    public Vector3i down() {
        return this.down(1);
    }

    public Vector3i down(int n) {
        return this.offset(Direction.DOWN, n);
    }

    public Vector3i offset(Direction direction, int n) {
        return n == 0 ? this : new Vector3i(this.getX() + direction.getXOffset() * n, this.getY() + direction.getYOffset() * n, this.getZ() + direction.getZOffset() * n);
    }

    public Vector3i crossProduct(Vector3i vector3i) {
        return new Vector3i(this.getY() * vector3i.getZ() - this.getZ() * vector3i.getY(), this.getZ() * vector3i.getX() - this.getX() * vector3i.getZ(), this.getX() * vector3i.getY() - this.getY() * vector3i.getX());
    }

    public boolean withinDistance(Vector3i vector3i, double d) {
        return this.distanceSq(vector3i.getX(), vector3i.getY(), vector3i.getZ(), true) < d * d;
    }

    public boolean withinDistance(IPosition iPosition, double d) {
        return this.distanceSq(iPosition.getX(), iPosition.getY(), iPosition.getZ(), false) < d * d;
    }

    public double distanceSq(Vector3i vector3i) {
        return this.distanceSq(vector3i.getX(), vector3i.getY(), vector3i.getZ(), false);
    }

    public double distanceSq(IPosition iPosition, boolean bl) {
        return this.distanceSq(iPosition.getX(), iPosition.getY(), iPosition.getZ(), bl);
    }

    public double distanceSq(double d, double d2, double d3, boolean bl) {
        double d4 = bl ? 0.5 : 0.0;
        double d5 = (double)this.getX() + d4 - d;
        double d6 = (double)this.getY() + d4 - d2;
        double d7 = (double)this.getZ() + d4 - d3;
        return d5 * d5 + d6 * d6 + d7 * d7;
    }

    public int manhattanDistance(Vector3i vector3i) {
        float f = Math.abs(vector3i.getX() - this.getX());
        float f2 = Math.abs(vector3i.getY() - this.getY());
        float f3 = Math.abs(vector3i.getZ() - this.getZ());
        return (int)(f + f2 + f3);
    }

    public int func_243648_a(Direction.Axis axis) {
        return axis.getCoordinate(this.x, this.y, this.z);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }

    public String getCoordinatesAsString() {
        return this.getX() + ", " + this.getY() + ", " + this.getZ();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Vector3i)object);
    }

    private static IntStream lambda$static$2(Vector3i vector3i) {
        return IntStream.of(vector3i.getX(), vector3i.getY(), vector3i.getZ());
    }

    private static DataResult lambda$static$1(IntStream intStream) {
        return Util.validateIntStreamSize(intStream, 3).map(Vector3i::lambda$static$0);
    }

    private static Vector3i lambda$static$0(int[] nArray) {
        return new Vector3i(nArray[0], nArray[1], nArray[2]);
    }
}

