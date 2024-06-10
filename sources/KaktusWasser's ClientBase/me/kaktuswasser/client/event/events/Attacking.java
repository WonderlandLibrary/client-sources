// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event.events;

import me.kaktuswasser.client.event.Event;
import net.minecraft.entity.Entity;

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
