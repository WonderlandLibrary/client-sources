package io.github.nevalackin.radium.event.impl.render;

import io.github.nevalackin.radium.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public final class WindowResizeEvent implements Event {

    private final ScaledResolution scaledResolution;

    public WindowResizeEvent(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

}
