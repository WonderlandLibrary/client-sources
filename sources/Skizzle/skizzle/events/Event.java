/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events;

import skizzle.events.EventDirection;
import skizzle.events.EventType;

public class Event<T> {
    public EventDirection direction;
    public boolean cancelled;
    public EventType type;

    public boolean isOutgoing() {
        Event Nigga;
        if (Nigga.direction == null) {
            return false;
        }
        return Nigga.direction == EventDirection.OUTGOING;
    }

    public void setType(EventType Nigga) {
        Nigga.type = Nigga;
    }

    public boolean isIncoming() {
        Event Nigga;
        if (Nigga.direction == null) {
            return false;
        }
        return Nigga.direction == EventDirection.INCOMING;
    }

    public static {
        throw throwable;
    }

    public boolean isPost() {
        Event Nigga;
        if (Nigga.type == null) {
            return false;
        }
        return Nigga.type == EventType.POST;
    }

    public void setDirection(EventDirection Nigga) {
        Nigga.direction = Nigga;
    }

    public boolean isCancelled() {
        Event Nigga;
        return Nigga.cancelled;
    }

    public boolean isPre() {
        Event Nigga;
        if (Nigga.type == null) {
            return false;
        }
        return Nigga.type == EventType.PRE;
    }

    public Event() {
        Event Nigga;
    }

    public EventDirection getDirection() {
        Event Nigga;
        return Nigga.direction;
    }

    public void setCancelled(boolean Nigga) {
        Nigga.cancelled = Nigga;
    }

    public EventType getType() {
        Event Nigga;
        return Nigga.type;
    }
}

