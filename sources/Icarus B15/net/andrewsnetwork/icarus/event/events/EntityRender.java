// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.event.events;

import net.minecraft.entity.Entity;
import net.andrewsnetwork.icarus.event.Cancellable;
import net.andrewsnetwork.icarus.event.Event;

public class EntityRender extends Event implements Cancellable
{
    private Entity entity;
    private boolean cancel;
    
    public EntityRender(final Entity entity) {
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
