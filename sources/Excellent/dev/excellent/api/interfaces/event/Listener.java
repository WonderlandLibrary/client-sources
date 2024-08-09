package dev.excellent.api.interfaces.event;

public interface Listener<T> {
    void handle(T event);
}
