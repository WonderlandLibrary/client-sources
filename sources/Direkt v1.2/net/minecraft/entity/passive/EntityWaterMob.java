package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityWaterMob extends EntityLiving implements IAnimals {
	public EntityWaterMob(World worldIn) {
		super(worldIn);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return true;
	}

	/**
	 * Checks that the entity is not colliding with any blocks / liquids
	 */
	@Override
	public boolean isNotColliding() {
		return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this);
	}

	/**
	 * Get number of ticks, at least during which the living entity will be silent.
	 */
	@Override
	public int getTalkInterval() {
		return 120;
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return true;
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	@Override
	protected int getExperiencePoints(EntityPlayer player) {
		return 1 + this.worldObj.rand.nextInt(3);
	}

	/**
	 * Gets called every tick from main Entity class
	 */
	@Override
	public void onEntityUpdate() {
		int i = this.getAir();
		super.onEntityUpdate();

		if (this.isEntityAlive() && !this.isInWater()) {
			--i;
			this.setAir(i);

			if (this.getAir() == -20) {
				this.setAir(0);
				this.attackEntityFrom(DamageSource.drown, 2.0F);
			}
		} else {
			this.setAir(300);
		}
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}
}
