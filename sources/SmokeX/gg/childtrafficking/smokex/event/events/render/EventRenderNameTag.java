// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.render;

import net.minecraft.entity.EntityLivingBase;
import gg.childtrafficking.smokex.event.Event;

public final class EventRenderNameTag extends Event
{
    private final EntityLivingBase entityLivingBase;
    
    public EventRenderNameTag(final EntityLivingBase entityLivingBase) {
        this.entityLivingBase = entityLivingBase;
    }
    
    public EntityLivingBase getEntityLivingBase() {
        return this.entityLivingBase;
    }
}
