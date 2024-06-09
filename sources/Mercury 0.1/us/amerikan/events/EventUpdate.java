/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.events;

import com.darkmagician6.eventapi.events.Event;

public class EventUpdate
implements Event {
    public boolean Cancellable;

    public boolean isCancellable() {
        return this.Cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.Cancellable = cancellable;
    }
}

