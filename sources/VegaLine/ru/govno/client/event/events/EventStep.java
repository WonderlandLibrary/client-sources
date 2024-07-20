/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import ru.govno.client.event.Event;

public class EventStep
extends Event {
    private final boolean pre;
    private double stepHeight;

    public EventStep(boolean pre, double stepHeight) {
        this.pre = pre;
        this.stepHeight = stepHeight;
    }

    public boolean isPre() {
        return this.pre;
    }

    public double getStepHeight() {
        return this.stepHeight;
    }

    public void setStepHeight(double stepHeight) {
        this.stepHeight = stepHeight;
    }
}

