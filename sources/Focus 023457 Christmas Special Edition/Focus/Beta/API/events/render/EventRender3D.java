
package Focus.Beta.API.events.render;

import Focus.Beta.API.Event;

public class EventRender3D extends Event {
	private float ticks;
	public EventRender3D() {}

	public EventRender3D(float ticks) {
		this.ticks = ticks;
		
	}

	public float getPartialTicks() {
		return this.ticks;
	}
}
