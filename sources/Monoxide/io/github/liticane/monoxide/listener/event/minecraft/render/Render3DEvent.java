package io.github.liticane.monoxide.listener.event.minecraft.render;

import io.github.liticane.monoxide.listener.event.Event;

public class Render3DEvent extends Event {
    private float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
