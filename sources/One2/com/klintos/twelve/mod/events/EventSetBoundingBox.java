// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.events;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import com.darkmagician6.eventapi.events.Event;

public class EventSetBoundingBox implements Event
{
    private AxisAlignedBB aabb;
    private Block block;
    private int x;
    private int y;
    private int z;
    
    public EventSetBoundingBox(final AxisAlignedBB aabb, final Block block, final int x, final int y, final int z) {
        this.aabb = aabb;
        this.block = block;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public AxisAlignedBB getAABB() {
        return this.aabb;
    }
    
    public void setAABB(final AxisAlignedBB aabb) {
        this.aabb = aabb;
    }
    
    public Block getBlock() {
        return this.block;
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
}
