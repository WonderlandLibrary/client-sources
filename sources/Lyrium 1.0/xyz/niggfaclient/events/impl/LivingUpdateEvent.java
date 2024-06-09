// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import net.minecraft.entity.Entity;
import xyz.niggfaclient.events.Event;

public class LivingUpdateEvent implements Event
{
    private Entity entity;
    
    public LivingUpdateEvent(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
