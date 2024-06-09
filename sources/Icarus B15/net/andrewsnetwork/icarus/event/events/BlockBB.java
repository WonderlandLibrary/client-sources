// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import net.andrewsnetwork.icarus.event.Event;

public class BlockBB extends Event
{
    private int x;
    private int y;
    private int z;
    private final Block block;
    private AxisAlignedBB bb;
    
    public BlockBB(final int x, final int y, final int z, final Block block, final AxisAlignedBB bb) {
        this.bb = bb;
        this.x = x;
        this.y = y;
        this.z = z;
        this.block = block;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.bb;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public void setBoundingBox(final AxisAlignedBB bb) {
        this.bb = bb;
    }
}
