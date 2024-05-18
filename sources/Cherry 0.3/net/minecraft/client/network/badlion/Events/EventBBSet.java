// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.client.network.badlion.memes.events.Event;

public class EventBBSet implements Event
{
    public Block block;
    public BlockPos pos;
    public AxisAlignedBB boundingBox;
    
    public EventBBSet(final Block block, final BlockPos pos, final AxisAlignedBB boundingBox) {
        this.block = block;
        this.pos = pos;
        this.boundingBox = boundingBox;
    }
}
