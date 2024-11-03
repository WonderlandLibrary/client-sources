package net.silentclient.client.event.impl;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.silentclient.client.event.EventCancelable;

public class RenderLivingEvent extends EventCancelable {
	private Entity entity;
	public double x;
	public double y;
	public double z;
	private RendererLivingEntity<EntityLivingBase> renderer;
	
	public RenderLivingEvent(Entity entity, double x, double y, double z, RendererLivingEntity<EntityLivingBase> renderer) {
		this.entity = entity;
		this.x = x;
		this.y = y;
		this.z = z;
		this.renderer = renderer;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public RendererLivingEntity<EntityLivingBase> getRenderer() {
		return renderer;
	}
}
