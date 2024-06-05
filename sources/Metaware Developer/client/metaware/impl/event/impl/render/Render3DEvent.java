package client.metaware.impl.event.impl.render;


import client.metaware.impl.event.Event;

public class Render3DEvent extends Event {

    private final float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
