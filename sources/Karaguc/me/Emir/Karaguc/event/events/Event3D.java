package me.Emir.Karaguc.event.events;

import me.Emir.Karaguc.event.Event;

public class Event3D extends Event {
    private float partialTicks;

    public Event3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
