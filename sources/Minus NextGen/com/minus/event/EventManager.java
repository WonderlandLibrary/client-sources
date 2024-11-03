package com.minus.event;

import com.minus.event.events.bus.impl.EventBus;

public class EventManager {

    private final EventBus<Event> bus;

    public EventManager() {
        this.bus = new EventBus<>();
    }

    public EventBus<Event> getBus() { return bus; }

    public void subscribe(Object listener) {
        bus.subscribe(listener);
    }

    public void unsubscribe(Object listener) {
        bus.unsubscribe(listener);
    }

    public void post(Event event) {
        bus.post(event);
    }

}