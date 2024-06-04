package com.polarware.event.bus;

@FunctionalInterface
public interface Listener<Event> {
    void call(Event event);
}