package white.floor.event.event;

import net.minecraft.client.gui.ScaledResolution;
import white.floor.event.Event;

public class EventRender2D extends Event {

    private ScaledResolution resolution;
    private float partialticks;

    public EventRender2D(ScaledResolution resolution, float partialticks) {
        this.resolution = resolution;
        this.partialticks = partialticks;
    }

    public ScaledResolution getResolution() {
        return resolution;
    }

    public float getPartialTicks() {
        return partialticks;
    }
}