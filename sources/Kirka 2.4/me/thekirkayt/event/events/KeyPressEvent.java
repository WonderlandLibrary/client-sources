/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event.events;

import me.thekirkayt.event.Event;

public class KeyPressEvent
extends Event {
    private int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

