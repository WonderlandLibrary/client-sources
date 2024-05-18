package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

import shadersmod.client.Shaders;

public class EventRender extends EventCancellable {

	private float ticks;
	private boolean isUsingShaders;

	public EventRender(float ticks) {
		this.ticks = ticks;
		this.isUsingShaders = (Shaders.getShaderPackName() != null);
	}

	public float getPartialTicks() {
		return this.ticks;
	}

	public boolean isUsingShaders() {
		return this.isUsingShaders;
	}
}
