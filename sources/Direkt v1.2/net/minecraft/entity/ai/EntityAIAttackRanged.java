package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.util.math.MathHelper;

public class EntityAIAttackRanged extends EntityAIBase {
	/** The entity the AI instance has been applied to */
	private final EntityLiving entityHost;

	/**
	 * The entity (as a RangedAttackMob) the AI instance has been applied to.
	 */
	private final IRangedAttackMob rangedAttackEntityHost;
	private EntityLivingBase attackTarget;

	/**
	 * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the maxRangedAttackTime.
	 */
	private int rangedAttackTime;
	private final double entityMoveSpeed;
	private int seeTime;
	private final int attackIntervalMin;

	/**
	 * The maximum time the AI has to wait before peforming another ranged attack.
	 */
	private final int maxRangedAttackTime;
	private final float attackRadius;
	private final float maxAttackDistance;

	public EntityAIAttackRanged(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
		this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
	}

	public EntityAIAttackRanged(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
		this.rangedAttackTime = -1;

		if (!(attacker instanceof EntityLivingBase)) {
			throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
		} else {
			this.rangedAttackEntityHost = attacker;
			this.entityHost = (EntityLiving) attacker;
			this.entityMoveSpeed = movespeed;
			this.attackIntervalMin = p_i1650_4_;
			this.maxRangedAttackTime = maxAttackTime;
			this.attackRadius = maxAttackDistanceIn;
			this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
			this.setMutexBits(3);
		}
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();

		if (entitylivingbase == null) {
			return false;
		} else {
			this.attackTarget = entitylivingbase;
			return true;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		this.attackTarget = null;
		this.seeTime = 0;
		this.rangedAttackTime = -1;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
		boolean flag = this.entityHost.getEntitySenses().canSee(this.attackTarget);

		if (flag) {
			++this.seeTime;
		} else {
			this.seeTime = 0;
		}

		if ((d0 <= this.maxAttackDistance) && (this.seeTime >= 20)) {
			this.entityHost.getNavigator().clearPathEntity();
		} else {
			this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
		}

		this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);

		if (--this.rangedAttackTime == 0) {
			if ((d0 > this.maxAttackDistance) || !flag) { return; }

			float f = MathHelper.sqrt_double(d0) / this.attackRadius;
			float lvt_5_1_ = MathHelper.clamp_float(f, 0.1F, 1.0F);
			this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
			this.rangedAttackTime = MathHelper.floor_float((f * (this.maxRangedAttackTime - this.attackIntervalMin)) + this.attackIntervalMin);
		} else if (this.rangedAttackTime < 0) {
			float f2 = MathHelper.sqrt_double(d0) / this.attackRadius;
			this.rangedAttackTime = MathHelper.floor_float((f2 * (this.maxRangedAttackTime - this.attackIntervalMin)) + this.attackIntervalMin);
		}
	}
}
