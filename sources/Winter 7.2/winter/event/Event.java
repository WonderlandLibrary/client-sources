/*
 * Decompiled with CFR 0_122.
 */
package winter.event;

public class Event {
    private boolean cancelled = false;
    private boolean endEvent = false;

    public void breakEvent() {
        this.endEvent = true;
    }

    public boolean hasEnded() {
        return this.endEvent;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}

