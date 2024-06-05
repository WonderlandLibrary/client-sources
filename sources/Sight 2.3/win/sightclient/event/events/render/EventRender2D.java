package win.sightclient.event.events.render;

import win.sightclient.Sight;
import win.sightclient.event.Event;

public class EventRender2D extends Event {
	
	private float partialTicks;
	
	public EventRender2D(float partialTicks) {
		this.partialTicks = partialTicks;
	}
	
	@Override
	public void call() {
		Sight.instance.nm.render();
		super.call();
	}
	
	public float getPartialTicks() {
		return this.partialTicks;
	}
} 
