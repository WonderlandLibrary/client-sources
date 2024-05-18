// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.client.network.badlion.memes.events.callables.MemeMeable;

public class EventChatSend extends MemeMeable
{
    public String message;
    
    public EventChatSend(final String message) {
        this.message = message;
    }
}
