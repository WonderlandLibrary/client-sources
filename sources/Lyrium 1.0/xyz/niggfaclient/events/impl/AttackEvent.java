// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import net.minecraft.entity.Entity;
import xyz.niggfaclient.events.Event;

public class AttackEvent implements Event
{
    public Entity target;
    
    public AttackEvent(final Entity target) {
        this.target = target;
    }
    
    public Entity getTarget() {
        return this.target;
    }
    
    public void setTarget(final Entity target) {
        this.target = target;
    }
}
