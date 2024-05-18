// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.network.badlion.Utils.Location;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.client.network.badlion.memes.events.Event;

public class EventEntityCollision implements Event
{
    private Entity entity;
    private Block block;
    private Location location;
    private AxisAlignedBB boundingBox;
    
    public EventEntityCollision(final Entity entity, final Location location, final AxisAlignedBB boundingBox, final Block block) {
        this.entity = entity;
        this.location = location;
        this.boundingBox = boundingBox;
        this.block = block;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public Block getBlock() {
        return this.block;
    }
}
