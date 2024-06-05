package club.mega.event.impl;

import club.mega.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRender2D extends Event {

    private final ScaledResolution scaledResolution;

    public EventRender2D(final ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public final ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

}
