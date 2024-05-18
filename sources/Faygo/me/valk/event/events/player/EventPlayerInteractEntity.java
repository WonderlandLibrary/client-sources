package me.valk.event.events.player;

import me.valk.event.Event;
import me.valk.event.EventType;
import net.minecraft.entity.Entity;

public class EventPlayerInteractEntity extends Event {

	private Entity entity;
	private EventType type;
	
	public EventPlayerInteractEntity(Entity entity, EventType type) {
		this.entity = entity;
		this.type = type;
	}
	
	public Entity getEntity() {
		return entity;
	}
	public EventType getType() {
		return type;
	}
	
	
}
