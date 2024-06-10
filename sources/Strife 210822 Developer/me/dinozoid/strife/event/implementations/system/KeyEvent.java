package me.dinozoid.strife.event.implementations.system;

import me.dinozoid.strife.event.Event;

public class KeyEvent extends Event {
    private final int key;

    public KeyEvent(final int key) {
        this.key = key;
    }

    public int key() {
        return key;
    }
}
