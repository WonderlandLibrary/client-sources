package club.mega.event.impl;

import club.mega.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventResize extends Event {

    private final ScaledResolution scaledResolution;

    public EventResize(final ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public final ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

}
