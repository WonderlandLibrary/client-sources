package dev.stephen.nexus.event.bus.bus;

public interface Bus<Event> {
    void subscribe(final Object subscriber);
    void unsubscribe(final Object subscriber);
    void post(final Event event);
}