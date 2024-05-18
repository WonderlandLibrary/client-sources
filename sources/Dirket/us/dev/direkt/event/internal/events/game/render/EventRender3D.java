package us.dev.direkt.event.internal.events.game.render;

import us.dev.direkt.event.Event;

/**
 * @author Foundry
 */
public class EventRender3D implements Event {
    private float partialTicks;

    public EventRender3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
