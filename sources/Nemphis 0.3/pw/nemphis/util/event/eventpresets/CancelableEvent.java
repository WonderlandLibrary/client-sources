/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.event.eventpresets;

import pw.vertexcode.util.event.Event;

public abstract class CancelableEvent
implements Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

