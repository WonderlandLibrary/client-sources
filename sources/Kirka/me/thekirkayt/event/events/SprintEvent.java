/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event.events;

import me.thekirkayt.event.Event;

public class SprintEvent
extends Event {
    private boolean sprinting;

    public SprintEvent(boolean sprinting) {
        this.setSprinting(sprinting);
    }

    public boolean isSprinting() {
        return this.sprinting;
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }
}

