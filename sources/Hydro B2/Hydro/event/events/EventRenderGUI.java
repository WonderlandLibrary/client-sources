package Hydro.event.events;

import Hydro.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRenderGUI extends Event<EventRenderGUI> {
	
	public ScaledResolution sr;
	
	public EventRenderGUI(ScaledResolution sr) {
		this.sr = sr;
	}

}
