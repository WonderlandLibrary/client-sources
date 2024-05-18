/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.event.events;

import me.thekirkayt.event.Event;

public class StepEvent
extends Event {
    private double stepHeight;
    private double realHeight;
    private boolean active;
    private Event.State state;

    public StepEvent(double stepHeight, Event.State state, double realHeight) {
        this.stepHeight = stepHeight;
        this.state = state;
        this.realHeight = realHeight;
    }

    public StepEvent(double stepHeight, Event.State state) {
        this.stepHeight = stepHeight;
        this.state = state;
    }

    public double getStepHeight() {
        return this.stepHeight;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setStepHeight(double stepHeight) {
        this.stepHeight = stepHeight;
    }

    public void setActive(boolean bypass) {
        this.active = bypass;
    }

    public Event.State getState() {
        return this.state;
    }

    public void setState(Event.State state) {
        this.state = state;
    }

    public double getRealHeight() {
        return this.realHeight;
    }

    public void setRealHeight(double realHeight) {
        this.realHeight = realHeight;
    }
}

