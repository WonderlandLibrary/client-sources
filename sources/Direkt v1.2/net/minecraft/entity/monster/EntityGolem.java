package net.minecraft.entity.monster;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class EntityGolem extends EntityCreature implements IAnimals {
	public EntityGolem(World worldIn) {
		super(worldIn);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	@Nullable
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	@Nullable
	protected SoundEvent getHurtSound() {
		return null;
	}

	@Override
	@Nullable
	protected SoundEvent getDeathSound() {
		return null;
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
		return false;
	}
}
