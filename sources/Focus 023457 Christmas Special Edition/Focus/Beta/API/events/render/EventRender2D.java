
package Focus.Beta.API.events.render;

import Focus.Beta.API.Event;

public class EventRender2D extends Event {
	private float partialTicks;

	public EventRender2D(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return this.partialTicks;
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}
}
