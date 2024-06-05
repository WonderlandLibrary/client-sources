package de.tired.event.events;

import de.tired.event.Event;

public class Render3DEvent extends Event {

    public float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

}
