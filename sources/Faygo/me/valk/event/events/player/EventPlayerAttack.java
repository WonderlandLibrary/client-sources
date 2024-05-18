package me.valk.event.events.player;

import me.valk.event.Event;
import me.valk.event.EventType;
import net.minecraft.entity.Entity;

public class EventPlayerAttack extends Event {

	private EventType type;
	private Entity entity;
	
	public EventPlayerAttack(EventType type, Entity entity) {
		this.type = type;
		this.entity = entity;
	}
	
	public EventType getType() {
		return type;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
}
