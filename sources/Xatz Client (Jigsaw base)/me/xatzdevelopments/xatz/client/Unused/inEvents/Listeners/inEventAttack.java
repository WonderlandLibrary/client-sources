// 
// Decompiled by Procyon v0.5.36
// 

package me.xatzdevelopments.xatz.client.Unused.inEvents.Listeners;

import me.xatzdevelopments.xatz.client.Unused.inEvents.inEvent;
import net.minecraft.entity.Entity;

public class inEventAttack extends inEvent<inEventAttack>
{
    private boolean isCancelled;
    public Entity entity;
    
    public inEventAttack(final Entity e) {
        this.entity = e;
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
}
