package com.minus.event.events.bus;

public interface Bus<Event> {
    void subscribe(final Object subscriber);
    void unsubscribe(final Object subscriber);
    void post(final Event event);
}