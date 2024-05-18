package me.valk.event.events.screen;

import me.valk.event.Event;
import me.valk.event.EventType;
import net.minecraft.entity.EntityLivingBase;

public class EventRenderEntity extends Event {

	private EntityLivingBase entity;
	private EventType type;
	public EventRenderEntity(EntityLivingBase entity, EventType type) {
		this.entity = entity;
		this.type = type;
	}
	public EntityLivingBase getEntity() {
		return entity;
	}
	public EventType getType() {
		return type;
	}
	
}
