package Hydro.event.events;

import Hydro.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRender3D extends Event {
	
	
	public final ScaledResolution scaledResolution;
	
	public EventRender3D(ScaledResolution scaledResolution, float ticks) {
		this.partialTicks = ticks;
		this.scaledResolution = scaledResolution;
	}
	
	float partialTicks;
	
	public float getTicks() {
		return this.partialTicks;
	}

	public float getPartialTicks() {
	    return this.partialTicks;
	  }

}
