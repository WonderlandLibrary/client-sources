package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIRestrictSun extends EntityAIBase {
	private final EntityCreature theEntity;

	public EntityAIRestrictSun(EntityCreature creature) {
		this.theEntity = creature;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		return this.theEntity.worldObj.isDaytime() && (this.theEntity.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		((PathNavigateGround) this.theEntity.getNavigator()).setAvoidSun(true);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		((PathNavigateGround) this.theEntity.getNavigator()).setAvoidSun(false);
	}
}
