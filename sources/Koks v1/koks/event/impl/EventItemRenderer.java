package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 20:00
 */
public class EventItemRenderer extends Event {

    float f, f1, firstArg, lastArg;

    public EventItemRenderer(float f, float f1) {
        this.f = f;
        this.f1 = f1;
        this.firstArg = f;
        this.lastArg = 0.0F;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public float getF1() {
        return f1;
    }

    public void setF1(float f1) {
        this.f1 = f1;
    }

    public float getFirstArg() {
        return firstArg;
    }

    public void setFirstArg(float firstArg) {
        this.firstArg = firstArg;
    }

    public float getLastArg() {
        return lastArg;
    }

    public void setLastArg(float lastArg) {
        this.lastArg = lastArg;
    }

}