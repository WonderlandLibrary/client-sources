package xyz.vergoclient.event.impl;

import xyz.vergoclient.event.Event;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

public class EventSetSkin extends Event {
	
	public EventSetSkin(AbstractClientPlayer player, ResourceLocation resourceLocation) {
		this.player = player;
		this.resourceLocation = resourceLocation;
	}
	
	public AbstractClientPlayer player;
	public ResourceLocation resourceLocation;
	
}
