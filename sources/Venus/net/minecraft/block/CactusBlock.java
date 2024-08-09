/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CactusBlock
extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_15;
    protected static final VoxelShape COLLISION_SHAPE = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
    protected static final VoxelShape OUTLINE_SHAPE = Block.makeCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    protected CactusBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(AGE, 0));
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!blockState.isValidPosition(serverWorld, blockPos)) {
            serverWorld.destroyBlock(blockPos, false);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        BlockPos blockPos2 = blockPos.up();
        if (serverWorld.isAirBlock(blockPos2)) {
            int n = 1;
            while (serverWorld.getBlockState(blockPos.down(n)).isIn(this)) {
                ++n;
            }
            if (n < 3) {
                int n2 = blockState.get(AGE);
                if (n2 == 15) {
                    serverWorld.setBlockState(blockPos2, this.getDefaultState());
                    BlockState blockState2 = (BlockState)blockState.with(AGE, 0);
                    serverWorld.setBlockState(blockPos, blockState2, 1);
                    blockState2.neighborChanged(serverWorld, blockPos2, this, blockPos, true);
                } else {
                    serverWorld.setBlockState(blockPos, (BlockState)blockState.with(AGE, n2 + 1), 1);
                }
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return COLLISION_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return OUTLINE_SHAPE;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (!blockState.isValidPosition(iWorld, blockPos)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockState2 = iWorldReader.getBlockState(blockPos.offset(direction));
            Material material = blockState2.getMaterial();
            if (!material.isSolid() && !iWorldReader.getFluidState(blockPos.offset(direction)).isTagged(FluidTags.LAVA)) continue;
            return true;
        }
        BlockState blockState3 = iWorldReader.getBlockState(blockPos.down());
        return (blockState3.isIn(Blocks.CACTUS) || blockState3.isIn(Blocks.SAND) || blockState3.isIn(Blocks.RED_SAND)) && !iWorldReader.getBlockState(blockPos.up()).getMaterial().isLiquid();
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        entity2.attackEntityFrom(DamageSource.CACTUS, 1.0f);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }
}

