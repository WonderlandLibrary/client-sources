package me.rich.event.events;

import me.rich.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventCrosshair extends Event {
    private final ScaledResolution sr;

    public EventCrosshair(ScaledResolution sr) {
        this.sr = sr;
    }

    public ScaledResolution getScaledRes() {
        return this.sr;
    }
}