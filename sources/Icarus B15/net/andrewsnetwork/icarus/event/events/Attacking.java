// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.minecraft.entity.Entity;
import net.andrewsnetwork.icarus.event.Event;

public class Attacking extends Event
{
    private Entity entity;
    
    public Attacking(final Entity entity) {
        this.entity = entity;
    }
    
    public void setEntity(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
