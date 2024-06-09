package me.gishreload.yukon.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

public class HealthUpdateEvent extends Event implements Cancellable {
	private boolean cancelled;

	private Minecraft minecraft = Minecraft.getMinecraft();

	private final EntityLiving entity;
	private final float healthDiffrence;
	private final float newHealth;

	public HealthUpdateEvent(Minecraft mc, final EntityLiving entity, final float healthDifference,
			final float newHealth) {
		this.minecraft = mc;
		this.healthDiffrence = healthDifference;
		this.newHealth = newHealth;
		this.entity = entity;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	public float getHealthDiffrence() {
		return healthDiffrence;
	}

	public EntityLiving getEntity() {
		return entity;
	}

	public Minecraft getMinecraft() {
		return minecraft;
	}

	public float getNewHealth() {
		return newHealth;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
