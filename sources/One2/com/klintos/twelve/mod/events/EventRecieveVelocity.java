// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.events;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.entity.Entity;
import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventRecieveVelocity extends EventCancellable
{
    private Entity entity;
    private S12PacketEntityVelocity packetIn;
    
    public EventRecieveVelocity(final Entity entity, final S12PacketEntityVelocity packetIn) {
        this.entity = entity;
        this.packetIn = packetIn;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public S12PacketEntityVelocity getPacket() {
        return this.packetIn;
    }
}
