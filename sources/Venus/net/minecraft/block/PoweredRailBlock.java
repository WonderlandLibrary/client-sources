/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PoweredRailBlock
extends AbstractRailBlock {
    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    protected PoweredRailBlock(AbstractBlock.Properties properties) {
        super(true, properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(SHAPE, RailShape.NORTH_SOUTH)).with(POWERED, false));
    }

    protected boolean findPoweredRailSignal(World world, BlockPos blockPos, BlockState blockState, boolean bl, int n) {
        if (n >= 8) {
            return true;
        }
        int n2 = blockPos.getX();
        int n3 = blockPos.getY();
        int n4 = blockPos.getZ();
        boolean bl2 = true;
        RailShape railShape = blockState.get(SHAPE);
        switch (railShape) {
            case NORTH_SOUTH: {
                if (bl) {
                    ++n4;
                    break;
                }
                --n4;
                break;
            }
            case EAST_WEST: {
                if (bl) {
                    --n2;
                    break;
                }
                ++n2;
                break;
            }
            case ASCENDING_EAST: {
                if (bl) {
                    --n2;
                } else {
                    ++n2;
                    ++n3;
                    bl2 = false;
                }
                railShape = RailShape.EAST_WEST;
                break;
            }
            case ASCENDING_WEST: {
                if (bl) {
                    --n2;
                    ++n3;
                    bl2 = false;
                } else {
                    ++n2;
                }
                railShape = RailShape.EAST_WEST;
                break;
            }
            case ASCENDING_NORTH: {
                if (bl) {
                    ++n4;
                } else {
                    --n4;
                    ++n3;
                    bl2 = false;
                }
                railShape = RailShape.NORTH_SOUTH;
                break;
            }
            case ASCENDING_SOUTH: {
                if (bl) {
                    ++n4;
                    ++n3;
                    bl2 = false;
                } else {
                    --n4;
                }
                railShape = RailShape.NORTH_SOUTH;
            }
        }
        if (this.isSamePoweredRail(world, new BlockPos(n2, n3, n4), bl, n, railShape)) {
            return false;
        }
        return bl2 && this.isSamePoweredRail(world, new BlockPos(n2, n3 - 1, n4), bl, n, railShape);
    }

    protected boolean isSamePoweredRail(World world, BlockPos blockPos, boolean bl, int n, RailShape railShape) {
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.isIn(this)) {
            return true;
        }
        RailShape railShape2 = blockState.get(SHAPE);
        if (railShape != RailShape.EAST_WEST || railShape2 != RailShape.NORTH_SOUTH && railShape2 != RailShape.ASCENDING_NORTH && railShape2 != RailShape.ASCENDING_SOUTH) {
            if (railShape != RailShape.NORTH_SOUTH || railShape2 != RailShape.EAST_WEST && railShape2 != RailShape.ASCENDING_EAST && railShape2 != RailShape.ASCENDING_WEST) {
                if (blockState.get(POWERED).booleanValue()) {
                    return world.isBlockPowered(blockPos) ? true : this.findPoweredRailSignal(world, blockPos, blockState, bl, n + 1);
                }
                return true;
            }
            return true;
        }
        return true;
    }

    @Override
    protected void updateState(BlockState blockState, World world, BlockPos blockPos, Block block) {
        boolean bl;
        boolean bl2 = blockState.get(POWERED);
        boolean bl3 = bl = world.isBlockPowered(blockPos) || this.findPoweredRailSignal(world, blockPos, blockState, true, 1) || this.findPoweredRailSignal(world, blockPos, blockState, false, 1);
        if (bl != bl2) {
            world.setBlockState(blockPos, (BlockState)blockState.with(POWERED, bl), 0);
            world.notifyNeighborsOfStateChange(blockPos.down(), this);
            if (blockState.get(SHAPE).isAscending()) {
                world.notifyNeighborsOfStateChange(blockPos.up(), this);
            }
        }
    }

    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180: {
                switch (blockState.get(SHAPE)) {
                    case ASCENDING_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                }
            }
            case COUNTERCLOCKWISE_90: {
                switch (blockState.get(SHAPE)) {
                    case NORTH_SOUTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_EAST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_WEST);
                    }
                }
            }
            case CLOCKWISE_90: {
                switch (blockState.get(SHAPE)) {
                    case NORTH_SOUTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_WEST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                }
            }
        }
        return blockState;
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        RailShape railShape = blockState.get(SHAPE);
        block0 : switch (mirror) {
            case LEFT_RIGHT: {
                switch (railShape) {
                    case ASCENDING_NORTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                }
                return super.mirror(blockState, mirror);
            }
            case FRONT_BACK: {
                switch (railShape) {
                    case ASCENDING_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.ASCENDING_EAST);
                    }
                    default: {
                        break block0;
                    }
                    case SOUTH_EAST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: 
                }
                return (BlockState)blockState.with(SHAPE, RailShape.NORTH_WEST);
            }
        }
        return super.mirror(blockState, mirror);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, POWERED);
    }
}

