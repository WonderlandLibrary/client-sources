/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;

public class TransportationHelper {
    public static int[][] func_234632_a_(Direction direction) {
        Direction direction2 = direction.rotateY();
        Direction direction3 = direction2.getOpposite();
        Direction direction4 = direction.getOpposite();
        return new int[][]{{direction2.getXOffset(), direction2.getZOffset()}, {direction3.getXOffset(), direction3.getZOffset()}, {direction4.getXOffset() + direction2.getXOffset(), direction4.getZOffset() + direction2.getZOffset()}, {direction4.getXOffset() + direction3.getXOffset(), direction4.getZOffset() + direction3.getZOffset()}, {direction.getXOffset() + direction2.getXOffset(), direction.getZOffset() + direction2.getZOffset()}, {direction.getXOffset() + direction3.getXOffset(), direction.getZOffset() + direction3.getZOffset()}, {direction4.getXOffset(), direction4.getZOffset()}, {direction.getXOffset(), direction.getZOffset()}};
    }

    public static boolean func_234630_a_(double d) {
        return !Double.isInfinite(d) && d < 1.0;
    }

    public static boolean func_234631_a_(ICollisionReader iCollisionReader, LivingEntity livingEntity, AxisAlignedBB axisAlignedBB) {
        return iCollisionReader.getCollisionShapes(livingEntity, axisAlignedBB).allMatch(VoxelShape::isEmpty);
    }

    @Nullable
    public static Vector3d func_242381_a(ICollisionReader iCollisionReader, double d, double d2, double d3, LivingEntity livingEntity, Pose pose) {
        if (TransportationHelper.func_234630_a_(d2)) {
            Vector3d vector3d = new Vector3d(d, d2, d3);
            if (TransportationHelper.func_234631_a_(iCollisionReader, livingEntity, livingEntity.getPoseAABB(pose).offset(vector3d))) {
                return vector3d;
            }
        }
        return null;
    }

    public static VoxelShape func_242380_a(IBlockReader iBlockReader, BlockPos blockPos) {
        BlockState blockState = iBlockReader.getBlockState(blockPos);
        return !blockState.isIn(BlockTags.CLIMBABLE) && (!(blockState.getBlock() instanceof TrapDoorBlock) || blockState.get(TrapDoorBlock.OPEN) == false) ? blockState.getCollisionShape(iBlockReader, blockPos) : VoxelShapes.empty();
    }

    public static double func_242383_a(BlockPos blockPos, int n, Function<BlockPos, VoxelShape> function) {
        BlockPos.Mutable mutable = blockPos.toMutable();
        for (int i = 0; i < n; ++i) {
            VoxelShape voxelShape = function.apply(mutable);
            if (!voxelShape.isEmpty()) {
                return (double)(blockPos.getY() + i) + voxelShape.getStart(Direction.Axis.Y);
            }
            mutable.move(Direction.UP);
        }
        return Double.POSITIVE_INFINITY;
    }

    @Nullable
    public static Vector3d func_242379_a(EntityType<?> entityType, ICollisionReader iCollisionReader, BlockPos blockPos, boolean bl) {
        if (bl && entityType.func_233597_a_(iCollisionReader.getBlockState(blockPos))) {
            return null;
        }
        double d = iCollisionReader.func_242402_a(TransportationHelper.func_242380_a(iCollisionReader, blockPos), () -> TransportationHelper.lambda$func_242379_a$0(iCollisionReader, blockPos));
        if (!TransportationHelper.func_234630_a_(d)) {
            return null;
        }
        if (bl && d <= 0.0 && entityType.func_233597_a_(iCollisionReader.getBlockState(blockPos.down()))) {
            return null;
        }
        Vector3d vector3d = Vector3d.copyCenteredWithVerticalOffset(blockPos, d);
        return iCollisionReader.getCollisionShapes(null, entityType.getSize().func_242286_a(vector3d)).allMatch(VoxelShape::isEmpty) ? vector3d : null;
    }

    private static VoxelShape lambda$func_242379_a$0(ICollisionReader iCollisionReader, BlockPos blockPos) {
        return TransportationHelper.func_242380_a(iCollisionReader, blockPos.down());
    }
}

