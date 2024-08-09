/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import java.util.Objects;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.CubeCoordinateIterator;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ICollisionReader;
import net.minecraft.world.border.WorldBorder;

public class VoxelShapeSpliterator
extends Spliterators.AbstractSpliterator<VoxelShape> {
    @Nullable
    private final Entity entity;
    private final AxisAlignedBB aabb;
    private final ISelectionContext context;
    private final CubeCoordinateIterator cubeCoordinateIterator;
    private final BlockPos.Mutable mutablePos;
    private final VoxelShape shape;
    private final ICollisionReader reader;
    private boolean field_234875_h_;
    private final BiPredicate<BlockState, BlockPos> statePositionPredicate;

    public VoxelShapeSpliterator(ICollisionReader iCollisionReader, @Nullable Entity entity2, AxisAlignedBB axisAlignedBB) {
        this(iCollisionReader, entity2, axisAlignedBB, VoxelShapeSpliterator::lambda$new$0);
    }

    public VoxelShapeSpliterator(ICollisionReader iCollisionReader, @Nullable Entity entity2, AxisAlignedBB axisAlignedBB, BiPredicate<BlockState, BlockPos> biPredicate) {
        super(Long.MAX_VALUE, 1280);
        this.context = entity2 == null ? ISelectionContext.dummy() : ISelectionContext.forEntity(entity2);
        this.mutablePos = new BlockPos.Mutable();
        this.shape = VoxelShapes.create(axisAlignedBB);
        this.reader = iCollisionReader;
        this.field_234875_h_ = entity2 != null;
        this.entity = entity2;
        this.aabb = axisAlignedBB;
        this.statePositionPredicate = biPredicate;
        int n = MathHelper.floor(axisAlignedBB.minX - 1.0E-7) - 1;
        int n2 = MathHelper.floor(axisAlignedBB.maxX + 1.0E-7) + 1;
        int n3 = MathHelper.floor(axisAlignedBB.minY - 1.0E-7) - 1;
        int n4 = MathHelper.floor(axisAlignedBB.maxY + 1.0E-7) + 1;
        int n5 = MathHelper.floor(axisAlignedBB.minZ - 1.0E-7) - 1;
        int n6 = MathHelper.floor(axisAlignedBB.maxZ + 1.0E-7) + 1;
        this.cubeCoordinateIterator = new CubeCoordinateIterator(n, n3, n5, n2, n4, n6);
    }

    @Override
    public boolean tryAdvance(Consumer<? super VoxelShape> consumer) {
        return this.field_234875_h_ && this.func_234879_b_(consumer) || this.func_234878_a_(consumer);
    }

    boolean func_234878_a_(Consumer<? super VoxelShape> consumer) {
        while (this.cubeCoordinateIterator.hasNext()) {
            IBlockReader iBlockReader;
            int n = this.cubeCoordinateIterator.getX();
            int n2 = this.cubeCoordinateIterator.getY();
            int n3 = this.cubeCoordinateIterator.getZ();
            int n4 = this.cubeCoordinateIterator.numBoundariesTouched();
            if (n4 == 3 || (iBlockReader = this.func_234876_a_(n, n3)) == null) continue;
            this.mutablePos.setPos(n, n2, n3);
            BlockState blockState = iBlockReader.getBlockState(this.mutablePos);
            if (!this.statePositionPredicate.test(blockState, this.mutablePos) || n4 == 1 && !blockState.isCollisionShapeLargerThanFullBlock() || n4 == 2 && !blockState.isIn(Blocks.MOVING_PISTON)) continue;
            VoxelShape voxelShape = blockState.getCollisionShape(this.reader, this.mutablePos, this.context);
            if (voxelShape == VoxelShapes.fullCube()) {
                if (!this.aabb.intersects(n, n2, n3, (double)n + 1.0, (double)n2 + 1.0, (double)n3 + 1.0)) continue;
                consumer.accept(voxelShape.withOffset(n, n2, n3));
                return false;
            }
            VoxelShape voxelShape2 = voxelShape.withOffset(n, n2, n3);
            if (!VoxelShapes.compare(voxelShape2, this.shape, IBooleanFunction.AND)) continue;
            consumer.accept(voxelShape2);
            return false;
        }
        return true;
    }

    @Nullable
    private IBlockReader func_234876_a_(int n, int n2) {
        int n3 = n >> 4;
        int n4 = n2 >> 4;
        return this.reader.getBlockReader(n3, n4);
    }

    boolean func_234879_b_(Consumer<? super VoxelShape> consumer) {
        VoxelShape voxelShape;
        Objects.requireNonNull(this.entity);
        this.field_234875_h_ = false;
        WorldBorder worldBorder = this.reader.getWorldBorder();
        AxisAlignedBB axisAlignedBB = this.entity.getBoundingBox();
        if (!VoxelShapeSpliterator.func_234877_a_(worldBorder, axisAlignedBB) && !VoxelShapeSpliterator.func_241461_b_(voxelShape = worldBorder.getShape(), axisAlignedBB) && VoxelShapeSpliterator.func_241460_a_(voxelShape, axisAlignedBB)) {
            consumer.accept(voxelShape);
            return false;
        }
        return true;
    }

    private static boolean func_241460_a_(VoxelShape voxelShape, AxisAlignedBB axisAlignedBB) {
        return VoxelShapes.compare(voxelShape, VoxelShapes.create(axisAlignedBB.grow(1.0E-7)), IBooleanFunction.AND);
    }

    private static boolean func_241461_b_(VoxelShape voxelShape, AxisAlignedBB axisAlignedBB) {
        return VoxelShapes.compare(voxelShape, VoxelShapes.create(axisAlignedBB.shrink(1.0E-7)), IBooleanFunction.AND);
    }

    public static boolean func_234877_a_(WorldBorder worldBorder, AxisAlignedBB axisAlignedBB) {
        double d = MathHelper.floor(worldBorder.minX());
        double d2 = MathHelper.floor(worldBorder.minZ());
        double d3 = MathHelper.ceil(worldBorder.maxX());
        double d4 = MathHelper.ceil(worldBorder.maxZ());
        return axisAlignedBB.minX > d && axisAlignedBB.minX < d3 && axisAlignedBB.minZ > d2 && axisAlignedBB.minZ < d4 && axisAlignedBB.maxX > d && axisAlignedBB.maxX < d3 && axisAlignedBB.maxZ > d2 && axisAlignedBB.maxZ < d4;
    }

    private static boolean lambda$new$0(BlockState blockState, BlockPos blockPos) {
        return false;
    }
}

