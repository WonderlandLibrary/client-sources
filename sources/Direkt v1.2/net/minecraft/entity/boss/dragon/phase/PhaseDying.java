package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseDying extends PhaseBase {
	private Vec3d targetLocation;
	private int time;

	public PhaseDying(EntityDragon dragonIn) {
		super(dragonIn);
	}

	/**
	 * Generates particle effects appropriate to the phase (or sometimes sounds). Called by dragon's onLivingUpdate. Only used when worldObj.isRemote.
	 */
	@Override
	public void doClientRenderEffects() {
		if ((this.time++ % 10) == 0) {
			float f = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
			float f1 = (this.dragon.getRNG().nextFloat() - 0.5F) * 4.0F;
			float f2 = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
			this.dragon.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.dragon.posX + f, this.dragon.posY + 2.0D + f1, this.dragon.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	/**
	 * Gives the phase a chance to update its status. Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
	 */
	@Override
	public void doLocalUpdate() {
		++this.time;

		if (this.targetLocation == null) {
			BlockPos blockpos = this.dragon.worldObj.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION);
			this.targetLocation = new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ());
		}

		double d0 = this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);

		if ((d0 >= 100.0D) && (d0 <= 22500.0D) && !this.dragon.isCollidedHorizontally && !this.dragon.isCollidedVertically) {
			this.dragon.setHealth(1.0F);
		} else {
			this.dragon.setHealth(0.0F);
		}
	}

	/**
	 * Called when this phase is set to active
	 */
	@Override
	public void initPhase() {
		this.targetLocation = null;
		this.time = 0;
	}

	/**
	 * Returns the maximum amount dragon may rise or fall during this phase
	 */
	@Override
	public float getMaxRiseOrFall() {
		return 3.0F;
	}

	@Override
	@Nullable

	/**
	 * Returns the location the dragon is flying toward
	 */
	public Vec3d getTargetLocation() {
		return this.targetLocation;
	}

	@Override
	public PhaseList<PhaseDying> getPhaseList() {
		return PhaseList.DYING;
	}
}
