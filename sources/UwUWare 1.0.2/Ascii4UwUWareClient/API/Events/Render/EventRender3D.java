
package Ascii4UwUWareClient.API.Events.Render;

import Ascii4UwUWareClient.API.Event;
import net.shadersmod.client.Shaders;

public class EventRender3D extends Event {
	private float ticks;
	private boolean isUsingShaders;

	public EventRender3D() {
		this.isUsingShaders = Shaders.getShaderPackName() != null;
	}

	public EventRender3D(float ticks) {
		this.ticks = ticks;
		this.isUsingShaders = Shaders.getShaderPackName() != null;
	}

	public float getPartialTicks() {
		return this.ticks;
	}

	public boolean isUsingShaders() {
		return this.isUsingShaders;
	}
}
