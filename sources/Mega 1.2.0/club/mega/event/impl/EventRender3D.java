package club.mega.event.impl;

import club.mega.event.Event;

public class EventRender3D extends Event {

    public float partialTicks;

    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
