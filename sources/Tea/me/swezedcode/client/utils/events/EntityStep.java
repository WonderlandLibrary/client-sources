package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

import net.minecraft.entity.Entity;

public class EntityStep extends EventCancellable {

	private float stepHeight;
	private Entity entity;
	private byte pre;

	public EntityStep(Entity entity, float stepHeight, byte pre) {
		this.entity = entity;
		this.stepHeight = stepHeight;
		this.pre = pre;
	}

	public float getStepHeight() {
		return this.stepHeight;
	}

	public void setStepHeight(float stepHeight) {
		this.stepHeight = stepHeight;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public byte getPre() {
		return this.pre;
	}
}