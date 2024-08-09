/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RailState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.Property;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class AbstractRailBlock
extends Block {
    protected static final VoxelShape FLAT_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    protected static final VoxelShape ASCENDING_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    private final boolean disableCorners;

    public static boolean isRail(World world, BlockPos blockPos) {
        return AbstractRailBlock.isRail(world.getBlockState(blockPos));
    }

    public static boolean isRail(BlockState blockState) {
        return blockState.isIn(BlockTags.RAILS) && blockState.getBlock() instanceof AbstractRailBlock;
    }

    protected AbstractRailBlock(boolean bl, AbstractBlock.Properties properties) {
        super(properties);
        this.disableCorners = bl;
    }

    public boolean areCornersDisabled() {
        return this.disableCorners;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
        RailShape railShape = blockState.isIn(this) ? blockState.get(this.getShapeProperty()) : null;
        return railShape != null && railShape.isAscending() ? ASCENDING_AABB : FLAT_AABB;
    }

    @Override
    public boolean isValidPosition(BlockState blockState, IWorldReader iWorldReader, BlockPos blockPos) {
        return AbstractRailBlock.hasSolidSideOnTop(iWorldReader, blockPos.down());
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock())) {
            this.updateRailState(blockState, world, blockPos, bl);
        }
    }

    protected BlockState updateRailState(BlockState blockState, World world, BlockPos blockPos, boolean bl) {
        blockState = this.getUpdatedState(world, blockPos, blockState, false);
        if (this.disableCorners) {
            blockState.neighborChanged(world, blockPos, this, blockPos, bl);
        }
        return blockState;
    }

    @Override
    public void neighborChanged(BlockState blockState, World world, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if (!world.isRemote && world.getBlockState(blockPos).isIn(this)) {
            RailShape railShape = blockState.get(this.getShapeProperty());
            if (AbstractRailBlock.isValidRailDirection(blockPos, world, railShape)) {
                AbstractRailBlock.spawnDrops(blockState, world, blockPos);
                world.removeBlock(blockPos, bl);
            } else {
                this.updateState(blockState, world, blockPos, block);
            }
        }
    }

    private static boolean isValidRailDirection(BlockPos blockPos, World world, RailShape railShape) {
        if (!AbstractRailBlock.hasSolidSideOnTop(world, blockPos.down())) {
            return false;
        }
        switch (1.$SwitchMap$net$minecraft$state$properties$RailShape[railShape.ordinal()]) {
            case 1: {
                return !AbstractRailBlock.hasSolidSideOnTop(world, blockPos.east());
            }
            case 2: {
                return !AbstractRailBlock.hasSolidSideOnTop(world, blockPos.west());
            }
            case 3: {
                return !AbstractRailBlock.hasSolidSideOnTop(world, blockPos.north());
            }
            case 4: {
                return !AbstractRailBlock.hasSolidSideOnTop(world, blockPos.south());
            }
        }
        return true;
    }

    protected void updateState(BlockState blockState, World world, BlockPos blockPos, Block block) {
    }

    protected BlockState getUpdatedState(World world, BlockPos blockPos, BlockState blockState, boolean bl) {
        if (world.isRemote) {
            return blockState;
        }
        RailShape railShape = blockState.get(this.getShapeProperty());
        return new RailState(world, blockPos, blockState).placeRail(world.isBlockPowered(blockPos), bl, railShape).getNewState();
    }

    @Override
    public PushReaction getPushReaction(BlockState blockState) {
        return PushReaction.NORMAL;
    }

    @Override
    public void onReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!bl) {
            super.onReplaced(blockState, world, blockPos, blockState2, bl);
            if (blockState.get(this.getShapeProperty()).isAscending()) {
                world.notifyNeighborsOfStateChange(blockPos.up(), this);
            }
            if (this.disableCorners) {
                world.notifyNeighborsOfStateChange(blockPos, this);
                world.notifyNeighborsOfStateChange(blockPos.down(), this);
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        BlockState blockState = super.getDefaultState();
        Direction direction = blockItemUseContext.getPlacementHorizontalFacing();
        boolean bl = direction == Direction.EAST || direction == Direction.WEST;
        return (BlockState)blockState.with(this.getShapeProperty(), bl ? RailShape.EAST_WEST : RailShape.NORTH_SOUTH);
    }

    public abstract Property<RailShape> getShapeProperty();
}

