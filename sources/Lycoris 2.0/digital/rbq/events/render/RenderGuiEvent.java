/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.render;

import net.minecraft.client.gui.ScaledResolution;
import digital.rbq.events.Event;

public final class RenderGuiEvent
implements Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;

    public RenderGuiEvent(ScaledResolution scaledResolution, float partialTicks) {
        this.scaledResolution = scaledResolution;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return this.scaledResolution;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

