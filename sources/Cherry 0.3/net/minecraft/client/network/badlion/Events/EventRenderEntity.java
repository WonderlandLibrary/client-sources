// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.network.badlion.memes.events.Event;

public class EventRenderEntity implements Event
{
    private EntityLivingBase entity;
    public EventState type;
    
    public EventRenderEntity(final EntityLivingBase entity, final EventState type) {
        this.entity = entity;
        this.type = type;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public EventState getType() {
        return this.type;
    }
}
