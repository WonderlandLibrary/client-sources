package de.tired.event.events;

import de.tired.event.Event;

public class Render3DEventPRE extends Event {

    public float partialTicks;

    public Render3DEventPRE(float partialTicks) {
        this.partialTicks = partialTicks;
    }

}
