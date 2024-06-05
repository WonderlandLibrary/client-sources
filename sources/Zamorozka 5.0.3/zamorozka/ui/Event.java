package zamorozka.ui;

public abstract class Event {
    protected boolean cancelled;

    public void fire() {
        cancelled = false;

    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}