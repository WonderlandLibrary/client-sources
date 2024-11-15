package dev.lvstrng.argon.event;

public abstract class CancellableEvent extends Event {
    private boolean cancel;

    public CancellableEvent() {
        this.cancel = false;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void cancelEvent() {
        this.cancel = true;
    }
}