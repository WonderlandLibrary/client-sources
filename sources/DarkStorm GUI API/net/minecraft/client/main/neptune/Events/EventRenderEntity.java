package net.minecraft.client.main.neptune.Events;

import net.minecraft.client.main.neptune.memes.events.Memevnt;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderEntity implements Memevnt
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

