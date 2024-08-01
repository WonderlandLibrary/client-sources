package wtf.diablo.client.event.impl.client;

import wtf.diablo.client.event.api.AbstractEvent;

public final class KeyEvent extends AbstractEvent {
    private final int key;

    public KeyEvent(final int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
