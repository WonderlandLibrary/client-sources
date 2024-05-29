package dev.event;

public class EventStorage<T> {

    private final Object owner;
    private final EventListener<T> callback;

    public EventStorage(Object owner, EventListener<T> callback) {
        this.owner = owner;
        this.callback = callback;
    }

    public Object getOwner() {
        return owner;
    }

    public EventListener<T> getCallback() {
        return callback;
    }

}
