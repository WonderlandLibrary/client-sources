package io.github.liticane.monoxide.listener.event.minecraft.input;

import io.github.liticane.monoxide.listener.event.Event;

public class KeyInputEvent extends Event {
    private final int key;

    public KeyInputEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
