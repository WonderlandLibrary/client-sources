/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FourWayBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class FenceBlock
extends FourWayBlock {
    private final VoxelShape[] renderShapes;

    public FenceBlock(AbstractBlock.Properties properties) {
        super(2.0f, 2.0f, 16.0f, 16.0f, 24.0f, properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(WATERLOGGED, false));
        this.renderShapes = this.makeShapes(2.0f, 1.0f, 16.0f, 6.0f, 15.0f);
    }

    @Override
    public VoxelShape getRenderShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return this.renderShapes[this.getIndex(blockState)];
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        return this.getShape(blockState, iBlockReader, blockPos, iSelectionContext);
    }

    @Override
    public boolean allowsMovement(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, PathType pathType) {
        return true;
    }

    public boolean canConnect(BlockState blockState, boolean bl, Direction direction) {
        Block block = blockState.getBlock();
        boolean bl2 = this.isWoodenFence(block);
        boolean bl3 = block instanceof FenceGateBlock && FenceGateBlock.isParallel(blockState, direction);
        return !FenceBlock.cannotAttach(block) && bl || bl2 || bl3;
    }

    private boolean isWoodenFence(Block block) {
        return block.isIn(BlockTags.FENCES) && block.isIn(BlockTags.WOODEN_FENCES) == this.getDefaultState().isIn(BlockTags.WOODEN_FENCES);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if (world.isRemote) {
            ItemStack itemStack = playerEntity.getHeldItem(hand);
            return itemStack.getItem() == Items.LEAD ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }
        return LeadItem.bindPlayerMobs(playerEntity, world, blockPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        World world = blockItemUseContext.getWorld();
        BlockPos blockPos = blockItemUseContext.getPos();
        FluidState fluidState = blockItemUseContext.getWorld().getFluidState(blockItemUseContext.getPos());
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.east();
        BlockPos blockPos4 = blockPos.south();
        BlockPos blockPos5 = blockPos.west();
        BlockState blockState = world.getBlockState(blockPos2);
        BlockState blockState2 = world.getBlockState(blockPos3);
        BlockState blockState3 = world.getBlockState(blockPos4);
        BlockState blockState4 = world.getBlockState(blockPos5);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)super.getStateForPlacement(blockItemUseContext).with(NORTH, this.canConnect(blockState, blockState.isSolidSide(world, blockPos2, Direction.SOUTH), Direction.SOUTH))).with(EAST, this.canConnect(blockState2, blockState2.isSolidSide(world, blockPos3, Direction.WEST), Direction.WEST))).with(SOUTH, this.canConnect(blockState3, blockState3.isSolidSide(world, blockPos4, Direction.NORTH), Direction.NORTH))).with(WEST, this.canConnect(blockState4, blockState4.isSolidSide(world, blockPos5, Direction.EAST), Direction.EAST))).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState blockState, Direction direction, BlockState blockState2, IWorld iWorld, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.get(WATERLOGGED).booleanValue()) {
            iWorld.getPendingFluidTicks().scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickRate(iWorld));
        }
        return direction.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? (BlockState)blockState.with((Property)FACING_TO_PROPERTY_MAP.get(direction), this.canConnect(blockState2, blockState2.isSolidSide(iWorld, blockPos2, direction.getOpposite()), direction.getOpposite())) : super.updatePostPlacement(blockState, direction, blockState2, iWorld, blockPos, blockPos2);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}

