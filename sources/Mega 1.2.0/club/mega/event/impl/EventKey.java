package club.mega.event.impl;

import club.mega.event.Event;

public class EventKey extends Event {

    private int key;

    public EventKey(final int key) {
        this.key = key;
    }

    public final int getKey() {
        return key;
    }

    public final void setKey(final int key) {
        this.key = key;
    }

}
