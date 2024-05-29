package dev.event;

public interface EventListener<T> {
    void call(T event);
}
