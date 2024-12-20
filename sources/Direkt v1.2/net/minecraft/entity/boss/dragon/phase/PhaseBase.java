package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class PhaseBase implements IPhase {
	protected final EntityDragon dragon;

	public PhaseBase(EntityDragon dragonIn) {
		this.dragon = dragonIn;
	}

	@Override
	public boolean getIsStationary() {
		return false;
	}

	/**
	 * Generates particle effects appropriate to the phase (or sometimes sounds). Called by dragon's onLivingUpdate. Only used when worldObj.isRemote.
	 */
	@Override
	public void doClientRenderEffects() {
	}

	/**
	 * Gives the phase a chance to update its status. Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
	 */
	@Override
	public void doLocalUpdate() {
	}

	@Override
	public void onCrystalDestroyed(EntityEnderCrystal crystal, BlockPos pos, DamageSource dmgSrc, @Nullable EntityPlayer plyr) {
	}

	/**
	 * Called when this phase is set to active
	 */
	@Override
	public void initPhase() {
	}

	@Override
	public void removeAreaEffect() {
	}

	/**
	 * Returns the maximum amount dragon may rise or fall during this phase
	 */
	@Override
	public float getMaxRiseOrFall() {
		return 0.6F;
	}

	@Override
	@Nullable

	/**
	 * Returns the location the dragon is flying toward
	 */
	public Vec3d getTargetLocation() {
		return null;
	}

	/**
	 * Normally, just returns damage. If dragon is sitting and src is an arrow, arrow is enflamed and zero damage returned.
	 */
	@Override
	public float getAdjustedDamage(EntityDragonPart pt, DamageSource src, float damage) {
		return damage;
	}

	@Override
	public float getYawFactor() {
		float f = MathHelper.sqrt_double((this.dragon.motionX * this.dragon.motionX) + (this.dragon.motionZ * this.dragon.motionZ)) + 1.0F;
		float f1 = Math.min(f, 40.0F);
		return 0.7F / f1 / f;
	}
}
