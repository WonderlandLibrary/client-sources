/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.event;

import cc.diablo.event.EventType;

public abstract class Event {
    public boolean cancelled;
    private EventType type;

    public boolean isPre() {
        return this.type == EventType.Pre;
    }

    public boolean isPost() {
        return this.type == EventType.Post;
    }

    public void setCancelled(boolean bool) {
        this.cancelled = bool;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public EventType getType() {
        return this.type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}

