/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3d;

public class AxisAlignedBB {
    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    public AxisAlignedBB(double d, double d2, double d3, double d4, double d5, double d6) {
        this.minX = Math.min(d, d4);
        this.minY = Math.min(d2, d5);
        this.minZ = Math.min(d3, d6);
        this.maxX = Math.max(d, d4);
        this.maxY = Math.max(d2, d5);
        this.maxZ = Math.max(d3, d6);
    }

    public AxisAlignedBB(BlockPos blockPos) {
        this(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1);
    }

    public AxisAlignedBB(BlockPos blockPos, BlockPos blockPos2) {
        this(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos2.getX(), blockPos2.getY(), blockPos2.getZ());
    }

    public AxisAlignedBB(Vector3d vector3d, Vector3d vector3d2) {
        this(vector3d.x, vector3d.y, vector3d.z, vector3d2.x, vector3d2.y, vector3d2.z);
    }

    public static AxisAlignedBB toImmutable(MutableBoundingBox mutableBoundingBox) {
        return new AxisAlignedBB(mutableBoundingBox.minX, mutableBoundingBox.minY, mutableBoundingBox.minZ, mutableBoundingBox.maxX + 1, mutableBoundingBox.maxY + 1, mutableBoundingBox.maxZ + 1);
    }

    public static AxisAlignedBB fromVector(Vector3d vector3d) {
        return new AxisAlignedBB(vector3d.x, vector3d.y, vector3d.z, vector3d.x + 1.0, vector3d.y + 1.0, vector3d.z + 1.0);
    }

    public double getMin(Direction.Axis axis) {
        return axis.getCoordinate(this.minX, this.minY, this.minZ);
    }

    public double getMax(Direction.Axis axis) {
        return axis.getCoordinate(this.maxX, this.maxY, this.maxZ);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof AxisAlignedBB)) {
            return true;
        }
        AxisAlignedBB axisAlignedBB = (AxisAlignedBB)object;
        if (Double.compare(axisAlignedBB.minX, this.minX) != 0) {
            return true;
        }
        if (Double.compare(axisAlignedBB.minY, this.minY) != 0) {
            return true;
        }
        if (Double.compare(axisAlignedBB.minZ, this.minZ) != 0) {
            return true;
        }
        if (Double.compare(axisAlignedBB.maxX, this.maxX) != 0) {
            return true;
        }
        if (Double.compare(axisAlignedBB.maxY, this.maxY) != 0) {
            return true;
        }
        return Double.compare(axisAlignedBB.maxZ, this.maxZ) == 0;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.minX);
        int n = (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.minY);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.minZ);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.maxX);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.maxY);
        n = 31 * n + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.maxZ);
        return 31 * n + (int)(l ^ l >>> 32);
    }

    public AxisAlignedBB contract(double d, double d2, double d3) {
        double d4 = this.minX;
        double d5 = this.minY;
        double d6 = this.minZ;
        double d7 = this.maxX;
        double d8 = this.maxY;
        double d9 = this.maxZ;
        if (d < 0.0) {
            d4 -= d;
        } else if (d > 0.0) {
            d7 -= d;
        }
        if (d2 < 0.0) {
            d5 -= d2;
        } else if (d2 > 0.0) {
            d8 -= d2;
        }
        if (d3 < 0.0) {
            d6 -= d3;
        } else if (d3 > 0.0) {
            d9 -= d3;
        }
        return new AxisAlignedBB(d4, d5, d6, d7, d8, d9);
    }

    public AxisAlignedBB expand(Vector3d vector3d) {
        return this.expand(vector3d.x, vector3d.y, vector3d.z);
    }

    public AxisAlignedBB expand(double d, double d2, double d3) {
        double d4 = this.minX;
        double d5 = this.minY;
        double d6 = this.minZ;
        double d7 = this.maxX;
        double d8 = this.maxY;
        double d9 = this.maxZ;
        if (d < 0.0) {
            d4 += d;
        } else if (d > 0.0) {
            d7 += d;
        }
        if (d2 < 0.0) {
            d5 += d2;
        } else if (d2 > 0.0) {
            d8 += d2;
        }
        if (d3 < 0.0) {
            d6 += d3;
        } else if (d3 > 0.0) {
            d9 += d3;
        }
        return new AxisAlignedBB(d4, d5, d6, d7, d8, d9);
    }

    public AxisAlignedBB grow(double d, double d2, double d3) {
        double d4 = this.minX - d;
        double d5 = this.minY - d2;
        double d6 = this.minZ - d3;
        double d7 = this.maxX + d;
        double d8 = this.maxY + d2;
        double d9 = this.maxZ + d3;
        return new AxisAlignedBB(d4, d5, d6, d7, d8, d9);
    }

    public AxisAlignedBB grow(double d) {
        return this.grow(d, d, d);
    }

    public AxisAlignedBB intersect(AxisAlignedBB axisAlignedBB) {
        double d = Math.max(this.minX, axisAlignedBB.minX);
        double d2 = Math.max(this.minY, axisAlignedBB.minY);
        double d3 = Math.max(this.minZ, axisAlignedBB.minZ);
        double d4 = Math.min(this.maxX, axisAlignedBB.maxX);
        double d5 = Math.min(this.maxY, axisAlignedBB.maxY);
        double d6 = Math.min(this.maxZ, axisAlignedBB.maxZ);
        return new AxisAlignedBB(d, d2, d3, d4, d5, d6);
    }

    public AxisAlignedBB union(AxisAlignedBB axisAlignedBB) {
        double d = Math.min(this.minX, axisAlignedBB.minX);
        double d2 = Math.min(this.minY, axisAlignedBB.minY);
        double d3 = Math.min(this.minZ, axisAlignedBB.minZ);
        double d4 = Math.max(this.maxX, axisAlignedBB.maxX);
        double d5 = Math.max(this.maxY, axisAlignedBB.maxY);
        double d6 = Math.max(this.maxZ, axisAlignedBB.maxZ);
        return new AxisAlignedBB(d, d2, d3, d4, d5, d6);
    }

    public AxisAlignedBB offset(double d, double d2, double d3) {
        return new AxisAlignedBB(this.minX + d, this.minY + d2, this.minZ + d3, this.maxX + d, this.maxY + d2, this.maxZ + d3);
    }

    public AxisAlignedBB offset(BlockPos blockPos) {
        return new AxisAlignedBB(this.minX + (double)blockPos.getX(), this.minY + (double)blockPos.getY(), this.minZ + (double)blockPos.getZ(), this.maxX + (double)blockPos.getX(), this.maxY + (double)blockPos.getY(), this.maxZ + (double)blockPos.getZ());
    }

    public AxisAlignedBB offset(Vector3d vector3d) {
        return this.offset(vector3d.x, vector3d.y, vector3d.z);
    }

    public boolean intersects(AxisAlignedBB axisAlignedBB) {
        return this.intersects(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ);
    }

    public boolean intersects(double d, double d2, double d3, double d4, double d5, double d6) {
        return this.minX < d4 && this.maxX > d && this.minY < d5 && this.maxY > d2 && this.minZ < d6 && this.maxZ > d3;
    }

    public boolean intersects(Vector3d vector3d, Vector3d vector3d2) {
        return this.intersects(Math.min(vector3d.x, vector3d2.x), Math.min(vector3d.y, vector3d2.y), Math.min(vector3d.z, vector3d2.z), Math.max(vector3d.x, vector3d2.x), Math.max(vector3d.y, vector3d2.y), Math.max(vector3d.z, vector3d2.z));
    }

    public boolean contains(Vector3d vector3d) {
        return this.contains(vector3d.x, vector3d.y, vector3d.z);
    }

    public boolean contains(double d, double d2, double d3) {
        return d >= this.minX && d < this.maxX && d2 >= this.minY && d2 < this.maxY && d3 >= this.minZ && d3 < this.maxZ;
    }

    public double getAverageEdgeLength() {
        double d = this.getXSize();
        double d2 = this.getYSize();
        double d3 = this.getZSize();
        return (d + d2 + d3) / 3.0;
    }

    public double getXSize() {
        return this.maxX - this.minX;
    }

    public double getYSize() {
        return this.maxY - this.minY;
    }

    public double getZSize() {
        return this.maxZ - this.minZ;
    }

    public AxisAlignedBB shrink(double d) {
        return this.grow(-d);
    }

    public Optional<Vector3d> rayTrace(Vector3d vector3d, Vector3d vector3d2) {
        double[] dArray = new double[]{1.0};
        double d = vector3d2.x - vector3d.x;
        double d2 = vector3d2.y - vector3d.y;
        double d3 = vector3d2.z - vector3d.z;
        Direction direction = AxisAlignedBB.calcSideHit(this, vector3d, dArray, null, d, d2, d3);
        if (direction == null) {
            return Optional.empty();
        }
        double d4 = dArray[0];
        return Optional.of(vector3d.add(d4 * d, d4 * d2, d4 * d3));
    }

    @Nullable
    public static BlockRayTraceResult rayTrace(Iterable<AxisAlignedBB> iterable, Vector3d vector3d, Vector3d vector3d2, BlockPos blockPos) {
        double[] dArray = new double[]{1.0};
        Direction direction = null;
        double d = vector3d2.x - vector3d.x;
        double d2 = vector3d2.y - vector3d.y;
        double d3 = vector3d2.z - vector3d.z;
        for (AxisAlignedBB axisAlignedBB : iterable) {
            direction = AxisAlignedBB.calcSideHit(axisAlignedBB.offset(blockPos), vector3d, dArray, direction, d, d2, d3);
        }
        if (direction == null) {
            return null;
        }
        double d4 = dArray[0];
        return new BlockRayTraceResult(vector3d.add(d4 * d, d4 * d2, d4 * d3), direction, blockPos, false);
    }

    @Nullable
    public static Direction calcSideHit(AxisAlignedBB axisAlignedBB, Vector3d vector3d, double[] dArray, @Nullable Direction direction, double d, double d2, double d3) {
        if (d > 1.0E-7) {
            direction = AxisAlignedBB.checkSideForHit(dArray, direction, d, d2, d3, axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxY, axisAlignedBB.minZ, axisAlignedBB.maxZ, Direction.WEST, vector3d.x, vector3d.y, vector3d.z);
        } else if (d < -1.0E-7) {
            direction = AxisAlignedBB.checkSideForHit(dArray, direction, d, d2, d3, axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxY, axisAlignedBB.minZ, axisAlignedBB.maxZ, Direction.EAST, vector3d.x, vector3d.y, vector3d.z);
        }
        if (d2 > 1.0E-7) {
            direction = AxisAlignedBB.checkSideForHit(dArray, direction, d2, d3, d, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxZ, axisAlignedBB.minX, axisAlignedBB.maxX, Direction.DOWN, vector3d.y, vector3d.z, vector3d.x);
        } else if (d2 < -1.0E-7) {
            direction = AxisAlignedBB.checkSideForHit(dArray, direction, d2, d3, d, axisAlignedBB.maxY, axisAlignedBB.minZ, axisAlignedBB.maxZ, axisAlignedBB.minX, axisAlignedBB.maxX, Direction.UP, vector3d.y, vector3d.z, vector3d.x);
        }
        if (d3 > 1.0E-7) {
            direction = AxisAlignedBB.checkSideForHit(dArray, direction, d3, d, d2, axisAlignedBB.minZ, axisAlignedBB.minX, axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxY, Direction.NORTH, vector3d.z, vector3d.x, vector3d.y);
        } else if (d3 < -1.0E-7) {
            direction = AxisAlignedBB.checkSideForHit(dArray, direction, d3, d, d2, axisAlignedBB.maxZ, axisAlignedBB.minX, axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxY, Direction.SOUTH, vector3d.z, vector3d.x, vector3d.y);
        }
        return direction;
    }

    @Nullable
    private static Direction checkSideForHit(double[] dArray, @Nullable Direction direction, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, Direction direction2, double d9, double d10, double d11) {
        double d12 = (d4 - d9) / d;
        double d13 = d10 + d12 * d2;
        double d14 = d11 + d12 * d3;
        if (0.0 < d12 && d12 < dArray[0] && d5 - 1.0E-7 < d13 && d13 < d6 + 1.0E-7 && d7 - 1.0E-7 < d14 && d14 < d8 + 1.0E-7) {
            dArray[0] = d12;
            return direction2;
        }
        return direction;
    }

    public String toString() {
        return "AABB[" + this.minX + ", " + this.minY + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }

    public boolean hasNaN() {
        return Double.isNaN(this.minX) || Double.isNaN(this.minY) || Double.isNaN(this.minZ) || Double.isNaN(this.maxX) || Double.isNaN(this.maxY) || Double.isNaN(this.maxZ);
    }

    public Vector3d getCenter() {
        return new Vector3d(MathHelper.lerp(0.5, this.minX, this.maxX), MathHelper.lerp(0.5, this.minY, this.maxY), MathHelper.lerp(0.5, this.minZ, this.maxZ));
    }

    public static AxisAlignedBB withSizeAtOrigin(double d, double d2, double d3) {
        return new AxisAlignedBB(-d / 2.0, -d2 / 2.0, -d3 / 2.0, d / 2.0, d2 / 2.0, d3 / 2.0);
    }
}

