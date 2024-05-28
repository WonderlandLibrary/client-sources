package arsenic.event.impl;

import arsenic.event.types.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class EventRender2D implements Event {

    private final ScaledResolution sr;
    private final float partialTicks;

    public EventRender2D(float partialTicks) {
        this.partialTicks = partialTicks;
        this.sr = new ScaledResolution(Minecraft.getMinecraft());
    }

    public EventRender2D(float partialTicks, ScaledResolution sr) {
        this.partialTicks = partialTicks;
        this.sr = sr;
    }

    public ScaledResolution getSr() { return sr; }

    public float getPartialTicks() { return partialTicks; }

}
