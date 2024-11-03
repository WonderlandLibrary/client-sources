package dev.stephen.nexus.event.bus;

@FunctionalInterface
public interface Listener<Event> { void call(Event event); }