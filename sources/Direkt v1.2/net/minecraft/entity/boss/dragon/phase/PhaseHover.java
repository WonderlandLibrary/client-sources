package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.Vec3d;

public class PhaseHover extends PhaseBase {
	private Vec3d targetLocation;

	public PhaseHover(EntityDragon dragonIn) {
		super(dragonIn);
	}

	/**
	 * Gives the phase a chance to update its status. Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
	 */
	@Override
	public void doLocalUpdate() {
		if (this.targetLocation == null) {
			this.targetLocation = new Vec3d(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
		}
	}

	@Override
	public boolean getIsStationary() {
		return true;
	}

	/**
	 * Called when this phase is set to active
	 */
	@Override
	public void initPhase() {
		this.targetLocation = null;
	}

	/**
	 * Returns the maximum amount dragon may rise or fall during this phase
	 */
	@Override
	public float getMaxRiseOrFall() {
		return 1.0F;
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
	public PhaseList<PhaseHover> getPhaseList() {
		return PhaseList.HOVER;
	}
}
