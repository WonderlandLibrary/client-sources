package wtf.diablo.client.event.impl.client.renderering;

import net.minecraft.client.gui.ScaledResolution;
import wtf.diablo.client.event.api.AbstractEvent;

public final class OverlayEvent extends AbstractEvent {
    private final ScaledResolution scaledResolution;

    public OverlayEvent(final ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public ScaledResolution getScaledResolution() {
        return this.scaledResolution;
    }
}
