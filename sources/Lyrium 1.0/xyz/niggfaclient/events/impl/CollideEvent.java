// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.block.Block;
import xyz.niggfaclient.events.CancellableEvent;

public class CollideEvent extends CancellableEvent
{
    public Block block;
    public AxisAlignedBB boundingBox;
    public double x;
    public double y;
    public double z;
    
    public CollideEvent(final AxisAlignedBB bb, final Block block, final double x, final double y, final double z) {
        this.block = block;
        this.boundingBox = bb;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public void setBlock(final Block block) {
        this.block = block;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
}
