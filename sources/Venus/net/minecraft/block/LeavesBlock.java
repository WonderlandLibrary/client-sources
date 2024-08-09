/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class LeavesBlock
extends Block {
    public static final IntegerProperty DISTANCE = BlockStateProperties.DISTANCE_1_7;
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

    public LeavesBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(DISTANCE, 7)).with(PERSISTENT, false));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return VoxelShapes.empty();
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return blockState.get(DISTANCE) == 7 && blockState.get(PERSISTENT) == false;
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (!blockState.get(PERSISTENT).booleanValue() && blockState.get(DISTANCE) == 7) {
            LeavesBlock.spawnDrops(blockState, serverWorld, blockPos);
            serverWorld.removeBlock(blockPos, true);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        serverWorld.setBlockState(blockPos, LeavesBlock.updateDistance(blockState, serverWorld, blockPos), 0);
    }

    @Override
    public int getOpacity(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return 0;
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        int n = LeavesBlock.getDistance(blockState2) + 1;
        if (n != 1 || blockState.get(DISTANCE) != n) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 1);
        }
        return blockState;
    }

    private static BlockState updateDistance(BlockState blockState, IWorld iWorld, BlockPos blockPos) {
        int n = 7;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (Direction direction : Direction.values()) {
            mutable.setAndMove(blockPos, direction);
            n = Math.min(n, LeavesBlock.getDistance(iWorld.getBlockState(mutable)) + 1);
            if (n == 1) break;
        }
        return (BlockState)blockState.with(DISTANCE, n);
    }

    private static int getDistance(BlockState blockState) {
        if (BlockTags.LOGS.contains(blockState.getBlock())) {
            return 1;
        }
        return blockState.getBlock() instanceof LeavesBlock ? blockState.get(DISTANCE) : 7;
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        BlockPos blockPos2;
        BlockState blockState2;
        if (!(!world.isRainingAt(blockPos.up()) || random2.nextInt(15) != 1 || (blockState2 = world.getBlockState(blockPos2 = blockPos.down())).isSolid() && blockState2.isSolidSide(world, blockPos2, Direction.UP))) {
            double d = (double)blockPos.getX() + random2.nextDouble();
            double d2 = (double)blockPos.getY() - 0.05;
            double d3 = (double)blockPos.getZ() + random2.nextDouble();
            world.addParticle(ParticleTypes.DRIPPING_WATER, d, d2, d3, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return LeavesBlock.updateDistance((BlockState)this.getDefaultState().with(PERSISTENT, true), blockItemUseContext.getWorld(), blockItemUseContext.getPos());
    }
}

