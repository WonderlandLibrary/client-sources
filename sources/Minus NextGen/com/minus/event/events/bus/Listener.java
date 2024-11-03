package com.minus.event.events.bus;

@FunctionalInterface
public interface Listener<Event> { void call(Event event); }