/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API;

import drunkclient.beta.API.EventBus;

public abstract class Event {
    private boolean cancelled;
    public byte type;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public static void call(Event event) {
        EventBus.getInstance().register(event);
    }
}

