package me.rich.event.events;

import me.rich.event.Event;

public class EventChatMessage
extends Event {
    public String message;
    public boolean cancelled;

    public EventChatMessage(String chat) {
        this.message = chat;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}