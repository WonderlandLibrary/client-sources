package us.dev.direkt.event.internal.events.game.render;

import net.minecraft.client.gui.ScaledResolution;
import us.dev.direkt.event.Event;

/**
 * @author Foundry
 */
public class EventRender2D implements Event {

	private ScaledResolution scaledResolution;
	
	public EventRender2D(ScaledResolution scaledResolution){
		this.scaledResolution = scaledResolution;
	}
	
	public ScaledResolution getScaledResolution() {
		return this.scaledResolution;
	}
}
