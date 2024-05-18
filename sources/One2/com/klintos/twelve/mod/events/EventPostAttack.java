// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.events;

import net.minecraft.entity.Entity;
import com.darkmagician6.eventapi.events.Event;

public class EventPostAttack implements Event
{
    private Entity attacker;
    private Entity target;
    
    public EventPostAttack(final Entity attacker, final Entity target) {
        this.attacker = attacker;
        this.target = target;
    }
    
    public Entity getAttacker() {
        return this.attacker;
    }
    
    public Entity getTarget() {
        return this.target;
    }
}
