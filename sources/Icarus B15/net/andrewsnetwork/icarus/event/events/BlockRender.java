// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.andrewsnetwork.icarus.event.Event;

public class BlockRender extends Event
{
    private final Block block;
    private final BlockPos pos;
    
    public BlockRender(final Block block, final BlockPos pos) {
        this.block = block;
        this.pos = pos;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
}
