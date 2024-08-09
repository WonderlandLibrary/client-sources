/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RailState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.CommandBlockMinecartEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DetectorRailBlock
extends AbstractRailBlock {
    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public DetectorRailBlock(AbstractBlock.Properties properties) {
        super(true, properties);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with(POWERED, false)).with(SHAPE, RailShape.NORTH_SOUTH));
    }

    @Override
    public boolean canProvidePower(BlockState blockState) {
        return false;
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos blockPos, Entity entity2) {
        if (!world.isRemote && !blockState.get(POWERED).booleanValue()) {
            this.updatePoweredState(world, blockPos, blockState);
        }
    }

    @Override
    public void tick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, Random random2) {
        if (blockState.get(POWERED).booleanValue()) {
            this.updatePoweredState(serverWorld, blockPos, blockState);
        }
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        return blockState.get(POWERED) != false ? 15 : 0;
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, Direction direction) {
        if (!blockState.get(POWERED).booleanValue()) {
            return 1;
        }
        return direction == Direction.UP ? 15 : 0;
    }

    private void updatePoweredState(World world, BlockPos blockPos, BlockState blockState) {
        if (this.isValidPosition(blockState, world, blockPos)) {
            BlockState blockState2;
            boolean bl = blockState.get(POWERED);
            boolean bl2 = false;
            List<AbstractMinecartEntity> list = this.findMinecarts(world, blockPos, AbstractMinecartEntity.class, null);
            if (!list.isEmpty()) {
                bl2 = true;
            }
            if (bl2 && !bl) {
                blockState2 = (BlockState)blockState.with(POWERED, true);
                world.setBlockState(blockPos, blockState2, 0);
                this.updateConnectedRails(world, blockPos, blockState2, false);
                world.notifyNeighborsOfStateChange(blockPos, this);
                world.notifyNeighborsOfStateChange(blockPos.down(), this);
                world.markBlockRangeForRenderUpdate(blockPos, blockState, blockState2);
            }
            if (!bl2 && bl) {
                blockState2 = (BlockState)blockState.with(POWERED, false);
                world.setBlockState(blockPos, blockState2, 0);
                this.updateConnectedRails(world, blockPos, blockState2, true);
                world.notifyNeighborsOfStateChange(blockPos, this);
                world.notifyNeighborsOfStateChange(blockPos.down(), this);
                world.markBlockRangeForRenderUpdate(blockPos, blockState, blockState2);
            }
            if (bl2) {
                world.getPendingBlockTicks().scheduleTick(blockPos, this, 20);
            }
            world.updateComparatorOutputLevel(blockPos, this);
        }
    }

    protected void updateConnectedRails(World world, BlockPos blockPos, BlockState blockState, boolean bl) {
        RailState railState = new RailState(world, blockPos, blockState);
        for (BlockPos blockPos2 : railState.getConnectedRails()) {
            BlockState blockState2 = world.getBlockState(blockPos2);
            blockState2.neighborChanged(world, blockPos2, blockState2.getBlock(), blockPos, true);
        }
    }

    @Override
    public void onBlockAdded(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState2.isIn(blockState.getBlock())) {
            this.updatePoweredState(world, blockPos, this.updateRailState(blockState, world, blockPos, bl));
        }
    }

    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState blockState) {
        return false;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World world, BlockPos blockPos) {
        if (blockState.get(POWERED).booleanValue()) {
            List<CommandBlockMinecartEntity> list = this.findMinecarts(world, blockPos, CommandBlockMinecartEntity.class, null);
            if (!list.isEmpty()) {
                return list.get(0).getCommandBlockLogic().getSuccessCount();
            }
            List<AbstractMinecartEntity> list2 = this.findMinecarts(world, blockPos, AbstractMinecartEntity.class, EntityPredicates.HAS_INVENTORY);
            if (!list2.isEmpty()) {
                return Container.calcRedstoneFromInventory((IInventory)((Object)list2.get(0)));
            }
        }
        return 1;
    }

    protected <T extends AbstractMinecartEntity> List<T> findMinecarts(World world, BlockPos blockPos, Class<T> clazz, @Nullable Predicate<Entity> predicate) {
        return world.getEntitiesWithinAABB(clazz, this.getDectectionBox(blockPos), predicate);
    }

    private AxisAlignedBB getDectectionBox(BlockPos blockPos) {
        double d = 0.2;
        return new AxisAlignedBB((double)blockPos.getX() + 0.2, blockPos.getY(), (double)blockPos.getZ() + 0.2, (double)(blockPos.getX() + 1) - 0.2, (double)(blockPos.getY() + 1) - 0.2, (double)(blockPos.getZ() + 1) - 0.2);
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
                    case NORTH_SOUTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_SOUTH);
                    }
                }
            }
            case CLOCKWISE_90: {
                switch (blockState.get(SHAPE)) {
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
                    case NORTH_SOUTH: {
                        return (BlockState)blockState.with(SHAPE, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)blockState.with(SHAPE, RailShape.NORTH_SOUTH);
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

