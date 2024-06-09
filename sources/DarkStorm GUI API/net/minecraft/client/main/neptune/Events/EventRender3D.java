package net.minecraft.client.main.neptune.Events;

import net.minecraft.client.main.neptune.memes.events.Memevnt;

public class EventRender3D implements Memevnt {

	public float partialTicks;

	public EventRender3D(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

}
