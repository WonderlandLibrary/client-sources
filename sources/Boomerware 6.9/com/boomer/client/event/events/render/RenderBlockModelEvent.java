package com.boomer.client.event.events.render;

import com.boomer.client.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

/**
 * made by Xen for BoomerWare
 *
 * @since 6/15/2019
 **/
public class RenderBlockModelEvent extends Event {
    private final Block block;

    private final BlockPos pos;

    public RenderBlockModelEvent(Block block, BlockPos pos) {
        this.block = block;
        this.pos = pos;
    }

    public Block getBlock() {
        return block;
    }

    public BlockPos getPos() {
        return pos;
    }
}