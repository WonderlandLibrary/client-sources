package club.bluezenith.events.impl;

import club.bluezenith.events.Event;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent extends Event {
    public final float partialTicks;
    public final ScaledResolution resolution;

    public final float width, height;

    public Render2DEvent(float partialTicks, ScaledResolution resolution) {
        this.partialTicks = partialTicks;
        this.resolution = resolution;
        this.width = (float) resolution.getScaledWidth_double();
        this.height = (float) resolution.getScaledHeight_double();
    }

    public int getWidthInt() {
        return resolution.getScaledWidth();
    }

    public int getHeightInt() {
        return resolution.getScaledWidth();
    }

    public double getWidth() {
        return resolution.getScaledWidth_double();
    }

    public double getHeight() {
        return resolution.getScaledHeight_double();
    }
}