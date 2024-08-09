/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RepeaterBlock
extends RedstoneDiodeBlock {
    public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
    public static final IntegerProperty DELAY = BlockStateProperties.DELAY_1_4;

    protected RepeaterBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(DELAY, 1)).with(LOCKED, false)).with(POWERED, false));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (!playerEntity.abilities.allowEdit) {
            return ActionResultType.PASS;
        }
        world.setBlockState(blockPos, (BlockState)blockState.func_235896_a_(DELAY), 0);
        return ActionResultType.func_233537_a_(world.isRemote);
    }

    @Override
    protected int getDelay(BlockState blockState) {
        return blockState.get(DELAY) * 2;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = super.getStateForPlacement(blockItemUseContext);
        return (BlockState)blockState.with(LOCKED, this.isLocked(blockItemUseContext.getWorld(), blockItemUseContext.getPos(), blockState));
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        return !iWorld.isRemote() && direction.getAxis() != blockState.get(HORIZONTAL_FACING).getAxis() ? (BlockState)blockState.with(LOCKED, this.isLocked(iWorld, blockPos, blockState)) : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isLocked(IWorldReader iWorldReader, BlockPos blockPos, BlockState blockState) {
        return this.getPowerOnSides(iWorldReader, blockPos, blockState) > 0;
    }

    @Override
    protected boolean isAlternateInput(BlockState blockState) {
        return RepeaterBlock.isDiode(blockState);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        if (blockState.get(POWERED).booleanValue()) {
            Direction direction = blockState.get(HORIZONTAL_FACING);
            double d = (double)blockPos.getX() + 0.5 + (random2.nextDouble() - 0.5) * 0.2;
            double d2 = (double)blockPos.getY() + 0.4 + (random2.nextDouble() - 0.5) * 0.2;
            double d3 = (double)blockPos.getZ() + 0.5 + (random2.nextDouble() - 0.5) * 0.2;
            float f = -5.0f;
            if (random2.nextBoolean()) {
                f = blockState.get(DELAY) * 2 - 1;
            }
            double d4 = (f /= 16.0f) * (float)direction.getXOffset();
            double d5 = f * (float)direction.getZOffset();
            world.addParticle(RedstoneParticleData.REDSTONE_DUST, d + d4, d2, d3 + d5, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, DELAY, LOCKED, POWERED);
    }
}

