package net.minecraft.entity.passive;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityOcelot extends EntityTameable {
	private static final DataParameter<Integer> OCELOT_VARIANT = EntityDataManager.<Integer> createKey(EntityOcelot.class, DataSerializers.VARINT);
	private EntityAIAvoidEntity<EntityPlayer> avoidEntity;

	/**
	 * The tempt AI task for this mob, used to prevent taming while it is fleeing.
	 */
	private EntityAITempt aiTempt;

	public EntityOcelot(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.7F);
	}

	@Override
	protected void initEntityAI() {
		this.aiSit = new EntityAISit(this);
		this.aiTempt = new EntityAITempt(this, 0.6D, Items.FISH, true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, this.aiTempt);
		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
		this.tasks.addTask(6, new EntityAIOcelotSit(this, 0.8D));
		this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
		this.tasks.addTask(8, new EntityAIOcelotAttack(this));
		this.tasks.addTask(9, new EntityAIMate(this, 0.8D));
		this.tasks.addTask(10, new EntityAIWander(this, 0.8D));
		this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
		this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, false, (Predicate) null));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(OCELOT_VARIANT, Integer.valueOf(0));
	}

	@Override
	public void updateAITasks() {
		if (this.getMoveHelper().isUpdating()) {
			double d0 = this.getMoveHelper().getSpeed();

			if (d0 == 0.6D) {
				this.setSneaking(true);
				this.setSprinting(false);
			} else if (d0 == 1.33D) {
				this.setSneaking(false);
				this.setSprinting(true);
			} else {
				this.setSneaking(false);
				this.setSprinting(false);
			}
		} else {
			this.setSneaking(false);
			this.setSprinting(false);
		}
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return !this.isTamed() && (this.ticksExisted > 2400);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	public static void func_189787_b(DataFixer p_189787_0_) {
		EntityLiving.func_189752_a(p_189787_0_, "Ozelot");
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("CatType", this.getTameSkin());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setTameSkin(compound.getInteger("CatType"));
	}

	@Override
	@Nullable
	protected SoundEvent getAmbientSound() {
		return this.isTamed() ? (this.isInLove() ? SoundEvents.ENTITY_CAT_PURR : (this.rand.nextInt(4) == 0 ? SoundEvents.ENTITY_CAT_PURREOW : SoundEvents.ENTITY_CAT_AMBIENT)) : null;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return SoundEvents.ENTITY_CAT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_CAT_DEATH;
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			if (this.aiSit != null) {
				this.aiSit.setSitting(false);
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_OCELOT;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
		if (this.isTamed()) {
			if (this.isOwner(player) && !this.worldObj.isRemote && !this.isBreedingItem(stack)) {
				this.aiSit.setSitting(!this.isSitting());
			}
		} else if (((this.aiTempt == null) || this.aiTempt.isRunning()) && (stack != null) && (stack.getItem() == Items.FISH) && (player.getDistanceSqToEntity(this) < 9.0D)) {
			if (!player.capabilities.isCreativeMode) {
				--stack.stackSize;
			}

			if (!this.worldObj.isRemote) {
				if (this.rand.nextInt(3) == 0) {
					this.setTamed(true);
					this.setTameSkin(1 + this.worldObj.rand.nextInt(3));
					this.setOwnerId(player.getUniqueID());
					this.playTameEffect(true);
					this.aiSit.setSitting(true);
					this.worldObj.setEntityState(this, (byte) 7);
				} else {
					this.playTameEffect(false);
					this.worldObj.setEntityState(this, (byte) 6);
				}
			}

			return true;
		}

		return super.processInteract(player, hand, stack);
	}

	@Override
	public EntityOcelot createChild(EntityAgeable ageable) {
		EntityOcelot entityocelot = new EntityOcelot(this.worldObj);

		if (this.isTamed()) {
			entityocelot.setOwnerId(this.getOwnerId());
			entityocelot.setTamed(true);
			entityocelot.setTameSkin(this.getTameSkin());
		}

		return entityocelot;
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on the animal type)
	 */
	@Override
	public boolean isBreedingItem(@Nullable ItemStack stack) {
		return (stack != null) && (stack.getItem() == Items.FISH);
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		if (otherAnimal == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(otherAnimal instanceof EntityOcelot)) {
			return false;
		} else {
			EntityOcelot entityocelot = (EntityOcelot) otherAnimal;
			return !entityocelot.isTamed() ? false : this.isInLove() && entityocelot.isInLove();
		}
	}

	public int getTameSkin() {
		return this.dataManager.get(OCELOT_VARIANT).intValue();
	}

	public void setTameSkin(int skinId) {
		this.dataManager.set(OCELOT_VARIANT, Integer.valueOf(skinId));
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return this.worldObj.rand.nextInt(3) != 0;
	}

	/**
	 * Checks that the entity is not colliding with any blocks / liquids
	 */
	@Override
	public boolean isNotColliding() {
		if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()
				&& !this.worldObj.containsAnyLiquid(this.getEntityBoundingBox())) {
			BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

			if (blockpos.getY() < this.worldObj.getSeaLevel()) { return false; }

			IBlockState iblockstate = this.worldObj.getBlockState(blockpos.down());
			Block block = iblockstate.getBlock();

			if ((block == Blocks.GRASS) || (iblockstate.getMaterial() == Material.LEAVES)) { return true; }
		}

		return false;
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.hasCustomName() ? this.getCustomNameTag() : (this.isTamed() ? I18n.translateToLocal("entity.Cat.name") : super.getName());
	}

	@Override
	public void setTamed(boolean tamed) {
		super.setTamed(tamed);
	}

	@Override
	protected void setupTamedAI() {
		if (this.avoidEntity == null) {
			this.avoidEntity = new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D);
		}

		this.tasks.removeTask(this.avoidEntity);

		if (!this.isTamed()) {
			this.tasks.addTask(4, this.avoidEntity);
		}
	}

	@Override
	@Nullable

	/**
	 * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	 */
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);

		if ((this.getTameSkin() == 0) && (this.worldObj.rand.nextInt(7) == 0)) {
			for (int i = 0; i < 2; ++i) {
				EntityOcelot entityocelot = new EntityOcelot(this.worldObj);
				entityocelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entityocelot.setGrowingAge(-24000);
				this.worldObj.spawnEntityInWorld(entityocelot);
			}
		}

		return livingdata;
	}
}
