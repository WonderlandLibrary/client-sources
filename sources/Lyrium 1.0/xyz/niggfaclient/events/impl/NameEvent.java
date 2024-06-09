// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import net.minecraft.entity.Entity;
import xyz.niggfaclient.events.CancellableEvent;

public class NameEvent extends CancellableEvent
{
    private final Entity entity;
    
    public NameEvent(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
