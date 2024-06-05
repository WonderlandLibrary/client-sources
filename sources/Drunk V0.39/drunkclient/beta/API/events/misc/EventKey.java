/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.events.misc;

import drunkclient.beta.API.Event;

public class EventKey
extends Event {
    private int key;

    public EventKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

