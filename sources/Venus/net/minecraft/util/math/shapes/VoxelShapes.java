/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.AxisRotation;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.DoubleCubeMergingList;
import net.minecraft.util.math.shapes.DoubleRangeList;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.IDoubleListMerger;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.IndirectMerger;
import net.minecraft.util.math.shapes.NonOverlappingMerger;
import net.minecraft.util.math.shapes.SimpleDoubleMerger;
import net.minecraft.util.math.shapes.SplitVoxelShape;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapeArray;
import net.minecraft.util.math.shapes.VoxelShapeCube;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.world.IWorldReader;

public final class VoxelShapes {
    private static final VoxelShape FULL_CUBE = Util.make(VoxelShapes::lambda$static$0);
    public static final VoxelShape INFINITY = VoxelShapes.create(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    private static final VoxelShape EMPTY = new VoxelShapeArray((VoxelShapePart)new BitSetVoxelShapePart(0, 0, 0), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0}));

    public static VoxelShape empty() {
        return EMPTY;
    }

    public static VoxelShape fullCube() {
        return FULL_CUBE;
    }

    public static VoxelShape create(double d, double d2, double d3, double d4, double d5, double d6) {
        return VoxelShapes.create(new AxisAlignedBB(d, d2, d3, d4, d5, d6));
    }

    public static VoxelShape create(AxisAlignedBB axisAlignedBB) {
        int n = VoxelShapes.getPrecisionBits(axisAlignedBB.minX, axisAlignedBB.maxX);
        int n2 = VoxelShapes.getPrecisionBits(axisAlignedBB.minY, axisAlignedBB.maxY);
        int n3 = VoxelShapes.getPrecisionBits(axisAlignedBB.minZ, axisAlignedBB.maxZ);
        if (n >= 0 && n2 >= 0 && n3 >= 0) {
            if (n == 0 && n2 == 0 && n3 == 0) {
                return axisAlignedBB.contains(0.5, 0.5, 0.5) ? VoxelShapes.fullCube() : VoxelShapes.empty();
            }
            int n4 = 1 << n;
            int n5 = 1 << n2;
            int n6 = 1 << n3;
            int n7 = (int)Math.round(axisAlignedBB.minX * (double)n4);
            int n8 = (int)Math.round(axisAlignedBB.maxX * (double)n4);
            int n9 = (int)Math.round(axisAlignedBB.minY * (double)n5);
            int n10 = (int)Math.round(axisAlignedBB.maxY * (double)n5);
            int n11 = (int)Math.round(axisAlignedBB.minZ * (double)n6);
            int n12 = (int)Math.round(axisAlignedBB.maxZ * (double)n6);
            BitSetVoxelShapePart bitSetVoxelShapePart = new BitSetVoxelShapePart(n4, n5, n6, n7, n9, n11, n8, n10, n12);
            for (long i = (long)n7; i < (long)n8; ++i) {
                for (long j = (long)n9; j < (long)n10; ++j) {
                    for (long k = (long)n11; k < (long)n12; ++k) {
                        bitSetVoxelShapePart.setFilled((int)i, (int)j, (int)k, false, false);
                    }
                }
            }
            return new VoxelShapeCube(bitSetVoxelShapePart);
        }
        return new VoxelShapeArray(VoxelShapes.FULL_CUBE.part, new double[]{axisAlignedBB.minX, axisAlignedBB.maxX}, new double[]{axisAlignedBB.minY, axisAlignedBB.maxY}, new double[]{axisAlignedBB.minZ, axisAlignedBB.maxZ});
    }

    private static int getPrecisionBits(double d, double d2) {
        if (!(d < -1.0E-7) && !(d2 > 1.0000001)) {
            for (int i = 0; i <= 3; ++i) {
                boolean bl;
                double d3 = d * (double)(1 << i);
                double d4 = d2 * (double)(1 << i);
                boolean bl2 = Math.abs(d3 - Math.floor(d3)) < 1.0E-7;
                boolean bl3 = bl = Math.abs(d4 - Math.floor(d4)) < 1.0E-7;
                if (!bl2 || !bl) continue;
                return i;
            }
            return 1;
        }
        return 1;
    }

    protected static long lcm(int n, int n2) {
        return (long)n * (long)(n2 / IntMath.gcd(n, n2));
    }

    public static VoxelShape or(VoxelShape voxelShape, VoxelShape voxelShape2) {
        return VoxelShapes.combineAndSimplify(voxelShape, voxelShape2, IBooleanFunction.OR);
    }

    public static VoxelShape or(VoxelShape voxelShape, VoxelShape ... voxelShapeArray) {
        return Arrays.stream(voxelShapeArray).reduce(voxelShape, VoxelShapes::or);
    }

    public static VoxelShape combineAndSimplify(VoxelShape voxelShape, VoxelShape voxelShape2, IBooleanFunction iBooleanFunction) {
        return VoxelShapes.combine(voxelShape, voxelShape2, iBooleanFunction).simplify();
    }

    public static VoxelShape combine(VoxelShape voxelShape, VoxelShape voxelShape2, IBooleanFunction iBooleanFunction) {
        if (iBooleanFunction.apply(false, false)) {
            throw Util.pauseDevMode(new IllegalArgumentException());
        }
        if (voxelShape == voxelShape2) {
            return iBooleanFunction.apply(true, true) ? voxelShape : VoxelShapes.empty();
        }
        boolean bl = iBooleanFunction.apply(true, false);
        boolean bl2 = iBooleanFunction.apply(false, true);
        if (voxelShape.isEmpty()) {
            return bl2 ? voxelShape2 : VoxelShapes.empty();
        }
        if (voxelShape2.isEmpty()) {
            return bl ? voxelShape : VoxelShapes.empty();
        }
        IDoubleListMerger iDoubleListMerger = VoxelShapes.makeListMerger(1, voxelShape.getValues(Direction.Axis.X), voxelShape2.getValues(Direction.Axis.X), bl, bl2);
        IDoubleListMerger iDoubleListMerger2 = VoxelShapes.makeListMerger(iDoubleListMerger.func_212435_a().size() - 1, voxelShape.getValues(Direction.Axis.Y), voxelShape2.getValues(Direction.Axis.Y), bl, bl2);
        IDoubleListMerger iDoubleListMerger3 = VoxelShapes.makeListMerger((iDoubleListMerger.func_212435_a().size() - 1) * (iDoubleListMerger2.func_212435_a().size() - 1), voxelShape.getValues(Direction.Axis.Z), voxelShape2.getValues(Direction.Axis.Z), bl, bl2);
        BitSetVoxelShapePart bitSetVoxelShapePart = BitSetVoxelShapePart.func_197852_a(voxelShape.part, voxelShape2.part, iDoubleListMerger, iDoubleListMerger2, iDoubleListMerger3, iBooleanFunction);
        return iDoubleListMerger instanceof DoubleCubeMergingList && iDoubleListMerger2 instanceof DoubleCubeMergingList && iDoubleListMerger3 instanceof DoubleCubeMergingList ? new VoxelShapeCube(bitSetVoxelShapePart) : new VoxelShapeArray((VoxelShapePart)bitSetVoxelShapePart, iDoubleListMerger.func_212435_a(), iDoubleListMerger2.func_212435_a(), iDoubleListMerger3.func_212435_a());
    }

    public static boolean compare(VoxelShape voxelShape, VoxelShape voxelShape2, IBooleanFunction iBooleanFunction) {
        if (iBooleanFunction.apply(false, false)) {
            throw Util.pauseDevMode(new IllegalArgumentException());
        }
        if (voxelShape == voxelShape2) {
            return iBooleanFunction.apply(true, true);
        }
        if (voxelShape.isEmpty()) {
            return iBooleanFunction.apply(false, !voxelShape2.isEmpty());
        }
        if (voxelShape2.isEmpty()) {
            return iBooleanFunction.apply(!voxelShape.isEmpty(), false);
        }
        boolean bl = iBooleanFunction.apply(true, false);
        boolean bl2 = iBooleanFunction.apply(false, true);
        for (Direction.Axis axis : AxisRotation.AXES) {
            if (voxelShape.getEnd(axis) < voxelShape2.getStart(axis) - 1.0E-7) {
                return bl || bl2;
            }
            if (!(voxelShape2.getEnd(axis) < voxelShape.getStart(axis) - 1.0E-7)) continue;
            return bl || bl2;
        }
        IDoubleListMerger iDoubleListMerger = VoxelShapes.makeListMerger(1, voxelShape.getValues(Direction.Axis.X), voxelShape2.getValues(Direction.Axis.X), bl, bl2);
        IDoubleListMerger iDoubleListMerger2 = VoxelShapes.makeListMerger(iDoubleListMerger.func_212435_a().size() - 1, voxelShape.getValues(Direction.Axis.Y), voxelShape2.getValues(Direction.Axis.Y), bl, bl2);
        IDoubleListMerger iDoubleListMerger3 = VoxelShapes.makeListMerger((iDoubleListMerger.func_212435_a().size() - 1) * (iDoubleListMerger2.func_212435_a().size() - 1), voxelShape.getValues(Direction.Axis.Z), voxelShape2.getValues(Direction.Axis.Z), bl, bl2);
        return VoxelShapes.join(iDoubleListMerger, iDoubleListMerger2, iDoubleListMerger3, voxelShape.part, voxelShape2.part, iBooleanFunction);
    }

    private static boolean join(IDoubleListMerger iDoubleListMerger, IDoubleListMerger iDoubleListMerger2, IDoubleListMerger iDoubleListMerger3, VoxelShapePart voxelShapePart, VoxelShapePart voxelShapePart2, IBooleanFunction iBooleanFunction) {
        return !iDoubleListMerger.forMergedIndexes((arg_0, arg_1, arg_2) -> VoxelShapes.lambda$join$3(iDoubleListMerger2, iDoubleListMerger3, iBooleanFunction, voxelShapePart, voxelShapePart2, arg_0, arg_1, arg_2));
    }

    public static double getAllowedOffset(Direction.Axis axis, AxisAlignedBB axisAlignedBB, Stream<VoxelShape> stream, double d) {
        Iterator iterator2 = stream.iterator();
        while (iterator2.hasNext()) {
            if (Math.abs(d) < 1.0E-7) {
                return 0.0;
            }
            d = ((VoxelShape)iterator2.next()).getAllowedOffset(axis, axisAlignedBB, d);
        }
        return d;
    }

    public static double getAllowedOffset(Direction.Axis axis, AxisAlignedBB axisAlignedBB, IWorldReader iWorldReader, double d, ISelectionContext iSelectionContext, Stream<VoxelShape> stream) {
        return VoxelShapes.getAllowedOffset(axisAlignedBB, iWorldReader, d, iSelectionContext, AxisRotation.from(axis, Direction.Axis.Z), stream);
    }

    private static double getAllowedOffset(AxisAlignedBB axisAlignedBB, IWorldReader iWorldReader, double d, ISelectionContext iSelectionContext, AxisRotation axisRotation, Stream<VoxelShape> stream) {
        if (!(axisAlignedBB.getXSize() < 1.0E-6 || axisAlignedBB.getYSize() < 1.0E-6 || axisAlignedBB.getZSize() < 1.0E-6)) {
            if (Math.abs(d) < 1.0E-7) {
                return 0.0;
            }
            AxisRotation axisRotation2 = axisRotation.reverse();
            Direction.Axis axis = axisRotation2.rotate(Direction.Axis.X);
            Direction.Axis axis2 = axisRotation2.rotate(Direction.Axis.Y);
            Direction.Axis axis3 = axisRotation2.rotate(Direction.Axis.Z);
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            int n = MathHelper.floor(axisAlignedBB.getMin(axis) - 1.0E-7) - 1;
            int n2 = MathHelper.floor(axisAlignedBB.getMax(axis) + 1.0E-7) + 1;
            int n3 = MathHelper.floor(axisAlignedBB.getMin(axis2) - 1.0E-7) - 1;
            int n4 = MathHelper.floor(axisAlignedBB.getMax(axis2) + 1.0E-7) + 1;
            double d2 = axisAlignedBB.getMin(axis3) - 1.0E-7;
            double d3 = axisAlignedBB.getMax(axis3) + 1.0E-7;
            boolean bl = d > 0.0;
            int n5 = bl ? MathHelper.floor(axisAlignedBB.getMax(axis3) - 1.0E-7) - 1 : MathHelper.floor(axisAlignedBB.getMin(axis3) + 1.0E-7) + 1;
            int n6 = VoxelShapes.getDifferenceFloored(d, d2, d3);
            int n7 = bl ? 1 : -1;
            int n8 = n5;
            while (!(bl ? n8 > n6 : n8 < n6)) {
                for (int i = n; i <= n2; ++i) {
                    for (int j = n3; j <= n4; ++j) {
                        int n9 = 0;
                        if (i == n || i == n2) {
                            ++n9;
                        }
                        if (j == n3 || j == n4) {
                            ++n9;
                        }
                        if (n8 == n5 || n8 == n6) {
                            ++n9;
                        }
                        if (n9 >= 3) continue;
                        mutable.setPos(axisRotation2, i, j, n8);
                        BlockState blockState = iWorldReader.getBlockState(mutable);
                        if (n9 == 1 && !blockState.isCollisionShapeLargerThanFullBlock() || n9 == 2 && !blockState.isIn(Blocks.MOVING_PISTON)) continue;
                        d = blockState.getCollisionShape(iWorldReader, mutable, iSelectionContext).getAllowedOffset(axis3, axisAlignedBB.offset(-mutable.getX(), -mutable.getY(), -mutable.getZ()), d);
                        if (Math.abs(d) < 1.0E-7) {
                            return 0.0;
                        }
                        n6 = VoxelShapes.getDifferenceFloored(d, d2, d3);
                    }
                }
                n8 += n7;
            }
            double[] dArray = new double[]{d};
            stream.forEach(arg_0 -> VoxelShapes.lambda$getAllowedOffset$4(dArray, axis3, axisAlignedBB, arg_0));
            return dArray[0];
        }
        return d;
    }

    private static int getDifferenceFloored(double d, double d2, double d3) {
        return d > 0.0 ? MathHelper.floor(d3 + d) + 1 : MathHelper.floor(d2 + d) - 1;
    }

    public static boolean isCubeSideCovered(VoxelShape voxelShape, VoxelShape voxelShape2, Direction direction) {
        if (voxelShape == VoxelShapes.fullCube() && voxelShape2 == VoxelShapes.fullCube()) {
            return false;
        }
        if (voxelShape2.isEmpty()) {
            return true;
        }
        Direction.Axis axis = direction.getAxis();
        Direction.AxisDirection axisDirection = direction.getAxisDirection();
        VoxelShape voxelShape3 = axisDirection == Direction.AxisDirection.POSITIVE ? voxelShape : voxelShape2;
        VoxelShape voxelShape4 = axisDirection == Direction.AxisDirection.POSITIVE ? voxelShape2 : voxelShape;
        IBooleanFunction iBooleanFunction = axisDirection == Direction.AxisDirection.POSITIVE ? IBooleanFunction.ONLY_FIRST : IBooleanFunction.ONLY_SECOND;
        return DoubleMath.fuzzyEquals(voxelShape3.getEnd(axis), 1.0, 1.0E-7) && DoubleMath.fuzzyEquals(voxelShape4.getStart(axis), 0.0, 1.0E-7) && !VoxelShapes.compare(new SplitVoxelShape(voxelShape3, axis, voxelShape3.part.getSize(axis) - 1), new SplitVoxelShape(voxelShape4, axis, 0), iBooleanFunction);
    }

    public static VoxelShape getFaceShape(VoxelShape voxelShape, Direction direction) {
        int n;
        boolean bl;
        if (voxelShape == VoxelShapes.fullCube()) {
            return VoxelShapes.fullCube();
        }
        Direction.Axis axis = direction.getAxis();
        if (direction.getAxisDirection() == Direction.AxisDirection.POSITIVE) {
            bl = DoubleMath.fuzzyEquals(voxelShape.getEnd(axis), 1.0, 1.0E-7);
            n = voxelShape.part.getSize(axis) - 1;
        } else {
            bl = DoubleMath.fuzzyEquals(voxelShape.getStart(axis), 0.0, 1.0E-7);
            n = 0;
        }
        return !bl ? VoxelShapes.empty() : new SplitVoxelShape(voxelShape, axis, n);
    }

    public static boolean doAdjacentCubeSidesFillSquare(VoxelShape voxelShape, VoxelShape voxelShape2, Direction direction) {
        if (voxelShape != VoxelShapes.fullCube() && voxelShape2 != VoxelShapes.fullCube()) {
            VoxelShape voxelShape3;
            Direction.Axis axis = direction.getAxis();
            Direction.AxisDirection axisDirection = direction.getAxisDirection();
            VoxelShape voxelShape4 = axisDirection == Direction.AxisDirection.POSITIVE ? voxelShape : voxelShape2;
            VoxelShape voxelShape5 = voxelShape3 = axisDirection == Direction.AxisDirection.POSITIVE ? voxelShape2 : voxelShape;
            if (!DoubleMath.fuzzyEquals(voxelShape4.getEnd(axis), 1.0, 1.0E-7)) {
                voxelShape4 = VoxelShapes.empty();
            }
            if (!DoubleMath.fuzzyEquals(voxelShape3.getStart(axis), 0.0, 1.0E-7)) {
                voxelShape3 = VoxelShapes.empty();
            }
            return !VoxelShapes.compare(VoxelShapes.fullCube(), VoxelShapes.combine(new SplitVoxelShape(voxelShape4, axis, voxelShape4.part.getSize(axis) - 1), new SplitVoxelShape(voxelShape3, axis, 0), IBooleanFunction.OR), IBooleanFunction.ONLY_FIRST);
        }
        return false;
    }

    public static boolean faceShapeCovers(VoxelShape voxelShape, VoxelShape voxelShape2) {
        if (voxelShape != VoxelShapes.fullCube() && voxelShape2 != VoxelShapes.fullCube()) {
            if (voxelShape.isEmpty() && voxelShape2.isEmpty()) {
                return true;
            }
            return !VoxelShapes.compare(VoxelShapes.fullCube(), VoxelShapes.combine(voxelShape, voxelShape2, IBooleanFunction.OR), IBooleanFunction.ONLY_FIRST);
        }
        return false;
    }

    @VisibleForTesting
    protected static IDoubleListMerger makeListMerger(int n, DoubleList doubleList, DoubleList doubleList2, boolean bl, boolean bl2) {
        long l;
        int n2 = doubleList.size() - 1;
        int n3 = doubleList2.size() - 1;
        if (doubleList instanceof DoubleRangeList && doubleList2 instanceof DoubleRangeList && (long)n * (l = VoxelShapes.lcm(n2, n3)) <= 256L) {
            return new DoubleCubeMergingList(n2, n3);
        }
        if (doubleList.getDouble(n2) < doubleList2.getDouble(0) - 1.0E-7) {
            return new NonOverlappingMerger(doubleList, doubleList2, false);
        }
        if (doubleList2.getDouble(n3) < doubleList.getDouble(0) - 1.0E-7) {
            return new NonOverlappingMerger(doubleList2, doubleList, true);
        }
        if (n2 == n3 && Objects.equals(doubleList, doubleList2)) {
            if (doubleList instanceof SimpleDoubleMerger) {
                return (IDoubleListMerger)((Object)doubleList);
            }
            return doubleList2 instanceof SimpleDoubleMerger ? (IDoubleListMerger)((Object)doubleList2) : new SimpleDoubleMerger(doubleList);
        }
        return new IndirectMerger(doubleList, doubleList2, bl, bl2);
    }

    private static void lambda$getAllowedOffset$4(double[] dArray, Direction.Axis axis, AxisAlignedBB axisAlignedBB, VoxelShape voxelShape) {
        dArray[0] = voxelShape.getAllowedOffset(axis, axisAlignedBB, dArray[0]);
    }

    private static boolean lambda$join$3(IDoubleListMerger iDoubleListMerger, IDoubleListMerger iDoubleListMerger2, IBooleanFunction iBooleanFunction, VoxelShapePart voxelShapePart, VoxelShapePart voxelShapePart2, int n, int n2, int n3) {
        return iDoubleListMerger.forMergedIndexes((arg_0, arg_1, arg_2) -> VoxelShapes.lambda$join$2(iDoubleListMerger2, iBooleanFunction, voxelShapePart, n, voxelShapePart2, n2, arg_0, arg_1, arg_2));
    }

    private static boolean lambda$join$2(IDoubleListMerger iDoubleListMerger, IBooleanFunction iBooleanFunction, VoxelShapePart voxelShapePart, int n, VoxelShapePart voxelShapePart2, int n2, int n3, int n4, int n5) {
        return iDoubleListMerger.forMergedIndexes((arg_0, arg_1, arg_2) -> VoxelShapes.lambda$join$1(iBooleanFunction, voxelShapePart, n, n3, voxelShapePart2, n2, n4, arg_0, arg_1, arg_2));
    }

    private static boolean lambda$join$1(IBooleanFunction iBooleanFunction, VoxelShapePart voxelShapePart, int n, int n2, VoxelShapePart voxelShapePart2, int n3, int n4, int n5, int n6, int n7) {
        return !iBooleanFunction.apply(voxelShapePart.contains(n, n2, n5), voxelShapePart2.contains(n3, n4, n6));
    }

    private static VoxelShapeCube lambda$static$0() {
        BitSetVoxelShapePart bitSetVoxelShapePart = new BitSetVoxelShapePart(1, 1, 1);
        ((VoxelShapePart)bitSetVoxelShapePart).setFilled(0, 0, 0, true, false);
        return new VoxelShapeCube(bitSetVoxelShapePart);
    }

    public static interface ILineConsumer {
        public void consume(double var1, double var3, double var5, double var7, double var9, double var11);
    }
}

