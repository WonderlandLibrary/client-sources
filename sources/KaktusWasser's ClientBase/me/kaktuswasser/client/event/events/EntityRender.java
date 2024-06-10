// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.event.events;

import me.kaktuswasser.client.event.Cancellable;
import me.kaktuswasser.client.event.Event;
import net.minecraft.entity.Entity;

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
