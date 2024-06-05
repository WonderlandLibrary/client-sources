package xyz.vergoclient.event.impl;

import xyz.vergoclient.event.Event;
import net.minecraft.client.entity.AbstractClientPlayer;

public class EventPlayerRender extends Event {
	
	public EventPlayerRender(AbstractClientPlayer entity) {
		
		this.entity = entity;
		
	}
	
	public AbstractClientPlayer entity;
	
}
