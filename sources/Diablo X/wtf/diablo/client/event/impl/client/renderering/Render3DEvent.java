package wtf.diablo.client.event.impl.client.renderering;

import wtf.diablo.client.event.api.AbstractEvent;

public final class Render3DEvent extends AbstractEvent {
    private final float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
