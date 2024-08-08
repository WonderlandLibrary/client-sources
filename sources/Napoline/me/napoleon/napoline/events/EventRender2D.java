package me.napoleon.napoline.events;


import me.napoleon.napoline.manager.event.Event;

public class EventRender2D
        extends Event {
    /** Do we need this? */
    private float partialTicks;

    public EventRender2D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}

