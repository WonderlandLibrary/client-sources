/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.render;

import net.minecraft.client.gui.ScaledResolution;
import digital.rbq.events.Cancellable;
import digital.rbq.events.Event;

public final class RenderCrosshairEvent
extends Cancellable
implements Event {
    private final ScaledResolution sr;

    public RenderCrosshairEvent(ScaledResolution sr) {
        this.sr = sr;
    }

    public ScaledResolution getScaledRes() {
        return this.sr;
    }
}

