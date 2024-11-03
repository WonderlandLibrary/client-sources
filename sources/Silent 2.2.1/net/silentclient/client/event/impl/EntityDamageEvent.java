package net.silentclient.client.event.impl;

import net.minecraft.entity.Entity;
import net.silentclient.client.event.Event;

public class EntityDamageEvent extends Event {
	private Entity entity;
	
	public EntityDamageEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
