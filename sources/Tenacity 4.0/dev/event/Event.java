package dev.event;

public class Event {

    private boolean cancelled;
    private Type type;

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isPre() {
        return this.type == Type.PRE;
    }

    public boolean isPost() {
        return this.type == Type.POST;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        PRE, POST
    }

}
