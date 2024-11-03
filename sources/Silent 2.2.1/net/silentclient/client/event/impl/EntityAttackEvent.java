package net.silentclient.client.event.impl;

import net.minecraft.entity.Entity;
import net.silentclient.client.event.Event;

public class EntityAttackEvent extends Event {
	private final Entity victim;
	private final Entity player;
	
	public EntityAttackEvent(Entity victim, Entity player) {
		this.victim = victim; 
		this.player = player;
	}
	
	public Entity getVictim() {
		return victim;
	}

	public Entity getPlayer() {
		return player;
	}
}
