/*
 * Decompiled with CFR 0_122.
 */
package Monix.Event.events;

import Monix.Event.Event;

public class EventKeyboard
extends Event {
    public int key;

    public EventKeyboard(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}

