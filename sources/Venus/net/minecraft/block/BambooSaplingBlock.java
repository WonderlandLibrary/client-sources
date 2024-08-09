/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BambooBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.state.properties.BambooLeaves;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BambooSaplingBlock
extends Block
implements IGrowable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(4.0, 0.0, 4.0, 12.0, 12.0, 12.0);

    public BambooSaplingBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        Vector3d vector3d = blockState.getOffset(iBlockReader, blockPos);
        return SHAPE.withOffset(vector3d.x, vector3d.y, vector3d.z);
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (random2.nextInt(3) == 0 && serverWorld.isAirBlock(blockPos.up()) && serverWorld.getLightSubtracted(blockPos.up(), 0) >= 9) {
            this.growBamboo(serverWorld, blockPos);
        }
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return iWorldReader.getBlockState(blockPos.down()).isIn(BlockTags.BAMBOO_PLANTABLE_ON);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (!blockState.isValidPosition(iWorld, blockPos)) {
            return Blocks.AIR.getDefaultState();
        }
        if (direction == Direction.UP && blockState2.isIn(Blocks.BAMBOO)) {
            iWorld.setBlockState(blockPos, Blocks.BAMBOO.getDefaultState(), 2);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public ItemStack getItem(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState) {
        return new ItemStack(Items.BAMBOO);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean bl) {
        return iBlockReader.getBlockState(blockPos.up()).isAir();
    }

    @Override
    public boolean canUseBonemeal(World world, Random random2, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random2, BlockPos blockPos, BlockState blockState) {
        this.growBamboo(serverWorld, blockPos);
    }

    @Override
    public float getPlayerRelativeBlockHardness(BlockState blockState, PlayerEntity playerEntity, IBlockReader iBlockReader, BlockPos blockPos) {
        return playerEntity.getHeldItemMainhand().getItem() instanceof SwordItem ? 1.0f : super.getPlayerRelativeBlockHardness(blockState, playerEntity, iBlockReader, blockPos);
    }

    protected void growBamboo(World world, BlockPos blockPos) {
        world.setBlockState(blockPos.up(), (BlockState)Blocks.BAMBOO.getDefaultState().with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.SMALL), 0);
    }
}

