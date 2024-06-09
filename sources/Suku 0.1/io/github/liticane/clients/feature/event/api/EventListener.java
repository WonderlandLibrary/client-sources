package io.github.liticane.clients.feature.event.api;

@FunctionalInterface
public interface EventListener<Event> {
    void call(Event event);
}