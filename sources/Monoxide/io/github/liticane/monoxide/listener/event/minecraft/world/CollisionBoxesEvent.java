package io.github.liticane.monoxide.listener.event.minecraft.world;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import io.github.liticane.monoxide.listener.event.Event;

public class CollisionBoxesEvent extends Event {
    private final BlockPos blockPos;

    private AxisAlignedBB boundingBox;

    public CollisionBoxesEvent(BlockPos blockPos, AxisAlignedBB boundingBox) {
        this.blockPos = blockPos;
        this.boundingBox = boundingBox;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }

}
