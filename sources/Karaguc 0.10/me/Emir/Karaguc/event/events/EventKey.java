package me.Emir.Karaguc.event.events;

import me.Emir.Karaguc.event.Event;

public class EventKey extends Event {
    private int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
