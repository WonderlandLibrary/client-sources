package client.metaware.impl.event.impl.render;


import client.metaware.impl.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Render2DEvent extends Event {

    private final float partialTicks;
    private final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

    public Render2DEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }
}
