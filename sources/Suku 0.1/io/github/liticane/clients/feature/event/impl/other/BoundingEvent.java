package io.github.liticane.clients.feature.event.impl.other;

import io.github.liticane.clients.feature.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class BoundingEvent extends Event {
    private final Block block;
    private final BlockPos blockPosition;
    private AxisAlignedBB boundingBox;

    public BoundingEvent(Block block, BlockPos pos, AxisAlignedBB boundingBox) {
        this.block = block;
        this.blockPosition = pos;
        this.boundingBox = boundingBox;
    }

    public final Block getBlock() {
        return this.block;
    }

    public final BlockPos getBlockPos() {
        return this.blockPosition;
    }

    public final AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public final void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
}

