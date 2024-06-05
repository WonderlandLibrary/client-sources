package zamorozka.event.events;

import net.minecraft.client.gui.ScaledResolution;
import zamorozka.event.Event;

public class EventCrosshair extends Event {
    private final ScaledResolution sr;

    public EventCrosshair(ScaledResolution sr) {
        this.sr = sr;
    }

    public ScaledResolution getScaledRes() {
        return this.sr;
    }
}