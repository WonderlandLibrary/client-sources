// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.player;

import net.minecraft.entity.EntityLivingBase;
import gg.childtrafficking.smokex.event.Event;

public final class EventDamageEntity extends Event
{
    private final EntityLivingBase entity;
    private final double damage;
    
    public EventDamageEntity(final EntityLivingBase entity, final double damage) {
        this.entity = entity;
        this.damage = damage;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public double getDamage() {
        return this.damage;
    }
}
