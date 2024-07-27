package dev.nexus.events.bus;

@FunctionalInterface
public interface Listener<Event> { void call(Event event); }