package me.dinozoid.strife.event.implementations.render;

import me.dinozoid.strife.event.Event;

public class Render3DEvent extends Event {

    private final float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float partialTicks() {
        return partialTicks;
    }
}
