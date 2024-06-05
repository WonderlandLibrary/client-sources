package me.dinozoid.strife.event.implementations.player;

import me.dinozoid.strife.event.Event;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class BoundingBoxEvent extends Event {

    private BlockPos blockPos;
    private AxisAlignedBB bounds;

    public BoundingBoxEvent(BlockPos blockPos, AxisAlignedBB bounds) {
        this.blockPos = blockPos;
        this.bounds = bounds;
    }

    public BlockPos blockPos() {
        return blockPos;
    }

    public void blockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public AxisAlignedBB bounds() {
        return bounds;
    }

    public void bounds(AxisAlignedBB bounds) {
        this.bounds = bounds;
    }
}
