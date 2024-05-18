/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event;

import me.thekirkayt.event.Event;

public class MouseEvent
extends Event {
    private int key;

    public MouseEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

