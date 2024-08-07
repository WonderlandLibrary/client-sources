package net.minecraft.entity.monster;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityZombie extends EntityMob {
	/**
	 * The attribute which determines the chance that this mob will spawn reinforcements
	 */
	protected static final IAttribute SPAWN_REINFORCEMENTS_CHANCE = (new RangedAttribute((IAttribute) null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D))
			.setDescription("Spawn Reinforcements Chance");
	private static final UUID BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier BABY_SPEED_BOOST = new AttributeModifier(BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5D, 1);
	private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.<Boolean> createKey(EntityZombie.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> VILLAGER_TYPE = EntityDataManager.<Integer> createKey(EntityZombie.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> CONVERTING = EntityDataManager.<Boolean> createKey(EntityZombie.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.<Boolean> createKey(EntityZombie.class, DataSerializers.BOOLEAN);
	private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor(this);

	/**
	 * Ticker used to determine the time remaining for this zombie to convert into a villager when cured.
	 */
	private int conversionTime;
	private boolean isBreakDoorsTaskSet;

	/** The width of the entity */
	private float zombieWidth = -1.0F;

	/** The height of the the entity. */
	private float zombieHeight;

	public EntityZombie(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.95F);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIZombieAttack(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
		this.getAttributeMap().registerAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(this.rand.nextDouble() * 0.10000000149011612D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataManager().register(IS_CHILD, Boolean.valueOf(false));
		this.getDataManager().register(VILLAGER_TYPE, Integer.valueOf(0));
		this.getDataManager().register(CONVERTING, Boolean.valueOf(false));
		this.getDataManager().register(ARMS_RAISED, Boolean.valueOf(false));
	}

	public void setArmsRaised(boolean armsRaised) {
		this.getDataManager().set(ARMS_RAISED, Boolean.valueOf(armsRaised));
	}

	public boolean isArmsRaised() {
		return this.getDataManager().get(ARMS_RAISED).booleanValue();
	}

	public boolean isBreakDoorsTaskSet() {
		return this.isBreakDoorsTaskSet;
	}

	/**
	 * Sets or removes EntityAIBreakDoor task
	 */
	public void setBreakDoorsAItask(boolean enabled) {
		if (this.isBreakDoorsTaskSet != enabled) {
			this.isBreakDoorsTaskSet = enabled;
			((PathNavigateGround) this.getNavigator()).setBreakDoors(enabled);

			if (enabled) {
				this.tasks.addTask(1, this.breakDoor);
			} else {
				this.tasks.removeTask(this.breakDoor);
			}
		}
	}

	/**
	 * If Animal, checks if the age timer is negative
	 */
	@Override
	public boolean isChild() {
		return this.getDataManager().get(IS_CHILD).booleanValue();
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	@Override
	protected int getExperiencePoints(EntityPlayer player) {
		if (this.isChild()) {
			this.experienceValue = (int) (this.experienceValue * 2.5F);
		}

		return super.getExperiencePoints(player);
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(boolean childZombie) {
		this.getDataManager().set(IS_CHILD, Boolean.valueOf(childZombie));

		if ((this.worldObj != null) && !this.worldObj.isRemote) {
			IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			iattributeinstance.removeModifier(BABY_SPEED_BOOST);

			if (childZombie) {
				iattributeinstance.applyModifier(BABY_SPEED_BOOST);
			}
		}

		this.setChildSize(childZombie);
	}

	public ZombieType func_189777_di() {
		return ZombieType.func_190146_a(this.getDataManager().get(VILLAGER_TYPE).intValue());
	}

	/**
	 * Return whether this zombie is a villager.
	 */
	public boolean isVillager() {
		return this.func_189777_di().func_190154_b();
	}

	public int getVillagerType() {
		return this.func_189777_di().func_190148_c();
	}

	public void func_189778_a(ZombieType p_189778_1_) {
		this.getDataManager().set(VILLAGER_TYPE, Integer.valueOf(p_189778_1_.func_190150_a()));
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		if (IS_CHILD.equals(key)) {
			this.setChildSize(this.isChild());
		}

		super.notifyDataManagerChange(key);
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild() && this.func_189777_di().func_190155_e()) {
			float f = this.getBrightness(1.0F);
			BlockPos blockpos = this.getRidingEntity() instanceof EntityBoat ? (new BlockPos(this.posX, Math.round(this.posY), this.posZ)).up()
					: new BlockPos(this.posX, Math.round(this.posY), this.posZ);

			if ((f > 0.5F) && ((this.rand.nextFloat() * 30.0F) < ((f - 0.4F) * 2.0F)) && this.worldObj.canSeeSky(blockpos)) {
				boolean flag = true;
				ItemStack itemstack = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

				if (itemstack != null) {
					if (itemstack.isItemStackDamageable()) {
						itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));

						if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
							this.renderBrokenItemStack(itemstack);
							this.setItemStackToSlot(EntityEquipmentSlot.HEAD, (ItemStack) null);
						}
					}

					flag = false;
				}

				if (flag) {
					this.setFire(8);
				}
			}
		}

		super.onLivingUpdate();
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (super.attackEntityFrom(source, amount)) {
			EntityLivingBase entitylivingbase = this.getAttackTarget();

			if ((entitylivingbase == null) && (source.getEntity() instanceof EntityLivingBase)) {
				entitylivingbase = (EntityLivingBase) source.getEntity();
			}

			if ((entitylivingbase != null) && (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
					&& (this.rand.nextFloat() < this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).getAttributeValue()) && this.worldObj.getGameRules().getBoolean("doMobSpawning")) {
				int i = MathHelper.floor_double(this.posX);
				int j = MathHelper.floor_double(this.posY);
				int k = MathHelper.floor_double(this.posZ);
				EntityZombie entityzombie = new EntityZombie(this.worldObj);

				for (int l = 0; l < 50; ++l) {
					int i1 = i + (MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1));
					int j1 = j + (MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1));
					int k1 = k + (MathHelper.getRandomIntegerInRange(this.rand, 7, 40) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1));

					if (this.worldObj.getBlockState(new BlockPos(i1, j1 - 1, k1)).isFullyOpaque() && (this.worldObj.getLightFromNeighbors(new BlockPos(i1, j1, k1)) < 10)) {
						entityzombie.setPosition(i1, j1, k1);

						if (!this.worldObj.isAnyPlayerWithinRangeAt(i1, j1, k1, 7.0D) && this.worldObj.checkNoEntityCollision(entityzombie.getEntityBoundingBox(), entityzombie)
								&& this.worldObj.getCollisionBoxes(entityzombie, entityzombie.getEntityBoundingBox()).isEmpty()
								&& !this.worldObj.containsAnyLiquid(entityzombie.getEntityBoundingBox())) {
							this.worldObj.spawnEntityInWorld(entityzombie);
							entityzombie.setAttackTarget(entitylivingbase);
							entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityzombie)), (IEntityLivingData) null);
							this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
							entityzombie.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
							break;
						}
					}
				}
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (!this.worldObj.isRemote && this.isConverting()) {
			int i = this.getConversionTimeBoost();
			this.conversionTime -= i;

			if (this.conversionTime <= 0) {
				this.convertToVillager();
			}
		}

		super.onUpdate();
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = super.attackEntityAsMob(entityIn);

		if (flag) {
			float f = this.worldObj.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();

			if (this.getHeldItemMainhand() == null) {
				if (this.isBurning() && (this.rand.nextFloat() < (f * 0.3F))) {
					entityIn.setFire(2 * (int) f);
				}

				if ((this.func_189777_di() == ZombieType.HUSK) && (entityIn instanceof EntityLivingBase)) {
					((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 140 * (int) f));
				}
			}
		}

		return flag;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return this.func_189777_di().func_190153_f();
	}

	@Override
	protected SoundEvent getHurtSound() {
		return this.func_189777_di().func_190152_g();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return this.func_189777_di().func_190151_h();
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		SoundEvent soundevent = this.func_189777_di().func_190149_i();
		this.playSound(soundevent, 0.15F, 1.0F);
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_ZOMBIE;
	}

	/**
	 * Gives armor or weapon for entity based on given DifficultyInstance
	 */
	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);

		if (this.rand.nextFloat() < (this.worldObj.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
			int i = this.rand.nextInt(3);

			if (i == 0) {
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			} else {
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
			}
		}
	}

	public static void func_189779_d(DataFixer p_189779_0_) {
		EntityLiving.func_189752_a(p_189779_0_, "Zombie");
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		if (this.isChild()) {
			compound.setBoolean("IsBaby", true);
		}

		compound.setInteger("ZombieType", this.func_189777_di().func_190150_a());
		compound.setInteger("ConversionTime", this.isConverting() ? this.conversionTime : -1);
		compound.setBoolean("CanBreakDoors", this.isBreakDoorsTaskSet());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		if (compound.getBoolean("IsBaby")) {
			this.setChild(true);
		}

		if (compound.getBoolean("IsVillager")) {
			if (compound.hasKey("VillagerProfession", 99)) {
				this.func_189778_a(ZombieType.func_190146_a(compound.getInteger("VillagerProfession") + 1));
			} else {
				this.func_189778_a(ZombieType.func_190146_a(this.worldObj.rand.nextInt(5) + 1));
			}
		}

		if (compound.hasKey("ZombieType")) {
			this.func_189778_a(ZombieType.func_190146_a(compound.getInteger("ZombieType")));
		}

		if (compound.hasKey("ConversionTime", 99) && (compound.getInteger("ConversionTime") > -1)) {
			this.startConversion(compound.getInteger("ConversionTime"));
		}

		this.setBreakDoorsAItask(compound.getBoolean("CanBreakDoors"));
	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	@Override
	public void onKillEntity(EntityLivingBase entityLivingIn) {
		super.onKillEntity(entityLivingIn);

		if (((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) || (this.worldObj.getDifficulty() == EnumDifficulty.HARD)) && (entityLivingIn instanceof EntityVillager)) {
			if ((this.worldObj.getDifficulty() != EnumDifficulty.HARD) && this.rand.nextBoolean()) { return; }

			EntityVillager entityvillager = (EntityVillager) entityLivingIn;
			EntityZombie entityzombie = new EntityZombie(this.worldObj);
			entityzombie.copyLocationAndAnglesFrom(entityLivingIn);
			this.worldObj.removeEntity(entityLivingIn);
			entityzombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityzombie)), new EntityZombie.GroupData(false, true));
			entityzombie.func_189778_a(ZombieType.func_190146_a(entityvillager.getProfession() + 1));
			entityzombie.setChild(entityLivingIn.isChild());
			entityzombie.setNoAI(entityvillager.isAIDisabled());

			if (entityvillager.hasCustomName()) {
				entityzombie.setCustomNameTag(entityvillager.getCustomNameTag());
				entityzombie.setAlwaysRenderNameTag(entityvillager.getAlwaysRenderNameTag());
			}

			this.worldObj.spawnEntityInWorld(entityzombie);
			this.worldObj.playEvent((EntityPlayer) null, 1026, new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ), 0);
		}
	}

	@Override
	public float getEyeHeight() {
		float f = 1.74F;

		if (this.isChild()) {
			f = (float) (f - 0.81D);
		}

		return f;
	}

	@Override
	protected boolean canEquipItem(ItemStack stack) {
		return (stack.getItem() == Items.EGG) && this.isChild() && this.isRiding() ? false : super.canEquipItem(stack);
	}

	@Override
	@Nullable

	/**
	 * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
	 */
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		float f = difficulty.getClampedAdditionalDifficulty();
		this.setCanPickUpLoot(this.rand.nextFloat() < (0.55F * f));

		if (livingdata == null) {
			livingdata = new EntityZombie.GroupData(this.worldObj.rand.nextFloat() < 0.05F, this.worldObj.rand.nextFloat() < 0.05F);
		}

		if (livingdata instanceof EntityZombie.GroupData) {
			EntityZombie.GroupData entityzombie$groupdata = (EntityZombie.GroupData) livingdata;
			boolean flag = false;
			Biome biome = this.worldObj.getBiomeGenForCoords(new BlockPos(this));

			if ((biome instanceof BiomeDesert) && this.worldObj.canSeeSky(new BlockPos(this)) && (this.rand.nextInt(5) != 0)) {
				this.func_189778_a(ZombieType.HUSK);
				flag = true;
			}

			if (!flag && entityzombie$groupdata.isVillager) {
				this.func_189778_a(ZombieType.func_190146_a(this.rand.nextInt(5) + 1));
			}

			if (entityzombie$groupdata.isChild) {
				this.setChild(true);

				if (this.worldObj.rand.nextFloat() < 0.05D) {
					List<EntityChicken> list = this.worldObj.<EntityChicken> getEntitiesWithinAABB(EntityChicken.class, this.getEntityBoundingBox().expand(5.0D, 3.0D, 5.0D),
							EntitySelectors.IS_STANDALONE);

					if (!list.isEmpty()) {
						EntityChicken entitychicken = list.get(0);
						entitychicken.setChickenJockey(true);
						this.startRiding(entitychicken);
					}
				} else if (this.worldObj.rand.nextFloat() < 0.05D) {
					EntityChicken entitychicken1 = new EntityChicken(this.worldObj);
					entitychicken1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
					entitychicken1.onInitialSpawn(difficulty, (IEntityLivingData) null);
					entitychicken1.setChickenJockey(true);
					this.worldObj.spawnEntityInWorld(entitychicken1);
					this.startRiding(entitychicken1);
				}
			}
		}

		this.setBreakDoorsAItask(this.rand.nextFloat() < (f * 0.1F));
		this.setEquipmentBasedOnDifficulty(difficulty);
		this.setEnchantmentBasedOnDifficulty(difficulty);

		if (this.getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null) {
			Calendar calendar = this.worldObj.getCurrentDate();

			if (((calendar.get(2) + 1) == 10) && (calendar.get(5) == 31) && (this.rand.nextFloat() < 0.25F)) {
				this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
				this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
		double d0 = this.rand.nextDouble() * 1.5D * f;

		if (d0 > 1.0D) {
			this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
		}

		if (this.rand.nextFloat() < (f * 0.05F)) {
			this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Leader zombie bonus", (this.rand.nextDouble() * 0.25D) + 0.5D, 0));
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", (this.rand.nextDouble() * 3.0D) + 1.0D, 2));
			this.setBreakDoorsAItask(true);
		}

		return livingdata;
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
		if ((stack != null) && (stack.getItem() == Items.GOLDEN_APPLE) && (stack.getMetadata() == 0) && this.isVillager() && this.isPotionActive(MobEffects.WEAKNESS)) {
			if (!player.capabilities.isCreativeMode) {
				--stack.stackSize;
			}

			if (!this.worldObj.isRemote) {
				this.startConversion(this.rand.nextInt(2401) + 3600);
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Starts converting this zombie into a villager. The zombie converts into a villager after the specified time in ticks.
	 */
	protected void startConversion(int ticks) {
		this.conversionTime = ticks;
		this.getDataManager().set(CONVERTING, Boolean.valueOf(true));
		this.removePotionEffect(MobEffects.WEAKNESS);
		this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, ticks, Math.min(this.worldObj.getDifficulty().getDifficultyId() - 1, 0)));
		this.worldObj.setEntityState(this, (byte) 16);
	}

	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 16) {
			if (!this.isSilent()) {
				this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, this.getSoundCategory(), 1.0F + this.rand.nextFloat(),
						(this.rand.nextFloat() * 0.7F) + 0.3F, false);
			}
		} else {
			super.handleStatusUpdate(id);
		}
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return !this.isConverting();
	}

	/**
	 * Returns whether this zombie is in the process of converting to a villager
	 */
	public boolean isConverting() {
		return this.getDataManager().get(CONVERTING).booleanValue();
	}

	/**
	 * Convert this zombie into a villager.
	 */
	protected void convertToVillager() {
		EntityVillager entityvillager = new EntityVillager(this.worldObj);
		entityvillager.copyLocationAndAnglesFrom(this);
		entityvillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData) null);
		entityvillager.setLookingForHome();

		if (this.isChild()) {
			entityvillager.setGrowingAge(-24000);
		}

		this.worldObj.removeEntity(this);
		entityvillager.setNoAI(this.isAIDisabled());
		entityvillager.setProfession(this.getVillagerType());

		if (this.hasCustomName()) {
			entityvillager.setCustomNameTag(this.getCustomNameTag());
			entityvillager.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
		}

		this.worldObj.spawnEntityInWorld(entityvillager);
		entityvillager.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
		this.worldObj.playEvent((EntityPlayer) null, 1027, new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ), 0);
	}

	/**
	 * Return the amount of time decremented from conversionTime every tick.
	 */
	protected int getConversionTimeBoost() {
		int i = 1;

		if (this.rand.nextFloat() < 0.01F) {
			int j = 0;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (int k = (int) this.posX - 4; (k < ((int) this.posX + 4)) && (j < 14); ++k) {
				for (int l = (int) this.posY - 4; (l < ((int) this.posY + 4)) && (j < 14); ++l) {
					for (int i1 = (int) this.posZ - 4; (i1 < ((int) this.posZ + 4)) && (j < 14); ++i1) {
						Block block = this.worldObj.getBlockState(blockpos$mutableblockpos.set(k, l, i1)).getBlock();

						if ((block == Blocks.IRON_BARS) || (block == Blocks.BED)) {
							if (this.rand.nextFloat() < 0.3F) {
								++i;
							}

							++j;
						}
					}
				}
			}
		}

		return i;
	}

	/**
	 * sets the size of the entity to be half of its current size if true.
	 */
	public void setChildSize(boolean isChild) {
		this.multiplySize(isChild ? 0.5F : 1.0F);
	}

	/**
	 * Sets the width and height of the entity.
	 */
	@Override
	protected final void setSize(float width, float height) {
		boolean flag = (this.zombieWidth > 0.0F) && (this.zombieHeight > 0.0F);
		this.zombieWidth = width;
		this.zombieHeight = height;

		if (!flag) {
			this.multiplySize(1.0F);
		}
	}

	/**
	 * Multiplies the height and width by the provided float.
	 */
	protected final void multiplySize(float size) {
		super.setSize(this.zombieWidth * size, this.zombieHeight * size);
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	@Override
	public double getYOffset() {
		return this.isChild() ? 0.0D : -0.35D;
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);

		if ((cause.getEntity() instanceof EntityCreeper) && !(this instanceof EntityPigZombie) && ((EntityCreeper) cause.getEntity()).getPowered()
				&& ((EntityCreeper) cause.getEntity()).isAIEnabled()) {
			((EntityCreeper) cause.getEntity()).incrementDroppedSkulls();
			this.entityDropItem(new ItemStack(Items.SKULL, 1, 2), 0.0F);
		}
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.hasCustomName() ? this.getCustomNameTag() : this.func_189777_di().func_190145_d().getUnformattedText();
	}

	class GroupData implements IEntityLivingData {
		public boolean isChild;
		public boolean isVillager;

		private GroupData(boolean isBaby, boolean isVillagerZombie) {
			this.isChild = isBaby;
			this.isVillager = isVillagerZombie;
		}
	}
}
