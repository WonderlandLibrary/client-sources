// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.client.network.badlion.memes.types.EventType;
import net.minecraft.client.network.badlion.memes.events.Event;

public class EventPlayerUpdate implements Event
{
    private EventType type;
    
    public EventPlayerUpdate(final EventType type) {
        this.type = type;
    }
    
    public EventType getType() {
        return this.type;
    }
}
