package net.minecraft.entity.monster;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityBlaze extends EntityMob {
	/** Random offset used in floating behaviour */
	private float heightOffset = 0.5F;

	/** ticks until heightOffset is randomized */
	private int heightOffsetUpdateTime;
	private static final DataParameter<Byte> ON_FIRE = EntityDataManager.<Byte> createKey(EntityBlaze.class, DataSerializers.BYTE);

	public EntityBlaze(World worldIn) {
		super(worldIn);
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.isImmuneToFire = true;
		this.experienceValue = 10;
	}

	public static void func_189761_b(DataFixer p_189761_0_) {
		EntityLiving.func_189752_a(p_189761_0_, "Blaze");
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(4, new EntityBlaze.AIFireballAttack(this));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ON_FIRE, Byte.valueOf((byte) 0));
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return SoundEvents.ENTITY_BLAZE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_BLAZE_DEATH;
	}

	@Override
	public int getBrightnessForRender(float partialTicks) {
		return 15728880;
	}

	/**
	 * Gets how bright this entity is.
	 */
	@Override
	public float getBrightness(float partialTicks) {
		return 1.0F;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (!this.onGround && (this.motionY < 0.0D)) {
			this.motionY *= 0.6D;
		}

		if (this.worldObj.isRemote) {
			if ((this.rand.nextInt(24) == 0) && !this.isSilent()) {
				this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.rand.nextFloat(),
						(this.rand.nextFloat() * 0.7F) + 0.3F, false);
			}

			for (int i = 0; i < 2; ++i) {
				this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + ((this.rand.nextDouble() - 0.5D) * this.width), this.posY + (this.rand.nextDouble() * this.height),
						this.posZ + ((this.rand.nextDouble() - 0.5D) * this.width), 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}

		super.onLivingUpdate();
	}

	@Override
	protected void updateAITasks() {
		if (this.isWet()) {
			this.attackEntityFrom(DamageSource.drown, 1.0F);
		}

		--this.heightOffsetUpdateTime;

		if (this.heightOffsetUpdateTime <= 0) {
			this.heightOffsetUpdateTime = 100;
			this.heightOffset = 0.5F + ((float) this.rand.nextGaussian() * 3.0F);
		}

		EntityLivingBase entitylivingbase = this.getAttackTarget();

		if ((entitylivingbase != null) && ((entitylivingbase.posY + entitylivingbase.getEyeHeight()) > (this.posY + this.getEyeHeight() + this.heightOffset))) {
			this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
			this.isAirBorne = true;
		}

		super.updateAITasks();
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	/**
	 * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
	 */
	@Override
	public boolean isBurning() {
		return this.isCharged();
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_BLAZE;
	}

	public boolean isCharged() {
		return (this.dataManager.get(ON_FIRE).byteValue() & 1) != 0;
	}

	public void setOnFire(boolean onFire) {
		byte b0 = this.dataManager.get(ON_FIRE).byteValue();

		if (onFire) {
			b0 = (byte) (b0 | 1);
		} else {
			b0 = (byte) (b0 & -2);
		}

		this.dataManager.set(ON_FIRE, Byte.valueOf(b0));
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	static class AIFireballAttack extends EntityAIBase {
		private final EntityBlaze blaze;
		private int attackStep;
		private int attackTime;

		public AIFireballAttack(EntityBlaze blazeIn) {
			this.blaze = blazeIn;
			this.setMutexBits(3);
		}

		@Override
		public boolean shouldExecute() {
			EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
			return (entitylivingbase != null) && entitylivingbase.isEntityAlive();
		}

		@Override
		public void startExecuting() {
			this.attackStep = 0;
		}

		@Override
		public void resetTask() {
			this.blaze.setOnFire(false);
		}

		@Override
		public void updateTask() {
			--this.attackTime;
			EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
			double d0 = this.blaze.getDistanceSqToEntity(entitylivingbase);

			if (d0 < 4.0D) {
				if (this.attackTime <= 0) {
					this.attackTime = 20;
					this.blaze.attackEntityAsMob(entitylivingbase);
				}

				this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			} else if (d0 < 256.0D) {
				double d1 = entitylivingbase.posX - this.blaze.posX;
				double d2 = (entitylivingbase.getEntityBoundingBox().minY + entitylivingbase.height / 2.0F) - (this.blaze.posY + this.blaze.height / 2.0F);
				double d3 = entitylivingbase.posZ - this.blaze.posZ;

				if (this.attackTime <= 0) {
					++this.attackStep;

					if (this.attackStep == 1) {
						this.attackTime = 60;
						this.blaze.setOnFire(true);
					} else if (this.attackStep <= 4) {
						this.attackTime = 6;
					} else {
						this.attackTime = 100;
						this.attackStep = 0;
						this.blaze.setOnFire(false);
					}

					if (this.attackStep > 1) {
						float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d0)) * 0.5F;
						this.blaze.worldObj.playEvent((EntityPlayer) null, 1018, new BlockPos((int) this.blaze.posX, (int) this.blaze.posY, (int) this.blaze.posZ), 0);

						for (int i = 0; i < 1; ++i) {
							EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.blaze.worldObj, this.blaze, d1 + (this.blaze.getRNG().nextGaussian() * f), d2,
									d3 + (this.blaze.getRNG().nextGaussian() * f));
							entitysmallfireball.posY = this.blaze.posY + this.blaze.height / 2.0F + 0.5D;
							this.blaze.worldObj.spawnEntityInWorld(entitysmallfireball);
						}
					}
				}

				this.blaze.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
			} else {
				this.blaze.getNavigator().clearPathEntity();
				this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
			}

			super.updateTask();
		}
	}
}
