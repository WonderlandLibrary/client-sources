package net.minecraft.entity.monster;

import javax.annotation.Nullable;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySlime extends EntityLiving implements IMob {
	private static final DataParameter<Integer> SLIME_SIZE = EntityDataManager.<Integer> createKey(EntitySlime.class, DataSerializers.VARINT);
	public float squishAmount;
	public float squishFactor;
	public float prevSquishFactor;
	private boolean wasOnGround;

	public EntitySlime(World worldIn) {
		super(worldIn);
		this.moveHelper = new EntitySlime.SlimeMoveHelper(this);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntitySlime.AISlimeFloat(this));
		this.tasks.addTask(2, new EntitySlime.AISlimeAttack(this));
		this.tasks.addTask(3, new EntitySlime.AISlimeFaceRandom(this));
		this.tasks.addTask(5, new EntitySlime.AISlimeHop(this));
		this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
		this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SLIME_SIZE, Integer.valueOf(1));
	}

	protected void setSlimeSize(int size) {
		this.dataManager.set(SLIME_SIZE, Integer.valueOf(size));
		this.setSize(0.51000005F * size, 0.51000005F * size);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(size * size);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2F + (0.1F * size));
		this.setHealth(this.getMaxHealth());
		this.experienceValue = size;
	}

	/**
	 * Returns the size of the slime.
	 */
	public int getSlimeSize() {
		return this.dataManager.get(SLIME_SIZE).intValue();
	}

	public static void func_189758_c(DataFixer p_189758_0_) {
		EntityLiving.func_189752_a(p_189758_0_, "Slime");
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("Size", this.getSlimeSize() - 1);
		compound.setBoolean("wasOnGround", this.wasOnGround);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		int i = compound.getInteger("Size");

		if (i < 0) {
			i = 0;
		}

		this.setSlimeSize(i + 1);
		this.wasOnGround = compound.getBoolean("wasOnGround");
	}

	public boolean isSmallSlime() {
		return this.getSlimeSize() <= 1;
	}

	protected EnumParticleTypes getParticleType() {
		return EnumParticleTypes.SLIME;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (!this.worldObj.isRemote && (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) && (this.getSlimeSize() > 0)) {
			this.isDead = true;
		}

		this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
		this.prevSquishFactor = this.squishFactor;
		super.onUpdate();

		if (this.onGround && !this.wasOnGround) {
			int i = this.getSlimeSize();

			for (int j = 0; j < (i * 8); ++j) {
				float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
				float f1 = (this.rand.nextFloat() * 0.5F) + 0.5F;
				float f2 = MathHelper.sin(f) * i * 0.5F * f1;
				float f3 = MathHelper.cos(f) * i * 0.5F * f1;
				World world = this.worldObj;
				EnumParticleTypes enumparticletypes = this.getParticleType();
				double d0 = this.posX + f2;
				double d1 = this.posZ + f3;
				world.spawnParticle(enumparticletypes, d0, this.getEntityBoundingBox().minY, d1, 0.0D, 0.0D, 0.0D, new int[0]);
			}

			this.playSound(this.getSquishSound(), this.getSoundVolume(), (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F) / 0.8F);
			this.squishAmount = -0.5F;
		} else if (!this.onGround && this.wasOnGround) {
			this.squishAmount = 1.0F;
		}

		this.wasOnGround = this.onGround;
		this.alterSquishAmount();
	}

	protected void alterSquishAmount() {
		this.squishAmount *= 0.6F;
	}

	/**
	 * Gets the amount of time the slime needs to wait between jumps.
	 */
	protected int getJumpDelay() {
		return this.rand.nextInt(20) + 10;
	}

	protected EntitySlime createInstance() {
		return new EntitySlime(this.worldObj);
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		if (SLIME_SIZE.equals(key)) {
			int i = this.getSlimeSize();
			this.setSize(0.51000005F * i, 0.51000005F * i);
			this.rotationYaw = this.rotationYawHead;
			this.renderYawOffset = this.rotationYawHead;

			if (this.isInWater() && (this.rand.nextInt(20) == 0)) {
				this.resetHeight();
			}
		}

		super.notifyDataManagerChange(key);
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		int i = this.getSlimeSize();

		if (!this.worldObj.isRemote && (i > 1) && (this.getHealth() <= 0.0F)) {
			int j = 2 + this.rand.nextInt(3);

			for (int k = 0; k < j; ++k) {
				float f = ((k % 2 - 0.5F) * i) / 4.0F;
				float f1 = ((k / 2 - 0.5F) * i) / 4.0F;
				EntitySlime entityslime = this.createInstance();

				if (this.hasCustomName()) {
					entityslime.setCustomNameTag(this.getCustomNameTag());
				}

				if (this.isNoDespawnRequired()) {
					entityslime.enablePersistence();
				}

				entityslime.setSlimeSize(i / 2);
				entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
				this.worldObj.spawnEntityInWorld(entityslime);
			}
		}

		super.setDead();
	}

	/**
	 * Applies a velocity to the entities, to push them away from eachother.
	 */
	@Override
	public void applyEntityCollision(Entity entityIn) {
		super.applyEntityCollision(entityIn);

		if ((entityIn instanceof EntityIronGolem) && this.canDamagePlayer()) {
			this.dealDamage((EntityLivingBase) entityIn);
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		if (this.canDamagePlayer()) {
			this.dealDamage(entityIn);
		}
	}

	protected void dealDamage(EntityLivingBase entityIn) {
		int i = this.getSlimeSize();

		if (this.canEntityBeSeen(entityIn) && (this.getDistanceSqToEntity(entityIn) < (0.6D * i * 0.6D * i))
				&& entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength())) {
			this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
			this.applyEnchantments(this, entityIn);
		}
	}

	@Override
	public float getEyeHeight() {
		return 0.625F * this.height;
	}

	/**
	 * Indicates weather the slime is able to damage the player (based upon the slime's size)
	 */
	protected boolean canDamagePlayer() {
		return !this.isSmallSlime();
	}

	/**
	 * Gets the amount of damage dealt to the player when "attacked" by the slime.
	 */
	protected int getAttackStrength() {
		return this.getSlimeSize();
	}

	@Override
	protected SoundEvent getHurtSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_HURT : SoundEvents.ENTITY_SLIME_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_DEATH : SoundEvents.ENTITY_SLIME_DEATH;
	}

	protected SoundEvent getSquishSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_SQUISH : SoundEvents.ENTITY_SLIME_SQUISH;
	}

	@Override
	protected Item getDropItem() {
		return this.getSlimeSize() == 1 ? Items.SLIME_BALL : null;
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return this.getSlimeSize() == 1 ? LootTableList.ENTITIES_SLIME : LootTableList.EMPTY;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		BlockPos blockpos = new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ));
		Chunk chunk = this.worldObj.getChunkFromBlockCoords(blockpos);

		if ((this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT) && (this.rand.nextInt(4) != 1)) {
			return false;
		} else {
			if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
				Biome biome = this.worldObj.getBiomeGenForCoords(blockpos);

				if ((biome == Biomes.SWAMPLAND) && (this.posY > 50.0D) && (this.posY < 70.0D) && (this.rand.nextFloat() < 0.5F) && (this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor())
						&& (this.worldObj.getLightFromNeighbors(new BlockPos(this)) <= this.rand.nextInt(8))) { return super.getCanSpawnHere(); }

				if ((this.rand.nextInt(10) == 0) && (chunk.getRandomWithSeed(987234911L).nextInt(10) == 0) && (this.posY < 40.0D)) { return super.getCanSpawnHere(); }
			}

			return false;
		}
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.4F * this.getSlimeSize();
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently use in wolves.
	 */
	@Override
	public int getVerticalFaceSpeed() {
		return 0;
	}

	/**
	 * Returns true if the slime makes a sound when it jumps (based upon the slime's size)
	 */
	protected boolean makesSoundOnJump() {
		return this.getSlimeSize() > 0;
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	@Override
	protected void jump() {
		this.motionY = 0.41999998688697815D;
		this.isAirBorne = true;
	}

	@Override
	@Nullable

	/**
	 * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	 */
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		int i = this.rand.nextInt(3);

		if ((i < 2) && (this.rand.nextFloat() < (0.5F * difficulty.getClampedAdditionalDifficulty()))) {
			++i;
		}

		int j = 1 << i;
		this.setSlimeSize(j);
		return super.onInitialSpawn(difficulty, livingdata);
	}

	protected SoundEvent getJumpSound() {
		return this.isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_JUMP : SoundEvents.ENTITY_SLIME_JUMP;
	}

	static class AISlimeAttack extends EntityAIBase {
		private final EntitySlime slime;
		private int growTieredTimer;

		public AISlimeAttack(EntitySlime slimeIn) {
			this.slime = slimeIn;
			this.setMutexBits(2);
		}

		@Override
		public boolean shouldExecute() {
			EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
			return entitylivingbase == null ? false
					: (!entitylivingbase.isEntityAlive() ? false : !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).capabilities.disableDamage);
		}

		@Override
		public void startExecuting() {
			this.growTieredTimer = 300;
			super.startExecuting();
		}

		@Override
		public boolean continueExecuting() {
			EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
			return entitylivingbase == null ? false
					: (!entitylivingbase.isEntityAlive() ? false
							: ((entitylivingbase instanceof EntityPlayer) && ((EntityPlayer) entitylivingbase).capabilities.disableDamage ? false : --this.growTieredTimer > 0));
		}

		@Override
		public void updateTask() {
			this.slime.faceEntity(this.slime.getAttackTarget(), 10.0F, 10.0F);
			((EntitySlime.SlimeMoveHelper) this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, this.slime.canDamagePlayer());
		}
	}

	static class AISlimeFaceRandom extends EntityAIBase {
		private final EntitySlime slime;
		private float chosenDegrees;
		private int nextRandomizeTime;

		public AISlimeFaceRandom(EntitySlime slimeIn) {
			this.slime = slimeIn;
			this.setMutexBits(2);
		}

		@Override
		public boolean shouldExecute() {
			return (this.slime.getAttackTarget() == null) && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(MobEffects.LEVITATION));
		}

		@Override
		public void updateTask() {
			if (--this.nextRandomizeTime <= 0) {
				this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
				this.chosenDegrees = this.slime.getRNG().nextInt(360);
			}

			((EntitySlime.SlimeMoveHelper) this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
		}
	}

	static class AISlimeFloat extends EntityAIBase {
		private final EntitySlime slime;

		public AISlimeFloat(EntitySlime slimeIn) {
			this.slime = slimeIn;
			this.setMutexBits(5);
			((PathNavigateGround) slimeIn.getNavigator()).setCanSwim(true);
		}

		@Override
		public boolean shouldExecute() {
			return this.slime.isInWater() || this.slime.isInLava();
		}

		@Override
		public void updateTask() {
			if (this.slime.getRNG().nextFloat() < 0.8F) {
				this.slime.getJumpHelper().setJumping();
			}

			((EntitySlime.SlimeMoveHelper) this.slime.getMoveHelper()).setSpeed(1.2D);
		}
	}

	static class AISlimeHop extends EntityAIBase {
		private final EntitySlime slime;

		public AISlimeHop(EntitySlime slimeIn) {
			this.slime = slimeIn;
			this.setMutexBits(5);
		}

		@Override
		public boolean shouldExecute() {
			return true;
		}

		@Override
		public void updateTask() {
			((EntitySlime.SlimeMoveHelper) this.slime.getMoveHelper()).setSpeed(1.0D);
		}
	}

	static class SlimeMoveHelper extends EntityMoveHelper {
		private float yRot;
		private int jumpDelay;
		private final EntitySlime slime;
		private boolean isAggressive;

		public SlimeMoveHelper(EntitySlime slimeIn) {
			super(slimeIn);
			this.slime = slimeIn;
			this.yRot = (180.0F * slimeIn.rotationYaw) / (float) Math.PI;
		}

		public void setDirection(float p_179920_1_, boolean p_179920_2_) {
			this.yRot = p_179920_1_;
			this.isAggressive = p_179920_2_;
		}

		public void setSpeed(double speedIn) {
			this.speed = speedIn;
			this.action = EntityMoveHelper.Action.MOVE_TO;
		}

		@Override
		public void onUpdateMoveHelper() {
			this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
			this.entity.rotationYawHead = this.entity.rotationYaw;
			this.entity.renderYawOffset = this.entity.rotationYaw;

			if (this.action != EntityMoveHelper.Action.MOVE_TO) {
				this.entity.setMoveForward(0.0F);
			} else {
				this.action = EntityMoveHelper.Action.WAIT;

				if (this.entity.onGround) {
					this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

					if (this.jumpDelay-- <= 0) {
						this.jumpDelay = this.slime.getJumpDelay();

						if (this.isAggressive) {
							this.jumpDelay /= 3;
						}

						this.slime.getJumpHelper().setJumping();

						if (this.slime.makesSoundOnJump()) {
							this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), (((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F) + 1.0F) * 0.8F);
						}
					} else {
						this.slime.moveStrafing = 0.0F;
						this.slime.moveForward = 0.0F;
						this.entity.setAIMoveSpeed(0.0F);
					}
				} else {
					this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
				}
			}
		}
	}
}
