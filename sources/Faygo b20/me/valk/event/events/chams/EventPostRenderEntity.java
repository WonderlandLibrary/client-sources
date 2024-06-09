package me.valk.event.events.chams;

import me.valk.event.Event;
import net.minecraft.entity.Entity;

public class EventPostRenderEntity extends Event {
	private Entity e;

	public EventPostRenderEntity(final Entity e) {
		this.e = null;
		this.setEntity(e);
	}

	public Entity getEntity() {
		return this.e;
	}

	public void setEntity(final Entity e) {
		this.e = e;
	}
}
