/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class BubbleColumnBlock
extends Block
implements IBucketPickupHandler {
    public static final BooleanProperty DRAG = BlockStateProperties.DRAG;

    public BubbleColumnBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(DRAG, true));
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        BlockState blockState2 = world.getBlockState(blockPos.up());
        if (blockState2.isAir()) {
            entity2.onEnterBubbleColumnWithAirAbove(blockState.get(DRAG));
            if (!world.isRemote) {
                ServerWorld serverWorld = (ServerWorld)world;
                for (int i = 0; i < 2; ++i) {
                    serverWorld.spawnParticle(ParticleTypes.SPLASH, (double)blockPos.getX() + world.rand.nextDouble(), blockPos.getY() + 1, (double)blockPos.getZ() + world.rand.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
                    serverWorld.spawnParticle(ParticleTypes.BUBBLE, (double)blockPos.getX() + world.rand.nextDouble(), blockPos.getY() + 1, (double)blockPos.getZ() + world.rand.nextDouble(), 1, 0.0, 0.01, 0.0, 0.2);
                }
            }
        } else {
            entity2.onEnterBubbleColumn(blockState.get(DRAG));
        }
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        BubbleColumnBlock.placeBubbleColumn(world, blockPos.up(), BubbleColumnBlock.getDrag(world, blockPos.down()));
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        BubbleColumnBlock.placeBubbleColumn(serverWorld, blockPos.up(), BubbleColumnBlock.getDrag(serverWorld, blockPos));
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        return Fluids.WATER.getStillFluidState(true);
    }

    public static void placeBubbleColumn(IWorld iWorld, BlockPos blockPos, boolean bl) {
        if (BubbleColumnBlock.canHoldBubbleColumn(iWorld, blockPos)) {
            iWorld.setBlockState(blockPos, (BlockState)Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, bl), 2);
        }
    }

    public static boolean canHoldBubbleColumn(IWorld iWorld, BlockPos blockPos) {
        FluidState fluidState = iWorld.getFluidState(blockPos);
        return iWorld.getBlockState(blockPos).isIn(Blocks.WATER) && fluidState.getLevel() >= 8 && fluidState.isSource();
    }

    private static boolean getDrag(IBlockReader iBlockReader, BlockPos blockPos) {
        BlockState blockState = iBlockReader.getBlockState(blockPos);
        if (blockState.isIn(Blocks.BUBBLE_COLUMN)) {
            return blockState.get(DRAG);
        }
        return !blockState.isIn(Blocks.SOUL_SAND);
    }

    @Override
    public void animateTick(BlockState blockState, World world, BlockPos blockPos, Random random2) {
        double d = blockPos.getX();
        double d2 = blockPos.getY();
        double d3 = blockPos.getZ();
        if (blockState.get(DRAG).booleanValue()) {
            world.addOptionalParticle(ParticleTypes.CURRENT_DOWN, d + 0.5, d2 + 0.8, d3, 0.0, 0.0, 0.0);
            if (random2.nextInt(200) == 0) {
                world.playSound(d, d2, d3, SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundCategory.BLOCKS, 0.2f + random2.nextFloat() * 0.2f, 0.9f + random2.nextFloat() * 0.15f, true);
            }
        } else {
            world.addOptionalParticle(ParticleTypes.BUBBLE_COLUMN_UP, d + 0.5, d2, d3 + 0.5, 0.0, 0.04, 0.0);
            world.addOptionalParticle(ParticleTypes.BUBBLE_COLUMN_UP, d + (double)random2.nextFloat(), d2 + (double)random2.nextFloat(), d3 + (double)random2.nextFloat(), 0.0, 0.04, 0.0);
            if (random2.nextInt(200) == 0) {
                world.playSound(d, d2, d3, SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundCategory.BLOCKS, 0.2f + random2.nextFloat() * 0.2f, 0.9f + random2.nextFloat() * 0.15f, true);
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (!blockState.isValidPosition(iWorld, blockPos)) {
            return Blocks.WATER.getDefaultState();
        }
        if (direction == Direction.DOWN) {
            iWorld.setBlockState(blockPos, (BlockState)Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, BubbleColumnBlock.getDrag(iWorld, blockPos2)), 2);
        } else if (direction == Direction.UP && !blockState2.isIn(Blocks.BUBBLE_COLUMN) && BubbleColumnBlock.canHoldBubbleColumn(iWorld, blockPos2)) {
            iWorld.getPendingBlockTicks().scheduleTick(blockPos, this, 5);
        }
        iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        return super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        BlockState blockState2 = iWorldReader.getBlockState(blockPos.down());
        return blockState2.isIn(Blocks.BUBBLE_COLUMN) || blockState2.isIn(Blocks.MAGMA_BLOCK) || blockState2.isIn(Blocks.SOUL_SAND);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(DRAG);
    }

    @Override
    public Fluid pickupFluid(IWorld iWorld, BlockPos blockPos, BlockState blockState) {
        iWorld.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 11);
        return Fluids.WATER;
    }
}

