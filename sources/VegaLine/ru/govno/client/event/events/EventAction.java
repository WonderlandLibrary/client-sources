/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import ru.govno.client.event.Event;

public class EventAction
extends Event {
    private boolean sprintState;

    public EventAction(boolean sprintState) {
        this.sprintState = sprintState;
    }

    public void setSprintState(boolean sprintState) {
        this.sprintState = sprintState;
    }

    public boolean getSprintState() {
        return this.sprintState;
    }
}

