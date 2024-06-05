package io.github.nevalackin.radium.event.impl.render;

import io.github.nevalackin.radium.event.Event;
import io.github.nevalackin.radium.utils.render.LockedResolution;

public final class Render2DEvent implements Event {

    private final LockedResolution resolution;
    private final float partialTicks;

    public Render2DEvent(LockedResolution resolution, float partialTicks) {
        this.resolution = resolution;
        this.partialTicks = partialTicks;
    }

    public LockedResolution getResolution() {
        return resolution;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

}
