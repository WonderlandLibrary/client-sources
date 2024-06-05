/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.events.render;

import digital.rbq.events.Cancellable;
import digital.rbq.events.Event;

public final class Render3DEvent
extends Cancellable
implements Event {
    private final float partialTicks;

    public Render3DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

