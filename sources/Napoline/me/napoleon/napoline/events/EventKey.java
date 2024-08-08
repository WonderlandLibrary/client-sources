package me.napoleon.napoline.events;

import me.napoleon.napoline.manager.event.Event;

public class EventKey extends Event {
    int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
