package client.metaware.impl.event.impl.system;


import client.metaware.impl.event.Event;

public class KeyEvent extends Event {
    private final int key;

    public KeyEvent(final int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
