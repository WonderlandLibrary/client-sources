/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.client.gui.ScaledResolution;
import ru.govno.client.event.Event;

public class EventRender2D
extends Event {
    private final ScaledResolution resolution;
    private final float partialticks;

    public EventRender2D(ScaledResolution resolution, float partialticks) {
        this.resolution = resolution;
        this.partialticks = partialticks;
    }

    public ScaledResolution getResolution() {
        return this.resolution;
    }

    public float getPartialTicks() {
        return this.partialticks;
    }
}

