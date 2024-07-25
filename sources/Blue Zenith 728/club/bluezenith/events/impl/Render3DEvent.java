package club.bluezenith.events.impl;

import club.bluezenith.events.Event;

public class Render3DEvent extends Event {
    public final float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
