package koks.event.impl;

import koks.event.Event;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 08:31
 */
public class EventRender3D extends Event {

    float partialTicks;

    public EventRender3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

}