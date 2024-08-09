/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FlowingFluidBlock
extends Block
implements IBucketPickupHandler {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_15;
    protected final FlowingFluid fluid;
    private final List<FluidState> fluidStatesCache;
    public static final VoxelShape LAVA_COLLISION_SHAPE = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    protected FlowingFluidBlock(FlowingFluid flowingFluid, AbstractBlock.Properties properties) {
        super(properties);
        this.fluid = flowingFluid;
        this.fluidStatesCache = Lists.newArrayList();
        this.fluidStatesCache.add(flowingFluid.getStillFluidState(true));
        for (int i = 1; i < 8; ++i) {
            this.fluidStatesCache.add(flowingFluid.getFlowingFluidState(8 - i, true));
        }
        this.fluidStatesCache.add(flowingFluid.getFlowingFluidState(8, false));
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(LEVEL, 0));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return iSelectionContext.func_216378_a(LAVA_COLLISION_SHAPE, blockPos, true) && blockState.get(LEVEL) == 0 && iSelectionContext.func_230426_a_(iBlockReader.getFluidState(blockPos.up()), this.fluid) ? LAVA_COLLISION_SHAPE : VoxelShapes.empty();
    }

    @Override
    public boolean ticksRandomly(BlockState blockState) {
        return blockState.getFluidState().ticksRandomly();
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        blockState.getFluidState().randomTick(serverWorld, blockPos, random2);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return true;
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return !this.fluid.isIn(FluidTags.LAVA);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        int n = blockState.get(LEVEL);
        return this.fluidStatesCache.get(Math.min(n, 8));
    }

    @Override
    public boolean isSideInvisible(BlockState blockState, BlockState blockState2, Direction direction) {
        return blockState2.getFluidState().getFluid().isEquivalentTo(this.fluid);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        return Collections.emptyList();
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return VoxelShapes.empty();
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (this.reactWithNeighbors(world, blockPos, blockState)) {
            world.getPendingFluidTicks().scheduleTick(blockPos, blockState.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getFluidState().isSource() || blockState2.getFluidState().isSource()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, blockState.getFluidState().getFluid(), this.fluid.getTickRate(iWorld));
        }
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (this.reactWithNeighbors(world, blockPos, blockState)) {
            world.getPendingFluidTicks().scheduleTick(blockPos, blockState.getFluidState().getFluid(), this.fluid.getTickRate(world));
        }
    }

    private boolean reactWithNeighbors(World world, BlockPos blockPos, BlockState blockState) {
        if (this.fluid.isIn(FluidTags.LAVA)) {
            boolean bl = world.getBlockState(blockPos.down()).isIn(Blocks.SOUL_SOIL);
            for (Direction direction : Direction.values()) {
                if (direction == Direction.DOWN) continue;
                BlockPos blockPos2 = blockPos.offset(direction);
                if (world.getFluidState(blockPos2).isTagged(FluidTags.WATER)) {
                    Block block = world.getFluidState(blockPos).isSource() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE;
                    world.setBlockState(blockPos, block.getDefaultState());
                    this.triggerMixEffects(world, blockPos);
                    return true;
                }
                if (!bl || !world.getBlockState(blockPos2).isIn(Blocks.BLUE_ICE)) continue;
                world.setBlockState(blockPos, Blocks.BASALT.getDefaultState());
                this.triggerMixEffects(world, blockPos);
                return true;
            }
        }
        return false;
    }

    private void triggerMixEffects(IWorld iWorld, BlockPos blockPos) {
        iWorld.playEvent(1501, blockPos, 0);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public Fluid pickupFluid(IWorld iWorld, BlockPos blockPos, BlockState blockState) {
        if (blockState.get(LEVEL) == 0) {
            iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 11);
            return this.fluid;
        }
        return Fluids.EMPTY;
    }
}

