/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.AxisRotation;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.OffsetDoubleList;
import net.minecraft.util.math.shapes.SplitVoxelShape;
import net.minecraft.util.math.shapes.VoxelShapeArray;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;

public abstract class VoxelShape {
    protected final VoxelShapePart part;
    @Nullable
    private VoxelShape[] projectionCache;

    VoxelShape(VoxelShapePart voxelShapePart) {
        this.part = voxelShapePart;
    }

    public double getStart(Direction.Axis axis) {
        int n = this.part.getStart(axis);
        return n >= this.part.getSize(axis) ? Double.POSITIVE_INFINITY : this.getValueUnchecked(axis, n);
    }

    public double getEnd(Direction.Axis axis) {
        int n = this.part.getEnd(axis);
        return n <= 0 ? Double.NEGATIVE_INFINITY : this.getValueUnchecked(axis, n);
    }

    public AxisAlignedBB getBoundingBox() {
        if (this.isEmpty()) {
            throw Util.pauseDevMode(new UnsupportedOperationException("No bounds for empty shape."));
        }
        return new AxisAlignedBB(this.getStart(Direction.Axis.X), this.getStart(Direction.Axis.Y), this.getStart(Direction.Axis.Z), this.getEnd(Direction.Axis.X), this.getEnd(Direction.Axis.Y), this.getEnd(Direction.Axis.Z));
    }

    protected double getValueUnchecked(Direction.Axis axis, int n) {
        return this.getValues(axis).getDouble(n);
    }

    protected abstract DoubleList getValues(Direction.Axis var1);

    public boolean isEmpty() {
        return this.part.isEmpty();
    }

    public VoxelShape withOffset(double d, double d2, double d3) {
        return this.isEmpty() ? VoxelShapes.empty() : new VoxelShapeArray(this.part, new OffsetDoubleList(this.getValues(Direction.Axis.X), d), new OffsetDoubleList(this.getValues(Direction.Axis.Y), d2), new OffsetDoubleList(this.getValues(Direction.Axis.Z), d3));
    }

    public VoxelShape simplify() {
        VoxelShape[] voxelShapeArray = new VoxelShape[]{VoxelShapes.empty()};
        this.forEachBox((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> VoxelShape.lambda$simplify$0(voxelShapeArray, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5));
        return voxelShapeArray[0];
    }

    public void forEachEdge(VoxelShapes.ILineConsumer iLineConsumer) {
        this.part.forEachEdge((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> this.lambda$forEachEdge$1(iLineConsumer, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5), false);
    }

    public void forEachBox(VoxelShapes.ILineConsumer iLineConsumer) {
        DoubleList doubleList = this.getValues(Direction.Axis.X);
        DoubleList doubleList2 = this.getValues(Direction.Axis.Y);
        DoubleList doubleList3 = this.getValues(Direction.Axis.Z);
        this.part.forEachBox((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> VoxelShape.lambda$forEachBox$2(iLineConsumer, doubleList, doubleList2, doubleList3, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5), false);
    }

    public List<AxisAlignedBB> toBoundingBoxList() {
        ArrayList<AxisAlignedBB> arrayList = Lists.newArrayList();
        this.forEachBox((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> VoxelShape.lambda$toBoundingBoxList$3(arrayList, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5));
        return arrayList;
    }

    public double max(Direction.Axis axis, double d, double d2) {
        int n;
        Direction.Axis axis2 = AxisRotation.FORWARD.rotate(axis);
        Direction.Axis axis3 = AxisRotation.BACKWARD.rotate(axis);
        int n2 = this.getClosestIndex(axis2, d);
        int n3 = this.part.lastFilled(axis, n2, n = this.getClosestIndex(axis3, d2));
        return n3 <= 0 ? Double.NEGATIVE_INFINITY : this.getValueUnchecked(axis, n3);
    }

    protected int getClosestIndex(Direction.Axis axis, double d) {
        return MathHelper.binarySearch(0, this.part.getSize(axis) + 1, arg_0 -> this.lambda$getClosestIndex$4(axis, d, arg_0)) - 1;
    }

    protected boolean contains(double d, double d2, double d3) {
        return this.part.contains(this.getClosestIndex(Direction.Axis.X, d), this.getClosestIndex(Direction.Axis.Y, d2), this.getClosestIndex(Direction.Axis.Z, d3));
    }

    @Nullable
    public BlockRayTraceResult rayTrace(Vector3d vector3d, Vector3d vector3d2, BlockPos blockPos) {
        if (this.isEmpty()) {
            return null;
        }
        Vector3d vector3d3 = vector3d2.subtract(vector3d);
        if (vector3d3.lengthSquared() < 1.0E-7) {
            return null;
        }
        Vector3d vector3d4 = vector3d.add(vector3d3.scale(0.001));
        return this.contains(vector3d4.x - (double)blockPos.getX(), vector3d4.y - (double)blockPos.getY(), vector3d4.z - (double)blockPos.getZ()) ? new BlockRayTraceResult(vector3d4, Direction.getFacingFromVector(vector3d3.x, vector3d3.y, vector3d3.z).getOpposite(), blockPos, true) : AxisAlignedBB.rayTrace(this.toBoundingBoxList(), vector3d, vector3d2, blockPos);
    }

    public VoxelShape project(Direction direction) {
        if (!this.isEmpty() && this != VoxelShapes.fullCube()) {
            VoxelShape voxelShape;
            if (this.projectionCache != null) {
                voxelShape = this.projectionCache[direction.ordinal()];
                if (voxelShape != null) {
                    return voxelShape;
                }
            } else {
                this.projectionCache = new VoxelShape[6];
            }
            this.projectionCache[direction.ordinal()] = voxelShape = this.doProject(direction);
            return voxelShape;
        }
        return this;
    }

    private VoxelShape doProject(Direction direction) {
        Direction.Axis axis = direction.getAxis();
        Direction.AxisDirection axisDirection = direction.getAxisDirection();
        DoubleList doubleList = this.getValues(axis);
        if (doubleList.size() == 2 && DoubleMath.fuzzyEquals(doubleList.getDouble(0), 0.0, 1.0E-7) && DoubleMath.fuzzyEquals(doubleList.getDouble(1), 1.0, 1.0E-7)) {
            return this;
        }
        int n = this.getClosestIndex(axis, axisDirection == Direction.AxisDirection.POSITIVE ? 0.9999999 : 1.0E-7);
        return new SplitVoxelShape(this, axis, n);
    }

    public double getAllowedOffset(Direction.Axis axis, AxisAlignedBB axisAlignedBB, double d) {
        return this.getAllowedOffset(AxisRotation.from(axis, Direction.Axis.X), axisAlignedBB, d);
    }

    protected double getAllowedOffset(AxisRotation axisRotation, AxisAlignedBB axisAlignedBB, double d) {
        block11: {
            int n;
            int n2;
            double d2;
            Direction.Axis axis;
            AxisRotation axisRotation2;
            block10: {
                if (this.isEmpty()) {
                    return d;
                }
                if (Math.abs(d) < 1.0E-7) {
                    return 0.0;
                }
                axisRotation2 = axisRotation.reverse();
                axis = axisRotation2.rotate(Direction.Axis.X);
                Direction.Axis axis2 = axisRotation2.rotate(Direction.Axis.Y);
                Direction.Axis axis3 = axisRotation2.rotate(Direction.Axis.Z);
                double d3 = axisAlignedBB.getMax(axis);
                d2 = axisAlignedBB.getMin(axis);
                int n3 = this.getClosestIndex(axis, d2 + 1.0E-7);
                int n4 = this.getClosestIndex(axis, d3 - 1.0E-7);
                int n5 = Math.max(0, this.getClosestIndex(axis2, axisAlignedBB.getMin(axis2) + 1.0E-7));
                n2 = Math.min(this.part.getSize(axis2), this.getClosestIndex(axis2, axisAlignedBB.getMax(axis2) - 1.0E-7) + 1);
                int n6 = Math.max(0, this.getClosestIndex(axis3, axisAlignedBB.getMin(axis3) + 1.0E-7));
                n = Math.min(this.part.getSize(axis3), this.getClosestIndex(axis3, axisAlignedBB.getMax(axis3) - 1.0E-7) + 1);
                int n7 = this.part.getSize(axis);
                if (!(d > 0.0)) break block10;
                for (int i = n4 + 1; i < n7; ++i) {
                    for (int j = n5; j < n2; ++j) {
                        for (int k = n6; k < n; ++k) {
                            if (!this.part.containsWithRotation(axisRotation2, i, j, k)) continue;
                            double d4 = this.getValueUnchecked(axis, i) - d3;
                            if (d4 >= -1.0E-7) {
                                d = Math.min(d, d4);
                            }
                            return d;
                        }
                    }
                }
                break block11;
            }
            if (!(d < 0.0)) break block11;
            for (int i = n3 - 1; i >= 0; --i) {
                for (int j = n5; j < n2; ++j) {
                    for (int k = n6; k < n; ++k) {
                        if (!this.part.containsWithRotation(axisRotation2, i, j, k)) continue;
                        double d5 = this.getValueUnchecked(axis, i + 1) - d2;
                        if (d5 <= 1.0E-7) {
                            d = Math.max(d, d5);
                        }
                        return d;
                    }
                }
            }
        }
        return d;
    }

    public String toString() {
        return this.isEmpty() ? "EMPTY" : "VoxelShape[" + this.getBoundingBox() + "]";
    }

    private boolean lambda$getClosestIndex$4(Direction.Axis axis, double d, int n) {
        if (n < 0) {
            return true;
        }
        if (n > this.part.getSize(axis)) {
            return false;
        }
        return d < this.getValueUnchecked(axis, n);
    }

    private static void lambda$toBoundingBoxList$3(List list, double d, double d2, double d3, double d4, double d5, double d6) {
        list.add(new AxisAlignedBB(d, d2, d3, d4, d5, d6));
    }

    private static void lambda$forEachBox$2(VoxelShapes.ILineConsumer iLineConsumer, DoubleList doubleList, DoubleList doubleList2, DoubleList doubleList3, int n, int n2, int n3, int n4, int n5, int n6) {
        iLineConsumer.consume(doubleList.getDouble(n), doubleList2.getDouble(n2), doubleList3.getDouble(n3), doubleList.getDouble(n4), doubleList2.getDouble(n5), doubleList3.getDouble(n6));
    }

    private void lambda$forEachEdge$1(VoxelShapes.ILineConsumer iLineConsumer, int n, int n2, int n3, int n4, int n5, int n6) {
        iLineConsumer.consume(this.getValueUnchecked(Direction.Axis.X, n), this.getValueUnchecked(Direction.Axis.Y, n2), this.getValueUnchecked(Direction.Axis.Z, n3), this.getValueUnchecked(Direction.Axis.X, n4), this.getValueUnchecked(Direction.Axis.Y, n5), this.getValueUnchecked(Direction.Axis.Z, n6));
    }

    private static void lambda$simplify$0(VoxelShape[] voxelShapeArray, double d, double d2, double d3, double d4, double d5, double d6) {
        voxelShapeArray[0] = VoxelShapes.combine(voxelShapeArray[0], VoxelShapes.create(d, d2, d3, d4, d5, d6), IBooleanFunction.OR);
    }
}

