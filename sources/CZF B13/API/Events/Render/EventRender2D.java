/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.API.Events.Render;

import gq.vapu.czfclient.API.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRender2D extends Event {
    public ScaledResolution res;
    private float partialTicks;

    public EventRender2D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }
}
