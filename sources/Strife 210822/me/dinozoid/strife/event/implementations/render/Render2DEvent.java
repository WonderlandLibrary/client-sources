package me.dinozoid.strife.event.implementations.render;

import me.dinozoid.strife.event.Event;

public class Render2DEvent extends Event {

    private final float partialTicks;

    public Render2DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float partialTicks() {
        return partialTicks;
    }
}
