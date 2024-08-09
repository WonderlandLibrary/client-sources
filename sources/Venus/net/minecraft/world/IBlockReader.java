/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;

public interface IBlockReader {
    @Nullable
    public TileEntity getTileEntity(BlockPos var1);

    public BlockState getBlockState(BlockPos var1);

    public FluidState getFluidState(BlockPos var1);

    default public int getLightValue(BlockPos blockPos) {
        return this.getBlockState(blockPos).getLightValue();
    }

    default public int getMaxLightLevel() {
        return 0;
    }

    default public int getHeight() {
        return 1;
    }

    default public Stream<BlockState> func_234853_a_(AxisAlignedBB axisAlignedBB) {
        return BlockPos.getAllInBox(axisAlignedBB).map(this::getBlockState);
    }

    default public BlockRayTraceResult rayTraceBlocks(RayTraceContext rayTraceContext) {
        return IBlockReader.doRayTrace(rayTraceContext, this::lambda$rayTraceBlocks$0, IBlockReader::lambda$rayTraceBlocks$1);
    }

    @Nullable
    default public BlockRayTraceResult rayTraceBlocks(Vector3d vector3d, Vector3d vector3d2, BlockPos blockPos, VoxelShape voxelShape, BlockState blockState) {
        BlockRayTraceResult blockRayTraceResult;
        BlockRayTraceResult blockRayTraceResult2 = voxelShape.rayTrace(vector3d, vector3d2, blockPos);
        if (blockRayTraceResult2 != null && (blockRayTraceResult = blockState.getRayTraceShape(this, blockPos).rayTrace(vector3d, vector3d2, blockPos)) != null && blockRayTraceResult.getHitVec().subtract(vector3d).lengthSquared() < blockRayTraceResult2.getHitVec().subtract(vector3d).lengthSquared()) {
            return blockRayTraceResult2.withFace(blockRayTraceResult.getFace());
        }
        return blockRayTraceResult2;
    }

    default public double func_242402_a(VoxelShape voxelShape, Supplier<VoxelShape> supplier) {
        if (!voxelShape.isEmpty()) {
            return voxelShape.getEnd(Direction.Axis.Y);
        }
        double d = supplier.get().getEnd(Direction.Axis.Y);
        return d >= 1.0 ? d - 1.0 : Double.NEGATIVE_INFINITY;
    }

    default public double func_242403_h(BlockPos blockPos) {
        return this.func_242402_a(this.getBlockState(blockPos).getCollisionShape(this, blockPos), () -> this.lambda$func_242403_h$2(blockPos));
    }

    public static <T> T doRayTrace(RayTraceContext rayTraceContext, BiFunction<RayTraceContext, BlockPos, T> biFunction, Function<RayTraceContext, T> function) {
        int n;
        int n2;
        Vector3d vector3d;
        Vector3d vector3d2 = rayTraceContext.getStartVec();
        if (vector3d2.equals(vector3d = rayTraceContext.getEndVec())) {
            return function.apply(rayTraceContext);
        }
        double d = MathHelper.lerp(-1.0E-7, vector3d.x, vector3d2.x);
        double d2 = MathHelper.lerp(-1.0E-7, vector3d.y, vector3d2.y);
        double d3 = MathHelper.lerp(-1.0E-7, vector3d.z, vector3d2.z);
        double d4 = MathHelper.lerp(-1.0E-7, vector3d2.x, vector3d.x);
        double d5 = MathHelper.lerp(-1.0E-7, vector3d2.y, vector3d.y);
        double d6 = MathHelper.lerp(-1.0E-7, vector3d2.z, vector3d.z);
        int n3 = MathHelper.floor(d4);
        BlockPos.Mutable mutable = new BlockPos.Mutable(n3, n2 = MathHelper.floor(d5), n = MathHelper.floor(d6));
        T t = biFunction.apply(rayTraceContext, mutable);
        if (t != null) {
            return t;
        }
        double d7 = d - d4;
        double d8 = d2 - d5;
        double d9 = d3 - d6;
        int n4 = MathHelper.signum(d7);
        int n5 = MathHelper.signum(d8);
        int n6 = MathHelper.signum(d9);
        double d10 = n4 == 0 ? Double.MAX_VALUE : (double)n4 / d7;
        double d11 = n5 == 0 ? Double.MAX_VALUE : (double)n5 / d8;
        double d12 = n6 == 0 ? Double.MAX_VALUE : (double)n6 / d9;
        double d13 = d10 * (n4 > 0 ? 1.0 - MathHelper.frac(d4) : MathHelper.frac(d4));
        double d14 = d11 * (n5 > 0 ? 1.0 - MathHelper.frac(d5) : MathHelper.frac(d5));
        double d15 = d12 * (n6 > 0 ? 1.0 - MathHelper.frac(d6) : MathHelper.frac(d6));
        while (d13 <= 1.0 || d14 <= 1.0 || d15 <= 1.0) {
            T t2;
            if (d13 < d14) {
                if (d13 < d15) {
                    n3 += n4;
                    d13 += d10;
                } else {
                    n += n6;
                    d15 += d12;
                }
            } else if (d14 < d15) {
                n2 += n5;
                d14 += d11;
            } else {
                n += n6;
                d15 += d12;
            }
            if ((t2 = biFunction.apply(rayTraceContext, mutable.setPos(n3, n2, n))) == null) continue;
            return t2;
        }
        return function.apply(rayTraceContext);
    }

    private VoxelShape lambda$func_242403_h$2(BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.down();
        return this.getBlockState(blockPos2).getCollisionShape(this, blockPos2);
    }

    private static BlockRayTraceResult lambda$rayTraceBlocks$1(RayTraceContext rayTraceContext) {
        Vector3d vector3d = rayTraceContext.getStartVec().subtract(rayTraceContext.getEndVec());
        return BlockRayTraceResult.createMiss(rayTraceContext.getEndVec(), Direction.getFacingFromVector(vector3d.x, vector3d.y, vector3d.z), new BlockPos(rayTraceContext.getEndVec()));
    }

    private BlockRayTraceResult lambda$rayTraceBlocks$0(RayTraceContext rayTraceContext, BlockPos blockPos) {
        BlockState blockState = this.getBlockState(blockPos);
        FluidState fluidState = this.getFluidState(blockPos);
        Vector3d vector3d = rayTraceContext.getStartVec();
        Vector3d vector3d2 = rayTraceContext.getEndVec();
        VoxelShape voxelShape = rayTraceContext.getBlockShape(blockState, this, blockPos);
        BlockRayTraceResult blockRayTraceResult = this.rayTraceBlocks(vector3d, vector3d2, blockPos, voxelShape, blockState);
        VoxelShape voxelShape2 = rayTraceContext.getFluidShape(fluidState, this, blockPos);
        BlockRayTraceResult blockRayTraceResult2 = voxelShape2.rayTrace(vector3d, vector3d2, blockPos);
        double d = blockRayTraceResult == null ? Double.MAX_VALUE : rayTraceContext.getStartVec().squareDistanceTo(blockRayTraceResult.getHitVec());
        double d2 = blockRayTraceResult2 == null ? Double.MAX_VALUE : rayTraceContext.getStartVec().squareDistanceTo(blockRayTraceResult2.getHitVec());
        return d <= d2 ? blockRayTraceResult : blockRayTraceResult2;
    }
}

