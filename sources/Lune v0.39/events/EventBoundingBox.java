package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventBoundingBox extends Event {
    private int x;
    private int y;
    private int z;
    private Block block;
    private AxisAlignedBB boundingBox;

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final int getZ() {
        return this.z;
    }

    public final Block getBlock() {
        return this.block;
    }

    public final AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public final void setBoundingBox(AxisAlignedBB var1) {
        this.boundingBox = var1;
    }

    public EventBoundingBox(BlockPos blockPos, Block block, AxisAlignedBB boundingBox) {
        this.block = block;
        this.boundingBox = boundingBox;
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
    }
}