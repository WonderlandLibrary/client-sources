/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.event.impl;

import cc.diablo.event.Event;

public class ChatEvent
extends Event {
    public String message;

    public ChatEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

