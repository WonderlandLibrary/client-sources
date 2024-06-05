package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;

public class EventKey extends Event {
    int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
