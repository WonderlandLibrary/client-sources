/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapeSpliterator;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.border.WorldBorder;

public interface ICollisionReader
extends IBlockReader {
    public WorldBorder getWorldBorder();

    @Nullable
    public IBlockReader getBlockReader(int var1, int var2);

    default public boolean checkNoEntityCollision(@Nullable Entity entity2, VoxelShape voxelShape) {
        return false;
    }

    default public boolean placedBlockCollides(BlockState blockState, BlockPos blockPos, ISelectionContext iSelectionContext) {
        VoxelShape voxelShape = blockState.getCollisionShape(this, blockPos, iSelectionContext);
        return voxelShape.isEmpty() || this.checkNoEntityCollision((Entity)null, voxelShape.withOffset(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
    }

    default public boolean checkNoEntityCollision(Entity entity2) {
        return this.checkNoEntityCollision(entity2, VoxelShapes.create(entity2.getBoundingBox()));
    }

    default public boolean checkNoEntityCollision(Entity entity2, AxisAlignedBB axisAlignedBB) {
        return this.checkNoEntityCollision(entity2, VoxelShapes.create(axisAlignedBB));
    }

    default public boolean hasNoCollisions(AxisAlignedBB axisAlignedBB) {
        return this.hasNoCollisions(null, axisAlignedBB, ICollisionReader::lambda$hasNoCollisions$0);
    }

    default public boolean hasNoCollisions(Entity entity2) {
        return this.hasNoCollisions(entity2, entity2.getBoundingBox(), ICollisionReader::lambda$hasNoCollisions$1);
    }

    default public boolean hasNoCollisions(Entity entity2, AxisAlignedBB axisAlignedBB) {
        return this.hasNoCollisions(entity2, axisAlignedBB, ICollisionReader::lambda$hasNoCollisions$2);
    }

    default public boolean hasNoCollisions(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, Predicate<Entity> predicate) {
        return this.func_234867_d_(entity2, axisAlignedBB, predicate).allMatch(VoxelShape::isEmpty);
    }

    public Stream<VoxelShape> func_230318_c_(@Nullable Entity var1, AxisAlignedBB var2, Predicate<Entity> var3);

    default public Stream<VoxelShape> func_234867_d_(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, Predicate<Entity> predicate) {
        return Stream.concat(this.getCollisionShapes(entity2, axisAlignedBB), this.func_230318_c_(entity2, axisAlignedBB, predicate));
    }

    default public Stream<VoxelShape> getCollisionShapes(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB) {
        return StreamSupport.stream(new VoxelShapeSpliterator(this, entity2, axisAlignedBB), false);
    }

    default public boolean func_242405_a(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, BiPredicate<BlockState, BlockPos> biPredicate) {
        return this.func_241457_a_(entity2, axisAlignedBB, biPredicate).allMatch(VoxelShape::isEmpty);
    }

    default public Stream<VoxelShape> func_241457_a_(@Nullable Entity entity2, AxisAlignedBB axisAlignedBB, BiPredicate<BlockState, BlockPos> biPredicate) {
        return StreamSupport.stream(new VoxelShapeSpliterator(this, entity2, axisAlignedBB, biPredicate), false);
    }

    private static boolean lambda$hasNoCollisions$2(Entity entity2) {
        return false;
    }

    private static boolean lambda$hasNoCollisions$1(Entity entity2) {
        return false;
    }

    private static boolean lambda$hasNoCollisions$0(Entity entity2) {
        return false;
    }
}

