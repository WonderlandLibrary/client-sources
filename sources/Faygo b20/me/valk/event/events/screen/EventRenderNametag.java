package me.valk.event.events.screen;

import me.valk.event.Event;
import net.minecraft.entity.Entity;

public class EventRenderNametag extends Event {

	private Entity entity;

	public EventRenderNametag(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
		
}
