// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import net.minecraft.client.network.badlion.memes.events.Event;

public class EventBlockBB implements Event
{
    public Block block;
    public AxisAlignedBB boundingBox;
    public int x;
    public int y;
    public int z;
    
    public Block getBlock() {
        return this.block;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
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
    
    public void setBlock(final Block block, final AxisAlignedBB boundingBox, final int x, final int y, final int z) {
        this.block = block;
        this.boundingBox = boundingBox;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
}
