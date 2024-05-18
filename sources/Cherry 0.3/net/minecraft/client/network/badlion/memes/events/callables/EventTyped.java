// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.memes.events.callables;

import net.minecraft.client.network.badlion.memes.events.Typed;
import net.minecraft.client.network.badlion.memes.events.Event;

public abstract class EventTyped implements Event, Typed
{
    private final byte type;
    
    protected EventTyped(final byte eventType) {
        this.type = eventType;
    }
    
    @Override
    public byte getType() {
        return this.type;
    }
}
