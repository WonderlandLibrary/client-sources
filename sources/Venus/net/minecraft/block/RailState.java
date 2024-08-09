/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RailState {
    private final World world;
    private final BlockPos pos;
    private final AbstractRailBlock block;
    private BlockState newState;
    private final boolean disableCorners;
    private final List<BlockPos> connectedRails = Lists.newArrayList();

    public RailState(World world, BlockPos blockPos, BlockState blockState) {
        this.world = world;
        this.pos = blockPos;
        this.newState = blockState;
        this.block = (AbstractRailBlock)blockState.getBlock();
        RailShape railShape = blockState.get(this.block.getShapeProperty());
        this.disableCorners = this.block.areCornersDisabled();
        this.reset(railShape);
    }

    public List<BlockPos> getConnectedRails() {
        return this.connectedRails;
    }

    private void reset(RailShape railShape) {
        this.connectedRails.clear();
        switch (1.$SwitchMap$net$minecraft$state$properties$RailShape[railShape.ordinal()]) {
            case 1: {
                this.connectedRails.add(this.pos.north());
                this.connectedRails.add(this.pos.south());
                break;
            }
            case 2: {
                this.connectedRails.add(this.pos.west());
                this.connectedRails.add(this.pos.east());
                break;
            }
            case 3: {
                this.connectedRails.add(this.pos.west());
                this.connectedRails.add(this.pos.east().up());
                break;
            }
            case 4: {
                this.connectedRails.add(this.pos.west().up());
                this.connectedRails.add(this.pos.east());
                break;
            }
            case 5: {
                this.connectedRails.add(this.pos.north().up());
                this.connectedRails.add(this.pos.south());
                break;
            }
            case 6: {
                this.connectedRails.add(this.pos.north());
                this.connectedRails.add(this.pos.south().up());
                break;
            }
            case 7: {
                this.connectedRails.add(this.pos.east());
                this.connectedRails.add(this.pos.south());
                break;
            }
            case 8: {
                this.connectedRails.add(this.pos.west());
                this.connectedRails.add(this.pos.south());
                break;
            }
            case 9: {
                this.connectedRails.add(this.pos.west());
                this.connectedRails.add(this.pos.north());
                break;
            }
            case 10: {
                this.connectedRails.add(this.pos.east());
                this.connectedRails.add(this.pos.north());
            }
        }
    }

    private void checkConnected() {
        for (int i = 0; i < this.connectedRails.size(); ++i) {
            RailState railState = this.createForAdjacent(this.connectedRails.get(i));
            if (railState != null && railState.isConnectedTo(this)) {
                this.connectedRails.set(i, railState.pos);
                continue;
            }
            this.connectedRails.remove(i--);
        }
    }

    private boolean isAdjacentRail(BlockPos blockPos) {
        return AbstractRailBlock.isRail(this.world, blockPos) || AbstractRailBlock.isRail(this.world, blockPos.up()) || AbstractRailBlock.isRail(this.world, blockPos.down());
    }

    @Nullable
    private RailState createForAdjacent(BlockPos blockPos) {
        BlockState blockState = this.world.getBlockState(blockPos);
        if (AbstractRailBlock.isRail(blockState)) {
            return new RailState(this.world, blockPos, blockState);
        }
        BlockPos blockPos2 = blockPos.up();
        blockState = this.world.getBlockState(blockPos2);
        if (AbstractRailBlock.isRail(blockState)) {
            return new RailState(this.world, blockPos2, blockState);
        }
        blockPos2 = blockPos.down();
        blockState = this.world.getBlockState(blockPos2);
        return AbstractRailBlock.isRail(blockState) ? new RailState(this.world, blockPos2, blockState) : null;
    }

    private boolean isConnectedTo(RailState railState) {
        return this.isConnectedTo(railState.pos);
    }

    private boolean isConnectedTo(BlockPos blockPos) {
        for (int i = 0; i < this.connectedRails.size(); ++i) {
            BlockPos blockPos2 = this.connectedRails.get(i);
            if (blockPos2.getX() != blockPos.getX() || blockPos2.getZ() != blockPos.getZ()) continue;
            return false;
        }
        return true;
    }

    protected int countAdjacentRails() {
        int n = 0;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (!this.isAdjacentRail(this.pos.offset(direction))) continue;
            ++n;
        }
        return n;
    }

    private boolean canConnect(RailState railState) {
        return this.isConnectedTo(railState) || this.connectedRails.size() != 2;
    }

    private void connect(RailState railState) {
        this.connectedRails.add(railState.pos);
        BlockPos blockPos = this.pos.north();
        BlockPos blockPos2 = this.pos.south();
        BlockPos blockPos3 = this.pos.west();
        BlockPos blockPos4 = this.pos.east();
        boolean bl = this.isConnectedTo(blockPos);
        boolean bl2 = this.isConnectedTo(blockPos2);
        boolean bl3 = this.isConnectedTo(blockPos3);
        boolean bl4 = this.isConnectedTo(blockPos4);
        RailShape railShape = null;
        if (bl || bl2) {
            railShape = RailShape.NORTH_SOUTH;
        }
        if (bl3 || bl4) {
            railShape = RailShape.EAST_WEST;
        }
        if (!this.disableCorners) {
            if (bl2 && bl4 && !bl && !bl3) {
                railShape = RailShape.SOUTH_EAST;
            }
            if (bl2 && bl3 && !bl && !bl4) {
                railShape = RailShape.SOUTH_WEST;
            }
            if (bl && bl3 && !bl2 && !bl4) {
                railShape = RailShape.NORTH_WEST;
            }
            if (bl && bl4 && !bl2 && !bl3) {
                railShape = RailShape.NORTH_EAST;
            }
        }
        if (railShape == RailShape.NORTH_SOUTH) {
            if (AbstractRailBlock.isRail(this.world, blockPos.up())) {
                railShape = RailShape.ASCENDING_NORTH;
            }
            if (AbstractRailBlock.isRail(this.world, blockPos2.up())) {
                railShape = RailShape.ASCENDING_SOUTH;
            }
        }
        if (railShape == RailShape.EAST_WEST) {
            if (AbstractRailBlock.isRail(this.world, blockPos4.up())) {
                railShape = RailShape.ASCENDING_EAST;
            }
            if (AbstractRailBlock.isRail(this.world, blockPos3.up())) {
                railShape = RailShape.ASCENDING_WEST;
            }
        }
        if (railShape == null) {
            railShape = RailShape.NORTH_SOUTH;
        }
        this.newState = (BlockState)this.newState.with(this.block.getShapeProperty(), railShape);
        this.world.setBlockState(this.pos, this.newState, 0);
    }

    private boolean canConnect(BlockPos blockPos) {
        RailState railState = this.createForAdjacent(blockPos);
        if (railState == null) {
            return true;
        }
        railState.checkConnected();
        return railState.canConnect(this);
    }

    public RailState placeRail(boolean bl, boolean bl2, RailShape railShape) {
        boolean bl3;
        boolean bl4;
        BlockPos blockPos = this.pos.north();
        BlockPos blockPos2 = this.pos.south();
        BlockPos blockPos3 = this.pos.west();
        BlockPos blockPos4 = this.pos.east();
        boolean bl5 = this.canConnect(blockPos);
        boolean bl6 = this.canConnect(blockPos2);
        boolean bl7 = this.canConnect(blockPos3);
        boolean bl8 = this.canConnect(blockPos4);
        RailShape railShape2 = null;
        boolean bl9 = bl5 || bl6;
        boolean bl10 = bl4 = bl7 || bl8;
        if (bl9 && !bl4) {
            railShape2 = RailShape.NORTH_SOUTH;
        }
        if (bl4 && !bl9) {
            railShape2 = RailShape.EAST_WEST;
        }
        boolean bl11 = bl6 && bl8;
        boolean bl12 = bl6 && bl7;
        boolean bl13 = bl5 && bl8;
        boolean bl14 = bl3 = bl5 && bl7;
        if (!this.disableCorners) {
            if (bl11 && !bl5 && !bl7) {
                railShape2 = RailShape.SOUTH_EAST;
            }
            if (bl12 && !bl5 && !bl8) {
                railShape2 = RailShape.SOUTH_WEST;
            }
            if (bl3 && !bl6 && !bl8) {
                railShape2 = RailShape.NORTH_WEST;
            }
            if (bl13 && !bl6 && !bl7) {
                railShape2 = RailShape.NORTH_EAST;
            }
        }
        if (railShape2 == null) {
            if (bl9 && bl4) {
                railShape2 = railShape;
            } else if (bl9) {
                railShape2 = RailShape.NORTH_SOUTH;
            } else if (bl4) {
                railShape2 = RailShape.EAST_WEST;
            }
            if (!this.disableCorners) {
                if (bl) {
                    if (bl11) {
                        railShape2 = RailShape.SOUTH_EAST;
                    }
                    if (bl12) {
                        railShape2 = RailShape.SOUTH_WEST;
                    }
                    if (bl13) {
                        railShape2 = RailShape.NORTH_EAST;
                    }
                    if (bl3) {
                        railShape2 = RailShape.NORTH_WEST;
                    }
                } else {
                    if (bl3) {
                        railShape2 = RailShape.NORTH_WEST;
                    }
                    if (bl13) {
                        railShape2 = RailShape.NORTH_EAST;
                    }
                    if (bl12) {
                        railShape2 = RailShape.SOUTH_WEST;
                    }
                    if (bl11) {
                        railShape2 = RailShape.SOUTH_EAST;
                    }
                }
            }
        }
        if (railShape2 == RailShape.NORTH_SOUTH) {
            if (AbstractRailBlock.isRail(this.world, blockPos.up())) {
                railShape2 = RailShape.ASCENDING_NORTH;
            }
            if (AbstractRailBlock.isRail(this.world, blockPos2.up())) {
                railShape2 = RailShape.ASCENDING_SOUTH;
            }
        }
        if (railShape2 == RailShape.EAST_WEST) {
            if (AbstractRailBlock.isRail(this.world, blockPos4.up())) {
                railShape2 = RailShape.ASCENDING_EAST;
            }
            if (AbstractRailBlock.isRail(this.world, blockPos3.up())) {
                railShape2 = RailShape.ASCENDING_WEST;
            }
        }
        if (railShape2 == null) {
            railShape2 = railShape;
        }
        this.reset(railShape2);
        this.newState = (BlockState)this.newState.with(this.block.getShapeProperty(), railShape2);
        if (bl2 || this.world.getBlockState(this.pos) != this.newState) {
            this.world.setBlockState(this.pos, this.newState, 0);
            for (int i = 0; i < this.connectedRails.size(); ++i) {
                RailState railState = this.createForAdjacent(this.connectedRails.get(i));
                if (railState == null) continue;
                railState.checkConnected();
                if (!railState.canConnect(this)) continue;
                railState.connect(this);
            }
        }
        return this;
    }

    public BlockState getNewState() {
        return this.newState;
    }
}

