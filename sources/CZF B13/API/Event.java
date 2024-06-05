/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.API;

public abstract class Event {
    public byte type;
    private boolean cancelled;

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

    public void fire() {
        cancelled = false;
        EventSystem.fire(this);
    }

}
