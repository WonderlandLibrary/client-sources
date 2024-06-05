package me.vaziak.sensation.client.api.event.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author antja03
 */
public class EventRender2D {
    private ScaledResolution resolution;
	private boolean pre;

    public EventRender2D(boolean pre) {
        resolution = new ScaledResolution(Minecraft.getMinecraft());
        this.pre = pre;
    }

    public boolean isPre() {
    	return pre;
    }
    
    public ScaledResolution getResolution() {
        return resolution;
    }
}
