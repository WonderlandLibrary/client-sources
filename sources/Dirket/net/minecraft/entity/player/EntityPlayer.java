package net.minecraft.entity.player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.scoreboard.IScoreCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.module.internal.combat.HitBoxes;
import us.dev.direkt.module.internal.movement.NoSlow;
import us.dev.direkt.module.internal.movement.Sprint;
import us.dev.direkt.module.internal.player.Freecam;

@SuppressWarnings("incomplete-switch")
public abstract class EntityPlayer extends EntityLivingBase {
	private static final DataParameter<Float> ABSORPTION = EntityDataManager.<Float> createKey(EntityPlayer.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> PLAYER_SCORE = EntityDataManager.<Integer> createKey(EntityPlayer.class, DataSerializers.VARINT);
	protected static final DataParameter<Byte> PLAYER_MODEL_FLAG = EntityDataManager.<Byte> createKey(EntityPlayer.class, DataSerializers.BYTE);
	protected static final DataParameter<Byte> MAIN_HAND = EntityDataManager.<Byte> createKey(EntityPlayer.class, DataSerializers.BYTE);

	/** Inventory of the player */
	public InventoryPlayer inventory = new InventoryPlayer(this);
	private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();

	/**
	 * The Container for the player's inventory (which opens when they press E)
	 */
	public Container inventoryContainer;

	/** The Container the player has open. */
	public Container openContainer;

	/** The food object of the player, the general hunger logic. */
	protected FoodStats foodStats = new FoodStats();

	/**
	 * Used to tell if the player pressed jump twice. If this is at 0 and it's pressed (And they are allowed to fly, as defined in the player's movementInput) it sets this to 7. If it's pressed and it's greater than 0 enable fly.
	 */
	protected int flyToggleTimer;
	public float prevCameraYaw;
	public float cameraYaw;

	/**
	 * Used by EntityPlayer to prevent too many xp orbs from getting absorbed at once.
	 */
	public int xpCooldown;
	public double prevChasingPosX;
	public double prevChasingPosY;
	public double prevChasingPosZ;
	public double chasingPosX;
	public double chasingPosY;
	public double chasingPosZ;

	/** Boolean value indicating weather a player is sleeping or not */
	protected boolean sleeping;

	/** the current location of the player */
	public BlockPos playerLocation;
	private int sleepTimer;
	public float renderOffsetX;
	public float renderOffsetY;
	public float renderOffsetZ;

	/** holds the spawn chunk of the player */
	private BlockPos spawnChunk;

	/**
	 * Whether this player's spawn point is forced, preventing execution of bed checks.
	 */
	private boolean spawnForced;

	/** Holds the coordinate of the player when enter a minecraft to ride. */
	private BlockPos startMinecartRidingCoordinate;

	/** The player's capabilities. (See class PlayerCapabilities) */
	public PlayerCapabilities capabilities = new PlayerCapabilities();

	/** The current experience level the player is on. */
	public int experienceLevel;

	/**
	 * The total amount of experience the player has. This also includes the amount of experience within their Experience Bar.
	 */
	public int experienceTotal;

	/**
	 * The current amount of experience the player has within their Experience Bar.
	 */
	public float experience;
	private int xpSeed;
	protected float speedOnGround = 0.1F;
	protected float speedInAir = 0.02F;
	private int lastXPSound;

	/** The player's unique game profile */
	private final GameProfile gameProfile;
	private boolean hasReducedDebug;
	private ItemStack itemStackMainHand;
	private final CooldownTracker cooldownTracker = this.createCooldownTracker();

	/**
	 * An instance of a fishing rod's hook. If this isn't null, the icon image of the fishing rod is slightly different
	 */
	public EntityFishHook fishEntity;

	protected CooldownTracker createCooldownTracker() {
		return new CooldownTracker();
	}

	public EntityPlayer(World worldIn, GameProfile gameProfileIn) {
		super(worldIn);
		this.setUniqueId(getUUID(gameProfileIn));
		this.gameProfile = gameProfileIn;
		this.inventoryContainer = new ContainerPlayer(this.inventory, !worldIn.isRemote, this);
		this.openContainer = this.inventoryContainer;
		BlockPos blockpos = worldIn.getSpawnPoint();
		this.setLocationAndAngles(blockpos.getX() + 0.5D, blockpos.getY() + 1, blockpos.getZ() + 0.5D, 0.0F, 0.0F);
		this.unused180 = 180.0F;
		this.fireResistance = 20;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10000000149011612D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.LUCK);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ABSORPTION, Float.valueOf(0.0F));
		this.dataManager.register(PLAYER_SCORE, Integer.valueOf(0));
		this.dataManager.register(PLAYER_MODEL_FLAG, Byte.valueOf((byte) 0));
		this.dataManager.register(MAIN_HAND, Byte.valueOf((byte) 1));
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
    	//TODO: Direkt: Freecam
    	if(!Direkt.getInstance().getModuleManager().getModule(Freecam.class).isRunning())
        this.noClip = this.isSpectator();

		if (this.isSpectator()) {
			this.onGround = false;
		}

		if (this.xpCooldown > 0) {
			--this.xpCooldown;
		}

		if (this.isPlayerSleeping()) {
			++this.sleepTimer;

			if (this.sleepTimer > 100) {
				this.sleepTimer = 100;
			}

			if (!this.worldObj.isRemote) {
				if (!this.isInBed()) {
					this.wakeUpPlayer(true, true, false);
				} else if (this.worldObj.isDaytime()) {
					this.wakeUpPlayer(false, true, true);
				}
			}
		} else if (this.sleepTimer > 0) {
			++this.sleepTimer;

			if (this.sleepTimer >= 110) {
				this.sleepTimer = 0;
			}
		}

		super.onUpdate();

		if (!this.worldObj.isRemote && (this.openContainer != null) && !this.openContainer.canInteractWith(this)) {
			this.closeScreen();
			this.openContainer = this.inventoryContainer;
		}

		if (this.isBurning() && this.capabilities.disableDamage) {
			this.extinguish();
		}

		this.updateCape();

		if (!this.isRiding()) {
			this.startMinecartRidingCoordinate = null;
		}

		if (!this.worldObj.isRemote) {
			this.foodStats.onUpdate(this);
			this.addStat(StatList.PLAY_ONE_MINUTE);

			if (this.isEntityAlive()) {
				this.addStat(StatList.TIME_SINCE_DEATH);
			}

			if (this.isSneaking()) {
				this.addStat(StatList.SNEAK_TIME);
			}
		}

		int i = 29999999;
		double d0 = MathHelper.clamp_double(this.posX, -2.9999999E7D, 2.9999999E7D);
		double d1 = MathHelper.clamp_double(this.posZ, -2.9999999E7D, 2.9999999E7D);

		if ((d0 != this.posX) || (d1 != this.posZ)) {
			this.setPosition(d0, this.posY, d1);
		}

		++this.ticksSinceLastSwing;
		ItemStack itemstack = this.getHeldItemMainhand();

		if (!ItemStack.areItemStacksEqual(this.itemStackMainHand, itemstack)) {
			if (!ItemStack.areItemsEqualIgnoreDurability(this.itemStackMainHand, itemstack)) {
				this.resetCooldown();
			}

			this.itemStackMainHand = itemstack == null ? null : itemstack.copy();
		}

		this.cooldownTracker.tick();
		this.updateSize();
	}

	private void updateCape() {
		this.prevChasingPosX = this.chasingPosX;
		this.prevChasingPosY = this.chasingPosY;
		this.prevChasingPosZ = this.chasingPosZ;
		double d0 = this.posX - this.chasingPosX;
		double d1 = this.posY - this.chasingPosY;
		double d2 = this.posZ - this.chasingPosZ;
		double d3 = 10.0D;

		if (d0 > 10.0D) {
			this.chasingPosX = this.posX;
			this.prevChasingPosX = this.chasingPosX;
		}

		if (d2 > 10.0D) {
			this.chasingPosZ = this.posZ;
			this.prevChasingPosZ = this.chasingPosZ;
		}

		if (d1 > 10.0D) {
			this.chasingPosY = this.posY;
			this.prevChasingPosY = this.chasingPosY;
		}

		if (d0 < -10.0D) {
			this.chasingPosX = this.posX;
			this.prevChasingPosX = this.chasingPosX;
		}

		if (d2 < -10.0D) {
			this.chasingPosZ = this.posZ;
			this.prevChasingPosZ = this.chasingPosZ;
		}

		if (d1 < -10.0D) {
			this.chasingPosY = this.posY;
			this.prevChasingPosY = this.chasingPosY;
		}

		this.chasingPosX += d0 * 0.25D;
		this.chasingPosZ += d2 * 0.25D;
		this.chasingPosY += d1 * 0.25D;
	}

	protected void updateSize() {
		float f;
		float f1;

		if (this.isElytraFlying()) {
			f = 0.6F;
			f1 = 0.6F;
		} else if (this.isPlayerSleeping()) {
			f = 0.2F;
			f1 = 0.2F;
		} else if (this.isSneaking()) {
			f = 0.6F;
			f1 = 1.65F;
		} else {
			f = 0.6F;
			f1 = 1.8F;
		}
//      TODO: Direkt: Hitboxes
        if (Direkt.getInstance().getModuleManager().getModule(HitBoxes.class).isRunning() && this != Wrapper.getPlayer() && !this.entityUniqueID.equals(Wrapper.getPlayer().entityUniqueID)) {
        	f = 2.2F;
        	f1 = 2;
        }
        
		if ((f != this.width) || (f1 != this.height)) {
			AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
			axisalignedbb = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + f, axisalignedbb.minY + f1, axisalignedbb.minZ + f);

			if (!this.worldObj.collidesWithAnyBlock(axisalignedbb)) {
				this.setSize(f, f1);
			}
		}
	}

	/**
	 * Return the amount of time this entity should stay in a portal before being transported.
	 */
	@Override
	public int getMaxInPortalTime() {
		return this.capabilities.disableDamage ? 1 : 80;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return SoundEvents.ENTITY_PLAYER_SWIM;
	}

	@Override
	protected SoundEvent getSplashSound() {
		return SoundEvents.ENTITY_PLAYER_SPLASH;
	}

	/**
	 * Return the amount of cooldown before this entity can use a portal again.
	 */
	@Override
	public int getPortalCooldown() {
		return 10;
	}

	@Override
	public void playSound(SoundEvent soundIn, float volume, float pitch) {
		this.worldObj.playSound(this, this.posX, this.posY, this.posZ, soundIn, this.getSoundCategory(), volume, pitch);
	}

	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.PLAYERS;
	}

	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 9) {
			this.onItemUseFinish();
		} else if (id == 23) {
			this.hasReducedDebug = false;
		} else if (id == 22) {
			this.hasReducedDebug = true;
		} else {
			super.handleStatusUpdate(id);
		}
	}

	/**
	 * Dead and sleeping entities cannot move
	 */
	@Override
	protected boolean isMovementBlocked() {
		return (this.getHealth() <= 0.0F) || this.isPlayerSleeping();
	}

	/**
	 * set current crafting inventory back to the 2x2 square
	 */
	protected void closeScreen() {
		this.openContainer = this.inventoryContainer;
	}

	/**
	 * Handles updating while being ridden by an entity
	 */
	@Override
	public void updateRidden() {
		if (!this.worldObj.isRemote && this.isSneaking() && this.isRiding()) {
			this.dismountRidingEntity();
			this.setSneaking(false);
		} else {
			double d0 = this.posX;
			double d1 = this.posY;
			double d2 = this.posZ;
			float f = this.rotationYaw;
			float f1 = this.rotationPitch;
			super.updateRidden();
			this.prevCameraYaw = this.cameraYaw;
			this.cameraYaw = 0.0F;
			this.addMountedMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);

			if (this.getRidingEntity() instanceof EntityPig) {
				this.rotationPitch = f1;
				this.rotationYaw = f;
				this.renderYawOffset = ((EntityPig) this.getRidingEntity()).renderYawOffset;
			}
		}
	}

	/**
	 * Keeps moving the entity up so it isn't colliding with blocks and other requirements for this entity to be spawned (only actually used on players though its also on Entity)
	 */
	@Override
	public void preparePlayerToSpawn() {
		this.setSize(0.6F, 1.8F);
		super.preparePlayerToSpawn();
		this.setHealth(this.getMaxHealth());
		this.deathTime = 0;
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();
		this.updateArmSwingProgress();
		this.rotationYawHead = this.rotationYaw;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons use this to react to sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (this.flyToggleTimer > 0) {
			--this.flyToggleTimer;
		}

		if ((this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) && this.worldObj.getGameRules().getBoolean("naturalRegeneration")) {
			if ((this.getHealth() < this.getMaxHealth()) && ((this.ticksExisted % 20) == 0)) {
				this.heal(1.0F);
			}

			if (this.foodStats.needFood() && ((this.ticksExisted % 10) == 0)) {
				this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
			}
		}

		this.inventory.decrementAnimations();
		this.prevCameraYaw = this.cameraYaw;
		super.onLivingUpdate();
		IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (!this.worldObj.isRemote) {
			iattributeinstance.setBaseValue(this.capabilities.getWalkSpeed());
		}

		this.jumpMovementFactor = this.speedInAir;

		if (this.isSprinting()) {
			this.jumpMovementFactor = (float) (this.jumpMovementFactor + (this.speedInAir * 0.3D));
		}

		this.setAIMoveSpeed((float) iattributeinstance.getAttributeValue());
		float f = MathHelper.sqrt_double((this.motionX * this.motionX) + (this.motionZ * this.motionZ));
		float f1 = (float) (Math.atan(-this.motionY * 0.20000000298023224D) * 15.0D);

		if (f > 0.1F) {
			f = 0.1F;
		}

		if (!this.onGround || (this.getHealth() <= 0.0F)) {
			f = 0.0F;
		}

		if (this.onGround || (this.getHealth() <= 0.0F)) {
			f1 = 0.0F;
		}

		this.cameraYaw += (f - this.cameraYaw) * 0.4F;
		this.cameraPitch += (f1 - this.cameraPitch) * 0.8F;

		if ((this.getHealth() > 0.0F) && !this.isSpectator()) {
			AxisAlignedBB axisalignedbb;

			if (this.isRiding() && !this.getRidingEntity().isDead) {
				axisalignedbb = this.getEntityBoundingBox().union(this.getRidingEntity().getEntityBoundingBox()).expand(1.0D, 0.0D, 1.0D);
			} else {
				axisalignedbb = this.getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D);
			}

			List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, axisalignedbb);

			for (int i = 0; i < list.size(); ++i) {
				Entity entity = list.get(i);

				if (!entity.isDead) {
					this.collideWithPlayer(entity);
				}
			}
		}
	}

	private void collideWithPlayer(Entity entityIn) {
		entityIn.onCollideWithPlayer(this);
	}

	public int getScore() {
		return this.dataManager.get(PLAYER_SCORE).intValue();
	}

	/**
	 * Set player's score
	 */
	public void setScore(int scoreIn) {
		this.dataManager.set(PLAYER_SCORE, Integer.valueOf(scoreIn));
	}

	/**
	 * Add to player's score
	 */
	public void addScore(int scoreIn) {
		int i = this.getScore();
		this.dataManager.set(PLAYER_SCORE, Integer.valueOf(i + scoreIn));
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);
		this.setSize(0.2F, 0.2F);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.motionY = 0.10000000149011612D;

		if ("Notch".equals(this.getName())) {
			this.dropItem(new ItemStack(Items.APPLE, 1), true, false);
		}

		if (!this.worldObj.getGameRules().getBoolean("keepInventory") && !this.isSpectator()) {
			this.inventory.dropAllItems();
		}

		if (cause != null) {
			this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 0.017453292F) * 0.1F;
			this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 0.017453292F) * 0.1F;
		} else {
			this.motionX = 0.0D;
			this.motionZ = 0.0D;
		}

		this.addStat(StatList.DEATHS);
		this.takeStat(StatList.TIME_SINCE_DEATH);
	}

	@Override
	protected SoundEvent getHurtSound() {
		return SoundEvents.ENTITY_PLAYER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_PLAYER_DEATH;
	}

	/**
	 * Adds to the players score.
	 */
	@Override
	public void addToPlayerScore(Entity entityIn, int amount) {
		if (entityIn != this) {
			this.addScore(amount);
			Collection<ScoreObjective> collection = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.TOTAL_KILL_COUNT);

			if (entityIn instanceof EntityPlayer) {
				this.addStat(StatList.PLAYER_KILLS);
				collection.addAll(this.getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.PLAYER_KILL_COUNT));
			} else {
				this.addStat(StatList.MOB_KILLS);
			}

			collection.addAll(this.giveTeamKillScores(entityIn));

			for (ScoreObjective scoreobjective : collection) {
				this.getWorldScoreboard().getOrCreateScore(this.getName(), scoreobjective).incrementScore();
			}
		}
	}

	private Collection<ScoreObjective> giveTeamKillScores(Entity p_175137_1_) {
		String s = p_175137_1_ instanceof EntityPlayer ? p_175137_1_.getName() : p_175137_1_.func_189512_bd();
		ScorePlayerTeam scoreplayerteam = this.getWorldScoreboard().getPlayersTeam(this.getName());

		if (scoreplayerteam != null) {
			int i = scoreplayerteam.getChatFormat().getColorIndex();

			if ((i >= 0) && (i < IScoreCriteria.KILLED_BY_TEAM.length)) {
				for (ScoreObjective scoreobjective : this.getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.KILLED_BY_TEAM[i])) {
					Score score = this.getWorldScoreboard().getOrCreateScore(s, scoreobjective);
					score.incrementScore();
				}
			}
		}

		ScorePlayerTeam scoreplayerteam1 = this.getWorldScoreboard().getPlayersTeam(s);

		if (scoreplayerteam1 != null) {
			int j = scoreplayerteam1.getChatFormat().getColorIndex();

			if ((j >= 0) && (j < IScoreCriteria.TEAM_KILL.length)) { return this.getWorldScoreboard().getObjectivesFromCriteria(IScoreCriteria.TEAM_KILL[j]); }
		}

		return Lists.<ScoreObjective> newArrayList();
	}

	@Nullable

	/**
	 * Drop one item out of the currently selected stack if {@code dropAll} is false. If {@code dropItem} is true the entire stack is dropped.
	 */
	public EntityItem dropItem(boolean dropAll) {
		return this.dropItem(this.inventory.decrStackSize(this.inventory.currentItem, dropAll && (this.inventory.getCurrentItem() != null) ? this.inventory.getCurrentItem().stackSize : 1), false,
				true);
	}

	@Nullable

	/**
	 * Drops an item into the world.
	 */
	public EntityItem dropItem(@Nullable ItemStack itemStackIn, boolean unused) {
		return this.dropItem(itemStackIn, false, false);
	}

	@Nullable
	public EntityItem dropItem(@Nullable ItemStack droppedItem, boolean dropAround, boolean traceItem) {
		if (droppedItem == null) {
			return null;
		} else if (droppedItem.stackSize == 0) {
			return null;
		} else {
			double d0 = (this.posY - 0.30000001192092896D) + this.getEyeHeight();
			EntityItem entityitem = new EntityItem(this.worldObj, this.posX, d0, this.posZ, droppedItem);
			entityitem.setPickupDelay(40);

			if (traceItem) {
				entityitem.setThrower(this.getName());
			}

			if (dropAround) {
				float f = this.rand.nextFloat() * 0.5F;
				float f1 = this.rand.nextFloat() * ((float) Math.PI * 2F);
				entityitem.motionX = -MathHelper.sin(f1) * f;
				entityitem.motionZ = MathHelper.cos(f1) * f;
				entityitem.motionY = 0.20000000298023224D;
			} else {
				float f2 = 0.3F;
				entityitem.motionX = -MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2;
				entityitem.motionZ = MathHelper.cos(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2;
				entityitem.motionY = (-MathHelper.sin(this.rotationPitch * 0.017453292F) * f2) + 0.1F;
				float f3 = this.rand.nextFloat() * ((float) Math.PI * 2F);
				f2 = 0.02F * this.rand.nextFloat();
				entityitem.motionX += Math.cos(f3) * f2;
				entityitem.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
				entityitem.motionZ += Math.sin(f3) * f2;
			}

			ItemStack itemstack = this.dropItemAndGetStack(entityitem);

			if (traceItem) {
				if (itemstack != null) {
					this.addStat(StatList.getDroppedObjectStats(itemstack.getItem()), droppedItem.stackSize);
				}

				this.addStat(StatList.DROP);
			}

			return entityitem;
		}
	}

	@Nullable
	protected ItemStack dropItemAndGetStack(EntityItem p_184816_1_) {
		this.worldObj.spawnEntityInWorld(p_184816_1_);
		return p_184816_1_.getEntityItem();
	}

	public float getDigSpeed(IBlockState state) {
		float f = this.inventory.getStrVsBlock(state);

		if (f > 1.0F) {
			int i = EnchantmentHelper.getEfficiencyModifier(this);
			ItemStack itemstack = this.getHeldItemMainhand();

			if ((i > 0) && (itemstack != null)) {
				f += (i * i) + 1;
			}
		}

		if (this.isPotionActive(MobEffects.HASTE)) {
			f *= 1.0F + ((this.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2F);
		}

		if (this.isPotionActive(MobEffects.MINING_FATIGUE)) {
			float f1;

			switch (this.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) {
			case 0:
				f1 = 0.3F;
				break;

			case 1:
				f1 = 0.09F;
				break;

			case 2:
				f1 = 0.0027F;
				break;

			case 3:
			default:
				f1 = 8.1E-4F;
			}

			f *= f1;
		}

		if (this.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
			f /= 5.0F;
		}

		if (!this.onGround) {
			f /= 5.0F;
		}

		return f;
	}

	public boolean canHarvestBlock(IBlockState state) {
		return this.inventory.canHarvestBlock(state);
	}

	public static void func_189806_a(DataFixer p_189806_0_) {
		p_189806_0_.registerWalker(FixTypes.PLAYER, new IDataWalker() {
			@Override
			public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
				DataFixesManager.processInventory(fixer, compound, versionIn, "Inventory");
				DataFixesManager.processInventory(fixer, compound, versionIn, "EnderItems");
				return compound;
			}
		});
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setUniqueId(getUUID(this.gameProfile));
		NBTTagList nbttaglist = compound.getTagList("Inventory", 10);
		this.inventory.readFromNBT(nbttaglist);
		this.inventory.currentItem = compound.getInteger("SelectedItemSlot");
		this.sleeping = compound.getBoolean("Sleeping");
		this.sleepTimer = compound.getShort("SleepTimer");
		this.experience = compound.getFloat("XpP");
		this.experienceLevel = compound.getInteger("XpLevel");
		this.experienceTotal = compound.getInteger("XpTotal");
		this.xpSeed = compound.getInteger("XpSeed");

		if (this.xpSeed == 0) {
			this.xpSeed = this.rand.nextInt();
		}

		this.setScore(compound.getInteger("Score"));

		if (this.sleeping) {
			this.playerLocation = new BlockPos(this);
			this.wakeUpPlayer(true, true, false);
		}

		if (compound.hasKey("SpawnX", 99) && compound.hasKey("SpawnY", 99) && compound.hasKey("SpawnZ", 99)) {
			this.spawnChunk = new BlockPos(compound.getInteger("SpawnX"), compound.getInteger("SpawnY"), compound.getInteger("SpawnZ"));
			this.spawnForced = compound.getBoolean("SpawnForced");
		}

		this.foodStats.readNBT(compound);
		this.capabilities.readCapabilitiesFromNBT(compound);

		if (compound.hasKey("EnderItems", 9)) {
			NBTTagList nbttaglist1 = compound.getTagList("EnderItems", 10);
			this.theInventoryEnderChest.loadInventoryFromNBT(nbttaglist1);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("DataVersion", 512);
		compound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
		compound.setInteger("SelectedItemSlot", this.inventory.currentItem);
		compound.setBoolean("Sleeping", this.sleeping);
		compound.setShort("SleepTimer", (short) this.sleepTimer);
		compound.setFloat("XpP", this.experience);
		compound.setInteger("XpLevel", this.experienceLevel);
		compound.setInteger("XpTotal", this.experienceTotal);
		compound.setInteger("XpSeed", this.xpSeed);
		compound.setInteger("Score", this.getScore());

		if (this.spawnChunk != null) {
			compound.setInteger("SpawnX", this.spawnChunk.getX());
			compound.setInteger("SpawnY", this.spawnChunk.getY());
			compound.setInteger("SpawnZ", this.spawnChunk.getZ());
			compound.setBoolean("SpawnForced", this.spawnForced);
		}

		this.foodStats.writeNBT(compound);
		this.capabilities.writeCapabilitiesToNBT(compound);
		compound.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else if (this.capabilities.disableDamage && !source.canHarmInCreative()) {
			return false;
		} else {
			this.entityAge = 0;

			if (this.getHealth() <= 0.0F) {
				return false;
			} else {
				if (this.isPlayerSleeping() && !this.worldObj.isRemote) {
					this.wakeUpPlayer(true, true, false);
				}

				if (source.isDifficultyScaled()) {
					if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
						amount = 0.0F;
					}

					if (this.worldObj.getDifficulty() == EnumDifficulty.EASY) {
						amount = (amount / 2.0F) + 1.0F;
					}

					if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
						amount = (amount * 3.0F) / 2.0F;
					}
				}

				return amount == 0.0F ? false : super.attackEntityFrom(source, amount);
			}
		}
	}

	public boolean canAttackPlayer(EntityPlayer other) {
		Team team = this.getTeam();
		Team team1 = other.getTeam();
		return team == null ? true : (!team.isSameTeam(team1) ? true : team.getAllowFriendlyFire());
	}

	@Override
	protected void damageArmor(float damage) {
		this.inventory.damageArmor(damage);
	}

	@Override
	protected void damageShield(float damage) {
		if ((damage >= 3.0F) && (this.activeItemStack != null) && (this.activeItemStack.getItem() == Items.SHIELD)) {
			int i = 1 + MathHelper.floor_float(damage);
			this.activeItemStack.damageItem(i, this);

			if (this.activeItemStack.stackSize <= 0) {
				EnumHand enumhand = this.getActiveHand();

				if (enumhand == EnumHand.MAIN_HAND) {
					this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, (ItemStack) null);
				} else {
					this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, (ItemStack) null);
				}

				this.activeItemStack = null;
				this.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + (this.worldObj.rand.nextFloat() * 0.4F));
			}
		}
	}

	/**
	 * When searching for vulnerable players, if a player is invisible, the return value of this is the chance of seeing them anyway.
	 */
	public float getArmorVisibility() {
		int i = 0;

		for (ItemStack itemstack : this.inventory.armorInventory) {
			if (itemstack != null) {
				++i;
			}
		}

		return (float) i / (float) this.inventory.armorInventory.length;
	}

	/**
	 * Deals damage to the entity. This will take the armor of the entity into consideration before damaging the health bar.
	 */
	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
		if (!this.isEntityInvulnerable(damageSrc)) {
			damageAmount = this.applyArmorCalculations(damageSrc, damageAmount);
			damageAmount = this.applyPotionDamageCalculations(damageSrc, damageAmount);
			float f = damageAmount;
			damageAmount = Math.max(damageAmount - this.getAbsorptionAmount(), 0.0F);
			this.setAbsorptionAmount(this.getAbsorptionAmount() - (f - damageAmount));

			if (damageAmount != 0.0F) {
				this.addExhaustion(damageSrc.getHungerDamage());
				float f1 = this.getHealth();
				this.setHealth(this.getHealth() - damageAmount);
				this.getCombatTracker().trackDamage(damageSrc, f1, damageAmount);

				if (damageAmount < 3.4028235E37F) {
					this.addStat(StatList.DAMAGE_TAKEN, Math.round(damageAmount * 10.0F));
				}
			}
		}
	}

	public void openEditSign(TileEntitySign signTile) {
	}

	public void displayGuiEditCommandCart(CommandBlockBaseLogic p_184809_1_) {
	}

	public void displayGuiCommandBlock(TileEntityCommandBlock p_184824_1_) {
	}

	public void func_189807_a(TileEntityStructure p_189807_1_) {
	}

	public void displayVillagerTradeGui(IMerchant villager) {
	}

	/**
	 * Displays the GUI for interacting with a chest inventory.
	 */
	public void displayGUIChest(IInventory chestInventory) {
	}

	public void openGuiHorseInventory(EntityHorse horse, IInventory inventoryIn) {
	}

	public void displayGui(IInteractionObject guiOwner) {
	}

	public void openBook(ItemStack stack, EnumHand hand) {
	}

	public EnumActionResult interact(Entity entityIn, @Nullable ItemStack stack, EnumHand hand) {
		if (this.isSpectator()) {
			if (entityIn instanceof IInventory) {
				this.displayGUIChest((IInventory) entityIn);
			}

			return EnumActionResult.PASS;
		} else {
			ItemStack itemstack = stack != null ? stack.copy() : null;

			if (!entityIn.processInitialInteract(this, stack, hand)) {
				if ((stack != null) && (entityIn instanceof EntityLivingBase)) {
					if (this.capabilities.isCreativeMode) {
						stack = itemstack;
					}

					if (stack.interactWithEntity(this, (EntityLivingBase) entityIn, hand)) {
						if ((stack.stackSize <= 0) && !this.capabilities.isCreativeMode) {
							this.setHeldItem(hand, (ItemStack) null);
						}

						return EnumActionResult.SUCCESS;
					}
				}

				return EnumActionResult.PASS;
			} else {
				if ((stack != null) && (stack == this.getHeldItem(hand))) {
					if ((stack.stackSize <= 0) && !this.capabilities.isCreativeMode) {
						this.setHeldItem(hand, (ItemStack) null);
					} else if ((stack.stackSize < itemstack.stackSize) && this.capabilities.isCreativeMode) {
						stack.stackSize = itemstack.stackSize;
					}
				}

				return EnumActionResult.SUCCESS;
			}
		}
	}

	/**
	 * Returns the Y Offset of this entity.
	 */
	@Override
	public double getYOffset() {
		return -0.35D;
	}

	@Override
	public void dismountRidingEntity() {
		super.dismountRidingEntity();
		this.rideCooldown = 0;
	}

	/**
	 * Attacks for the player the targeted entity with the currently equipped item. The equipped item has hitEntity called on it. Args: targetEntity
	 */
	public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
		if (targetEntity.canBeAttackedWithItem()) {
			if (!targetEntity.hitByEntity(this)) {
				float f = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
				float f1;

				if (targetEntity instanceof EntityLivingBase) {
					f1 = EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) targetEntity).getCreatureAttribute());
				} else {
					f1 = EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), EnumCreatureAttribute.UNDEFINED);
				}

				float f2 = this.getCooledAttackStrength(0.5F);
				f = f * (0.2F + (f2 * f2 * 0.8F));
				f1 = f1 * f2;
				this.resetCooldown();

				if ((f > 0.0F) || (f1 > 0.0F)) {
					boolean flag = f2 > 0.9F;
					boolean flag1 = false;
					int i = 0;
					i = i + EnchantmentHelper.getKnockbackModifier(this);

					if (this.isSprinting() && flag) {
						this.worldObj.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, this.getSoundCategory(), 1.0F, 1.0F);
						++i;
						flag1 = true;
					}

					boolean flag2 = flag && (this.fallDistance > 0.0F) && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(MobEffects.BLINDNESS) && !this.isRiding()
							&& (targetEntity instanceof EntityLivingBase);
					flag2 = flag2 && !this.isSprinting();

					if (flag2) {
						f *= 1.5F;
					}

					f = f + f1;
					boolean flag3 = false;
					double d0 = this.distanceWalkedModified - this.prevDistanceWalkedModified;

					if (flag && !flag2 && !flag1 && this.onGround && (d0 < this.getAIMoveSpeed())) {
						ItemStack itemstack = this.getHeldItem(EnumHand.MAIN_HAND);

						if ((itemstack != null) && (itemstack.getItem() instanceof ItemSword)) {
							flag3 = true;
						}
					}

					float f4 = 0.0F;
					boolean flag4 = false;
					int j = EnchantmentHelper.getFireAspectModifier(this);

					if (targetEntity instanceof EntityLivingBase) {
						f4 = ((EntityLivingBase) targetEntity).getHealth();

						if ((j > 0) && !targetEntity.isBurning()) {
							flag4 = true;
							targetEntity.setFire(1);
						}
					}

					double d1 = targetEntity.motionX;
					double d2 = targetEntity.motionY;
					double d3 = targetEntity.motionZ;
					boolean flag5 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(this), f);

					if (flag5) {
						if (i > 0) {
							if (targetEntity instanceof EntityLivingBase) {
								((EntityLivingBase) targetEntity).knockBack(this, i * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
							} else {
								targetEntity.addVelocity(-MathHelper.sin(this.rotationYaw * 0.017453292F) * i * 0.5F, 0.1D, MathHelper.cos(this.rotationYaw * 0.017453292F) * i * 0.5F);
							}

//                          TODO: Direkt: Sprint & NoSlow
                          if (!Direkt.getInstance().getModuleManager().getModule(NoSlow.class).isRunning() && !Direkt.getInstance().getModuleManager().getModule(Sprint.class).isRunning()) {
                              this.motionX *= 0.6D;
                              this.motionZ *= 0.6D;
                              this.setSprinting(false);
                          }
						}

						if (flag3) {
							for (EntityLivingBase entitylivingbase : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, targetEntity.getEntityBoundingBox().expand(1.0D, 0.25D, 1.0D))) {
								if ((entitylivingbase != this) && (entitylivingbase != targetEntity) && !this.isOnSameTeam(entitylivingbase) && (this.getDistanceSqToEntity(entitylivingbase) < 9.0D)) {
									entitylivingbase.knockBack(this, 0.4F, MathHelper.sin(this.rotationYaw * 0.017453292F), (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
									entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(this), 1.0F);
								}
							}

							this.worldObj.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
							this.spawnSweepParticles();
						}

						if ((targetEntity instanceof EntityPlayerMP) && targetEntity.velocityChanged) {
							((EntityPlayerMP) targetEntity).connection.sendPacket(new SPacketEntityVelocity(targetEntity));
							targetEntity.velocityChanged = false;
							targetEntity.motionX = d1;
							targetEntity.motionY = d2;
							targetEntity.motionZ = d3;
						}

						if (flag2) {
							this.worldObj.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundCategory(), 1.0F, 1.0F);
							this.onCriticalHit(targetEntity);
						}

						if (!flag2 && !flag3) {
							if (flag) {
								this.worldObj.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, this.getSoundCategory(), 1.0F, 1.0F);
							} else {
								this.worldObj.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, this.getSoundCategory(), 1.0F, 1.0F);
							}
						}

						if (f1 > 0.0F) {
							this.onEnchantmentCritical(targetEntity);
						}

						if (!this.worldObj.isRemote && (targetEntity instanceof EntityPlayer)) {
							EntityPlayer entityplayer = (EntityPlayer) targetEntity;
							ItemStack itemstack2 = this.getHeldItemMainhand();
							ItemStack itemstack3 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : null;

							if ((itemstack2 != null) && (itemstack3 != null) && (itemstack2.getItem() instanceof ItemAxe) && (itemstack3.getItem() == Items.SHIELD)) {
								float f3 = 0.25F + (EnchantmentHelper.getEfficiencyModifier(this) * 0.05F);

								if (flag1) {
									f3 += 0.75F;
								}

								if (this.rand.nextFloat() < f3) {
									entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
									this.worldObj.setEntityState(entityplayer, (byte) 30);
								}
							}
						}

						if (f >= 18.0F) {
							this.addStat(AchievementList.OVERKILL);
						}

						this.setLastAttacker(targetEntity);

						if (targetEntity instanceof EntityLivingBase) {
							EnchantmentHelper.applyThornEnchantments((EntityLivingBase) targetEntity, this);
						}

						EnchantmentHelper.applyArthropodEnchantments(this, targetEntity);
						ItemStack itemstack1 = this.getHeldItemMainhand();
						Entity entity = targetEntity;

						if (targetEntity instanceof EntityDragonPart) {
							IEntityMultiPart ientitymultipart = ((EntityDragonPart) targetEntity).entityDragonObj;

							if (ientitymultipart instanceof EntityLivingBase) {
								entity = (EntityLivingBase) ientitymultipart;
							}
						}

						if ((itemstack1 != null) && (entity instanceof EntityLivingBase)) {
							itemstack1.hitEntity((EntityLivingBase) entity, this);

							if (itemstack1.stackSize <= 0) {
								this.setHeldItem(EnumHand.MAIN_HAND, (ItemStack) null);
							}
						}

						if (targetEntity instanceof EntityLivingBase) {
							float f5 = f4 - ((EntityLivingBase) targetEntity).getHealth();
							this.addStat(StatList.DAMAGE_DEALT, Math.round(f5 * 10.0F));

							if (j > 0) {
								targetEntity.setFire(j * 4);
							}

							if ((this.worldObj instanceof WorldServer) && (f5 > 2.0F)) {
								int k = (int) (f5 * 0.5D);
								((WorldServer) this.worldObj).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, targetEntity.posX, targetEntity.posY + targetEntity.height * 0.5F, targetEntity.posZ, k,
										0.1D, 0.0D, 0.1D, 0.2D, new int[0]);
							}
						}

						this.addExhaustion(0.3F);
					} else {
						this.worldObj.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, this.getSoundCategory(), 1.0F, 1.0F);

						if (flag4) {
							targetEntity.extinguish();
						}
					}
				}
			}
		}
	}

	/**
	 * Called when the entity is dealt a critical hit.
	 */
	public void onCriticalHit(Entity entityHit) {
	}

	public void onEnchantmentCritical(Entity entityHit) {
	}

	public void spawnSweepParticles() {
		double d0 = (-MathHelper.sin(this.rotationYaw * 0.017453292F));
		double d1 = MathHelper.cos(this.rotationYaw * 0.017453292F);

		if (this.worldObj instanceof WorldServer) {
			((WorldServer) this.worldObj).spawnParticle(EnumParticleTypes.SWEEP_ATTACK, this.posX + d0, this.posY + (this.height * 0.5D), this.posZ + d1, 0, d0, 0.0D, d1, 0.0D, new int[0]);
		}
	}

	public void respawnPlayer() {
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		super.setDead();
		this.inventoryContainer.onContainerClosed(this);

		if (this.openContainer != null) {
			this.openContainer.onContainerClosed(this);
		}
	}

	/**
	 * Checks if this entity is inside of an opaque block
	 */
	@Override
	public boolean isEntityInsideOpaqueBlock() {
		return !this.sleeping && super.isEntityInsideOpaqueBlock();
	}

	/**
	 * returns true if this is an EntityPlayerSP, or the logged in player.
	 */
	public boolean isUser() {
		return false;
	}

	/**
	 * Returns the GameProfile for this player
	 */
	public GameProfile getGameProfile() {
		return this.gameProfile;
	}

	public EntityPlayer.SleepResult trySleep(BlockPos bedLocation) {
		if (!this.worldObj.isRemote) {
			if (this.isPlayerSleeping() || !this.isEntityAlive()) { return EntityPlayer.SleepResult.OTHER_PROBLEM; }

			if (!this.worldObj.provider.isSurfaceWorld()) { return EntityPlayer.SleepResult.NOT_POSSIBLE_HERE; }

			if (this.worldObj.isDaytime()) { return EntityPlayer.SleepResult.NOT_POSSIBLE_NOW; }

			if ((Math.abs(this.posX - bedLocation.getX()) > 3.0D) || (Math.abs(this.posY - bedLocation.getY()) > 2.0D)
					|| (Math.abs(this.posZ - bedLocation.getZ()) > 3.0D)) { return EntityPlayer.SleepResult.TOO_FAR_AWAY; }

			double d0 = 8.0D;
			double d1 = 5.0D;
			List<EntityMob> list = this.worldObj.<EntityMob> getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(bedLocation.getX() - 8.0D, bedLocation.getY() - 5.0D, bedLocation.getZ() - 8.0D,
					bedLocation.getX() + 8.0D, bedLocation.getY() + 5.0D, bedLocation.getZ() + 8.0D));

			if (!list.isEmpty()) { return EntityPlayer.SleepResult.NOT_SAFE; }
		}

		if (this.isRiding()) {
			this.dismountRidingEntity();
		}

		this.setSize(0.2F, 0.2F);

		if (this.worldObj.isBlockLoaded(bedLocation)) {
			EnumFacing enumfacing = this.worldObj.getBlockState(bedLocation).getValue(BlockHorizontal.FACING);
			float f = 0.5F;
			float f1 = 0.5F;

			switch (enumfacing) {
			case SOUTH:
				f1 = 0.9F;
				break;

			case NORTH:
				f1 = 0.1F;
				break;

			case WEST:
				f = 0.1F;
				break;

			case EAST:
				f = 0.9F;
			}

			this.setRenderOffsetForSleep(enumfacing);
			this.setPosition(bedLocation.getX() + f, bedLocation.getY() + 0.6875F, bedLocation.getZ() + f1);
		} else {
			this.setPosition(bedLocation.getX() + 0.5F, bedLocation.getY() + 0.6875F, bedLocation.getZ() + 0.5F);
		}

		this.sleeping = true;
		this.sleepTimer = 0;
		this.playerLocation = bedLocation;
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;

		if (!this.worldObj.isRemote) {
			this.worldObj.updateAllPlayersSleepingFlag();
		}

		return EntityPlayer.SleepResult.OK;
	}

	private void setRenderOffsetForSleep(EnumFacing p_175139_1_) {
		this.renderOffsetX = 0.0F;
		this.renderOffsetZ = 0.0F;

		switch (p_175139_1_) {
		case SOUTH:
			this.renderOffsetZ = -1.8F;
			break;

		case NORTH:
			this.renderOffsetZ = 1.8F;
			break;

		case WEST:
			this.renderOffsetX = 1.8F;
			break;

		case EAST:
			this.renderOffsetX = -1.8F;
		}
	}

	/**
	 * Wake up the player if they're sleeping.
	 */
	public void wakeUpPlayer(boolean immediately, boolean updateWorldFlag, boolean setSpawn) {
		this.setSize(0.6F, 1.8F);
		IBlockState iblockstate = this.worldObj.getBlockState(this.playerLocation);

		if ((this.playerLocation != null) && (iblockstate.getBlock() == Blocks.BED)) {
			this.worldObj.setBlockState(this.playerLocation, iblockstate.withProperty(BlockBed.OCCUPIED, Boolean.valueOf(false)), 4);
			BlockPos blockpos = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, 0);

			if (blockpos == null) {
				blockpos = this.playerLocation.up();
			}

			this.setPosition(blockpos.getX() + 0.5F, blockpos.getY() + 0.1F, blockpos.getZ() + 0.5F);
		}

		this.sleeping = false;

		if (!this.worldObj.isRemote && updateWorldFlag) {
			this.worldObj.updateAllPlayersSleepingFlag();
		}

		this.sleepTimer = immediately ? 0 : 100;

		if (setSpawn) {
			this.setSpawnPoint(this.playerLocation, false);
		}
	}

	private boolean isInBed() {
		return this.worldObj.getBlockState(this.playerLocation).getBlock() == Blocks.BED;
	}

	@Nullable

	/**
	 * Return null if bed is invalid
	 */
	public static BlockPos getBedSpawnLocation(World worldIn, BlockPos bedLocation, boolean forceSpawn) {
		Block block = worldIn.getBlockState(bedLocation).getBlock();

		if (block != Blocks.BED) {
			if (!forceSpawn) {
				return null;
			} else {
				boolean flag = block.canSpawnInBlock();
				boolean flag1 = worldIn.getBlockState(bedLocation.up()).getBlock().canSpawnInBlock();
				return flag && flag1 ? bedLocation : null;
			}
		} else {
			return BlockBed.getSafeExitLocation(worldIn, bedLocation, 0);
		}
	}

	/**
	 * Returns the orientation of the bed in degrees.
	 */
	public float getBedOrientationInDegrees() {
		if (this.playerLocation != null) {
			EnumFacing enumfacing = this.worldObj.getBlockState(this.playerLocation).getValue(BlockHorizontal.FACING);

			switch (enumfacing) {
			case SOUTH:
				return 90.0F;

			case NORTH:
				return 270.0F;

			case WEST:
				return 0.0F;

			case EAST:
				return 180.0F;
			}
		}

		return 0.0F;
	}

	/**
	 * Returns whether player is sleeping or not
	 */
	@Override
	public boolean isPlayerSleeping() {
		return this.sleeping;
	}

	/**
	 * Returns whether or not the player is asleep and the screen has fully faded.
	 */
	public boolean isPlayerFullyAsleep() {
		return this.sleeping && (this.sleepTimer >= 100);
	}

	public int getSleepTimer() {
		return this.sleepTimer;
	}

	public void addChatComponentMessage(ITextComponent chatComponent) {
	}

	public BlockPos getBedLocation() {
		return this.spawnChunk;
	}

	public boolean isSpawnForced() {
		return this.spawnForced;
	}

	public void setSpawnPoint(BlockPos pos, boolean forced) {
		if (pos != null) {
			this.spawnChunk = pos;
			this.spawnForced = forced;
		} else {
			this.spawnChunk = null;
			this.spawnForced = false;
		}
	}

	public boolean hasAchievement(Achievement achievementIn) {
		return false;
	}

	/**
	 * Add a stat once
	 */
	public void addStat(StatBase stat) {
		this.addStat(stat, 1);
	}

	/**
	 * Adds a value to a statistic field.
	 */
	public void addStat(StatBase stat, int amount) {
	}

	public void takeStat(StatBase stat) {
	}

	/**
	 * Causes this entity to do an upwards motion (jumping).
	 */
	@Override
	public void jump() {
		super.jump();
		this.addStat(StatList.JUMP);

		if (this.isSprinting()) {
			this.addExhaustion(0.8F);
		} else {
			this.addExhaustion(0.2F);
		}
	}

	/**
	 * Moves the entity based on the specified heading.
	 */
	@Override
	public void moveEntityWithHeading(float strafe, float forward) {
		double d0 = this.posX;
		double d1 = this.posY;
		double d2 = this.posZ;

		if (this.capabilities.isFlying && !this.isRiding()) {
			double d3 = this.motionY;
			float f = this.jumpMovementFactor;
			this.jumpMovementFactor = this.capabilities.getFlySpeed() * (this.isSprinting() ? 2 : 1);
			super.moveEntityWithHeading(strafe, forward);
			this.motionY = d3 * 0.6D;
			this.jumpMovementFactor = f;
			this.fallDistance = 0.0F;
			this.setFlag(7, false);
		} else {
			super.moveEntityWithHeading(strafe, forward);
		}

		this.addMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
	}

	/**
	 * the movespeed used for the new AI system
	 */
	@Override
	public float getAIMoveSpeed() {
		return (float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
	}

	/**
	 * Adds a value to a movement statistic field - like run, walk, swin or climb.
	 */
	public void addMovementStat(double p_71000_1_, double p_71000_3_, double p_71000_5_) {
		if (!this.isRiding()) {
			if (this.isInsideOfMaterial(Material.WATER)) {
				int i = Math.round(MathHelper.sqrt_double((p_71000_1_ * p_71000_1_) + (p_71000_3_ * p_71000_3_) + (p_71000_5_ * p_71000_5_)) * 100.0F);

				if (i > 0) {
					this.addStat(StatList.DIVE_ONE_CM, i);
					this.addExhaustion(0.015F * i * 0.01F);
				}
			} else if (this.isInWater()) {
				int j = Math.round(MathHelper.sqrt_double((p_71000_1_ * p_71000_1_) + (p_71000_5_ * p_71000_5_)) * 100.0F);

				if (j > 0) {
					this.addStat(StatList.SWIM_ONE_CM, j);
					this.addExhaustion(0.015F * j * 0.01F);
				}
			} else if (this.isOnLadder()) {
				if (p_71000_3_ > 0.0D) {
					this.addStat(StatList.CLIMB_ONE_CM, (int) Math.round(p_71000_3_ * 100.0D));
				}
			} else if (this.onGround) {
				int k = Math.round(MathHelper.sqrt_double((p_71000_1_ * p_71000_1_) + (p_71000_5_ * p_71000_5_)) * 100.0F);

				if (k > 0) {
					if (this.isSprinting()) {
						this.addStat(StatList.SPRINT_ONE_CM, k);
						this.addExhaustion(0.099999994F * k * 0.01F);
					} else if (this.isSneaking()) {
						this.addStat(StatList.CROUCH_ONE_CM, k);
						this.addExhaustion(0.005F * k * 0.01F);
					} else {
						this.addStat(StatList.WALK_ONE_CM, k);
						this.addExhaustion(0.01F * k * 0.01F);
					}
				}
			} else if (this.isElytraFlying()) {
				int l = Math.round(MathHelper.sqrt_double((p_71000_1_ * p_71000_1_) + (p_71000_3_ * p_71000_3_) + (p_71000_5_ * p_71000_5_)) * 100.0F);
				this.addStat(StatList.AVIATE_ONE_CM, l);
			} else {
				int i1 = Math.round(MathHelper.sqrt_double((p_71000_1_ * p_71000_1_) + (p_71000_5_ * p_71000_5_)) * 100.0F);

				if (i1 > 25) {
					this.addStat(StatList.FLY_ONE_CM, i1);
				}
			}
		}
	}

	/**
	 * Adds a value to a mounted movement statistic field - by minecart, boat, or pig.
	 */
	private void addMountedMovementStat(double p_71015_1_, double p_71015_3_, double p_71015_5_) {
		if (this.isRiding()) {
			int i = Math.round(MathHelper.sqrt_double((p_71015_1_ * p_71015_1_) + (p_71015_3_ * p_71015_3_) + (p_71015_5_ * p_71015_5_)) * 100.0F);

			if (i > 0) {
				if (this.getRidingEntity() instanceof EntityMinecart) {
					this.addStat(StatList.MINECART_ONE_CM, i);

					if (this.startMinecartRidingCoordinate == null) {
						this.startMinecartRidingCoordinate = new BlockPos(this);
					} else if (this.startMinecartRidingCoordinate.distanceSq(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY),
							MathHelper.floor_double(this.posZ)) >= 1000000.0D) {
						this.addStat(AchievementList.ON_A_RAIL);
					}
				} else if (this.getRidingEntity() instanceof EntityBoat) {
					this.addStat(StatList.BOAT_ONE_CM, i);
				} else if (this.getRidingEntity() instanceof EntityPig) {
					this.addStat(StatList.PIG_ONE_CM, i);
				} else if (this.getRidingEntity() instanceof EntityHorse) {
					this.addStat(StatList.HORSE_ONE_CM, i);
				}
			}
		}
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		if (!this.capabilities.allowFlying) {
			if (distance >= 2.0F) {
				this.addStat(StatList.FALL_ONE_CM, (int) Math.round(distance * 100.0D));
			}

			super.fall(distance, damageMultiplier);
		}
	}

	/**
	 * sets the players height back to normal after doing things like sleeping and dieing
	 */
	@Override
	protected void resetHeight() {
		if (!this.isSpectator()) {
			super.resetHeight();
		}
	}

	@Override
	protected SoundEvent getFallSound(int heightIn) {
		return heightIn > 4 ? SoundEvents.ENTITY_PLAYER_BIG_FALL : SoundEvents.ENTITY_PLAYER_SMALL_FALL;
	}

	/**
	 * This method gets called when the entity kills another one.
	 */
	@Override
	public void onKillEntity(EntityLivingBase entityLivingIn) {
		if (entityLivingIn instanceof IMob) {
			this.addStat(AchievementList.KILL_ENEMY);
		}

		EntityList.EntityEggInfo entitylist$entityegginfo = EntityList.ENTITY_EGGS.get(EntityList.getEntityString(entityLivingIn));

		if (entitylist$entityegginfo != null) {
			this.addStat(entitylist$entityegginfo.killEntityStat);
		}
	}

	/**
	 * Sets the Entity inside a web block.
	 */
	@Override
	public void setInWeb() {
		if (!this.capabilities.isFlying) {
			super.setInWeb();
		}
	}

	/**
	 * Add experience points to player.
	 */
	public void addExperience(int amount) {
		this.addScore(amount);
		int i = Integer.MAX_VALUE - this.experienceTotal;

		if (amount > i) {
			amount = i;
		}

		this.experience += (float) amount / (float) this.xpBarCap();

		for (this.experienceTotal += amount; this.experience >= 1.0F; this.experience /= this.xpBarCap()) {
			this.experience = (this.experience - 1.0F) * this.xpBarCap();
			this.addExperienceLevel(1);
		}
	}

	public int getXPSeed() {
		return this.xpSeed;
	}

	public void removeExperienceLevel(int levels) {
		this.experienceLevel -= levels;

		if (this.experienceLevel < 0) {
			this.experienceLevel = 0;
			this.experience = 0.0F;
			this.experienceTotal = 0;
		}

		this.xpSeed = this.rand.nextInt();
	}

	/**
	 * Add experience levels to this player.
	 */
	public void addExperienceLevel(int levels) {
		this.experienceLevel += levels;

		if (this.experienceLevel < 0) {
			this.experienceLevel = 0;
			this.experience = 0.0F;
			this.experienceTotal = 0;
		}

		if ((levels > 0) && ((this.experienceLevel % 5) == 0) && (this.lastXPSound < (this.ticksExisted - 100.0F))) {
			float f = this.experienceLevel > 30 ? 1.0F : this.experienceLevel / 30.0F;
			this.worldObj.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, this.getSoundCategory(), f * 0.75F, 1.0F);
			this.lastXPSound = this.ticksExisted;
		}
	}

	/**
	 * This method returns the cap amount of experience that the experience bar can hold. With each level, the experience cap on the player's experience bar is raised by 10.
	 */
	public int xpBarCap() {
		return this.experienceLevel >= 30 ? 112 + ((this.experienceLevel - 30) * 9) : (this.experienceLevel >= 15 ? 37 + ((this.experienceLevel - 15) * 5) : 7 + (this.experienceLevel * 2));
	}

	/**
	 * increases exhaustion level by supplied amount
	 */
	public void addExhaustion(float exhaustion) {
		if (!this.capabilities.disableDamage) {
			if (!this.worldObj.isRemote) {
				this.foodStats.addExhaustion(exhaustion);
			}
		}
	}

	/**
	 * Returns the player's FoodStats object.
	 */
	public FoodStats getFoodStats() {
		return this.foodStats;
	}

	public boolean canEat(boolean ignoreHunger) {
		return (ignoreHunger || this.foodStats.needFood()) && !this.capabilities.disableDamage;
	}

	/**
	 * Checks if the player's health is not full and not zero.
	 */
	public boolean shouldHeal() {
		return (this.getHealth() > 0.0F) && (this.getHealth() < this.getMaxHealth());
	}

	public boolean isAllowEdit() {
		return this.capabilities.allowEdit;
	}

	public boolean canPlayerEdit(BlockPos pos, EnumFacing facing, @Nullable ItemStack stack) {
		if (this.capabilities.allowEdit) {
			return true;
		} else if (stack == null) {
			return false;
		} else {
			BlockPos blockpos = pos.offset(facing.getOpposite());
			Block block = this.worldObj.getBlockState(blockpos).getBlock();
			return stack.canPlaceOn(block) || stack.canEditBlocks();
		}
	}

	/**
	 * Get the experience points the entity currently has.
	 */
	@Override
	protected int getExperiencePoints(EntityPlayer player) {
		if (!this.worldObj.getGameRules().getBoolean("keepInventory") && !this.isSpectator()) {
			int i = this.experienceLevel * 7;
			return i > 100 ? 100 : i;
		} else {
			return 0;
		}
	}

	/**
	 * Only use is to identify if class is an instance of player for experience dropping
	 */
	@Override
	protected boolean isPlayer() {
		return true;
	}

	@Override
	public boolean getAlwaysRenderNameTagForRender() {
		return true;
	}

	/**
	 * Copies the values from the given player into this player if boolean par2 is true. Always clones Ender Chest Inventory.
	 */
	public void clonePlayer(EntityPlayer oldPlayer, boolean respawnFromEnd) {
		if (respawnFromEnd) {
			this.inventory.copyInventory(oldPlayer.inventory);
			this.setHealth(oldPlayer.getHealth());
			this.foodStats = oldPlayer.foodStats;
			this.experienceLevel = oldPlayer.experienceLevel;
			this.experienceTotal = oldPlayer.experienceTotal;
			this.experience = oldPlayer.experience;
			this.setScore(oldPlayer.getScore());
			this.lastPortalPos = oldPlayer.lastPortalPos;
			this.lastPortalVec = oldPlayer.lastPortalVec;
			this.teleportDirection = oldPlayer.teleportDirection;
		} else if (this.worldObj.getGameRules().getBoolean("keepInventory") || oldPlayer.isSpectator()) {
			this.inventory.copyInventory(oldPlayer.inventory);
			this.experienceLevel = oldPlayer.experienceLevel;
			this.experienceTotal = oldPlayer.experienceTotal;
			this.experience = oldPlayer.experience;
			this.setScore(oldPlayer.getScore());
		}

		this.xpSeed = oldPlayer.xpSeed;
		this.theInventoryEnderChest = oldPlayer.theInventoryEnderChest;
		this.getDataManager().set(PLAYER_MODEL_FLAG, oldPlayer.getDataManager().get(PLAYER_MODEL_FLAG));
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return !this.capabilities.isFlying;
	}

	/**
	 * Sends the player's abilities to the server (if there is one).
	 */
	public void sendPlayerAbilities() {
	}

	/**
	 * Sets the player's game mode and sends it to them.
	 */
	public void setGameType(GameType gameType) {
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.gameProfile.getName();
	}

	/**
	 * Returns the InventoryEnderChest of this player.
	 */
	public InventoryEnderChest getInventoryEnderChest() {
		return this.theInventoryEnderChest;
	}

	@Override
	@Nullable
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		return slotIn == EntityEquipmentSlot.MAINHAND ? this.inventory.getCurrentItem()
				: (slotIn == EntityEquipmentSlot.OFFHAND ? this.inventory.offHandInventory[0]
						: (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR ? this.inventory.armorInventory[slotIn.getIndex()] : null));
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, @Nullable ItemStack stack) {
		if (slotIn == EntityEquipmentSlot.MAINHAND) {
			this.playEquipSound(stack);
			this.inventory.mainInventory[this.inventory.currentItem] = stack;
		} else if (slotIn == EntityEquipmentSlot.OFFHAND) {
			this.playEquipSound(stack);
			this.inventory.offHandInventory[0] = stack;
		} else if (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
			this.playEquipSound(stack);
			this.inventory.armorInventory[slotIn.getIndex()] = stack;
		}
	}

	@Override
	public Iterable<ItemStack> getHeldEquipment() {
		return Lists.newArrayList(new ItemStack[] { this.getHeldItemMainhand(), this.getHeldItemOffhand() });
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return Arrays.<ItemStack> asList(this.inventory.armorInventory);
	}

	/**
	 * Only used by renderer in EntityLivingBase subclasses. Determines if an entity is visible or not to a specfic player, if the entity is normally invisible. For EntityLivingBase subclasses, returning false when invisible will render the entity semitransparent.
	 */
	@Override
	public boolean isInvisibleToPlayer(EntityPlayer player) {
		if (!this.isInvisible()) {
			return false;
		} else if (player.isSpectator()) {
			return false;
		} else {
			Team team = this.getTeam();
			return (team == null) || (player == null) || (player.getTeam() != team) || !team.getSeeFriendlyInvisiblesEnabled();
		}
	}

	/**
	 * Returns true if the player is in spectator mode.
	 */
	public abstract boolean isSpectator();

	public abstract boolean isCreative();

	@Override
	public boolean isPushedByWater() {
		return !this.capabilities.isFlying;
	}

	public Scoreboard getWorldScoreboard() {
		return this.worldObj.getScoreboard();
	}

	@Override
	public Team getTeam() {
		return this.getWorldScoreboard().getPlayersTeam(this.getName());
	}

	/**
	 * Get the formatted ChatComponent that will be used for the sender's username in chat
	 */
	@Override
	public ITextComponent getDisplayName() {
		ITextComponent itextcomponent = new TextComponentString(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
		itextcomponent.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
		itextcomponent.getStyle().setHoverEvent(this.getHoverEvent());
		itextcomponent.getStyle().setInsertion(this.getName());
		return itextcomponent;
	}

	@Override
	public float getEyeHeight() {
		float f = 1.62F;

		if (this.isPlayerSleeping()) {
			f = 0.2F;
		} else if (!this.isSneaking() && (this.height != 1.65F)) {
			if (this.isElytraFlying() || (this.height == 0.6F)) {
				f = 0.4F;
			}
		} else {
			f -= 0.08F;
		}

		return f;
	}

	@Override
	public void setAbsorptionAmount(float amount) {
		if (amount < 0.0F) {
			amount = 0.0F;
		}

		this.getDataManager().set(ABSORPTION, Float.valueOf(amount));
	}

	@Override
	public float getAbsorptionAmount() {
		return this.getDataManager().get(ABSORPTION).floatValue();
	}

	/**
	 * Gets a players UUID given their GameProfie
	 */
	public static UUID getUUID(GameProfile profile) {
		UUID uuid = profile.getId();

		if (uuid == null) {
			uuid = getOfflineUUID(profile.getName());
		}

		return uuid;
	}

	public static UUID getOfflineUUID(String username) {
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(Charsets.UTF_8));
	}

	/**
	 * Check whether this player can open an inventory locked with the given LockCode.
	 */
	public boolean canOpen(LockCode code) {
		if (code.isEmpty()) {
			return true;
		} else {
			ItemStack itemstack = this.getHeldItemMainhand();
			return (itemstack != null) && itemstack.hasDisplayName() ? itemstack.getDisplayName().equals(code.getLock()) : false;
		}
	}

	public boolean isWearing(EnumPlayerModelParts part) {
		return (this.getDataManager().get(PLAYER_MODEL_FLAG).byteValue() & part.getPartMask()) == part.getPartMask();
	}

	/**
	 * Returns true if the command sender should be sent feedback about executed commands
	 */
	@Override
	public boolean sendCommandFeedback() {
		return this.getServer().worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
	}

	@Override
	public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
		if ((inventorySlot >= 0) && (inventorySlot < this.inventory.mainInventory.length)) {
			this.inventory.setInventorySlotContents(inventorySlot, itemStackIn);
			return true;
		} else {
			EntityEquipmentSlot entityequipmentslot;

			if (inventorySlot == (100 + EntityEquipmentSlot.HEAD.getIndex())) {
				entityequipmentslot = EntityEquipmentSlot.HEAD;
			} else if (inventorySlot == (100 + EntityEquipmentSlot.CHEST.getIndex())) {
				entityequipmentslot = EntityEquipmentSlot.CHEST;
			} else if (inventorySlot == (100 + EntityEquipmentSlot.LEGS.getIndex())) {
				entityequipmentslot = EntityEquipmentSlot.LEGS;
			} else if (inventorySlot == (100 + EntityEquipmentSlot.FEET.getIndex())) {
				entityequipmentslot = EntityEquipmentSlot.FEET;
			} else {
				entityequipmentslot = null;
			}

			if (inventorySlot == 98) {
				this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemStackIn);
				return true;
			} else if (inventorySlot == 99) {
				this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, itemStackIn);
				return true;
			} else if (entityequipmentslot == null) {
				int i = inventorySlot - 200;

				if ((i >= 0) && (i < this.theInventoryEnderChest.getSizeInventory())) {
					this.theInventoryEnderChest.setInventorySlotContents(i, itemStackIn);
					return true;
				} else {
					return false;
				}
			} else {
				if ((itemStackIn != null) && (itemStackIn.getItem() != null)) {
					if (!(itemStackIn.getItem() instanceof ItemArmor) && !(itemStackIn.getItem() instanceof ItemElytra)) {
						if (entityequipmentslot != EntityEquipmentSlot.HEAD) { return false; }
					} else if (EntityLiving.getSlotForItemStack(itemStackIn) != entityequipmentslot) { return false; }
				}

				this.inventory.setInventorySlotContents(entityequipmentslot.getIndex() + this.inventory.mainInventory.length, itemStackIn);
				return true;
			}
		}
	}

	/**
	 * Whether the "reducedDebugInfo" option is active for this player.
	 */
	public boolean hasReducedDebug() {
		return true;
	}

	public void setReducedDebug(boolean reducedDebug) {
		this.hasReducedDebug = true;
	}

	@Override
	public EnumHandSide getPrimaryHand() {
		return this.dataManager.get(MAIN_HAND).byteValue() == 0 ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
	}

	public void setPrimaryHand(EnumHandSide hand) {
		this.dataManager.set(MAIN_HAND, Byte.valueOf((byte) (hand == EnumHandSide.LEFT ? 0 : 1)));
	}

	public float getCooldownPeriod() {
		return (float) ((1.0D / this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue()) * 20.0D);
	}

	/**
	 * Returns the percentage of attack power available based on the cooldown (zero to one).
	 */
	public float getCooledAttackStrength(float adjustTicks) {
		return MathHelper.clamp_float((this.ticksSinceLastSwing + adjustTicks) / this.getCooldownPeriod(), 0.0F, 1.0F);
	}

	public void resetCooldown() {
		this.ticksSinceLastSwing = 0;
	}

	public CooldownTracker getCooldownTracker() {
		return this.cooldownTracker;
	}

	/**
	 * Applies a velocity to the entities, to push them away from eachother.
	 */
	@Override
	public void applyEntityCollision(Entity entityIn) {
		if (!this.isPlayerSleeping()) {
			super.applyEntityCollision(entityIn);
		}
	}

	public float getLuck() {
		return (float) this.getEntityAttribute(SharedMonsterAttributes.LUCK).getAttributeValue();
	}

	public boolean func_189808_dh() {
		return this.capabilities.isCreativeMode && this.canCommandSenderUseCommand(2, "");
	}

	public static enum EnumChatVisibility {
		FULL(0, "options.chat.visibility.full"), SYSTEM(1, "options.chat.visibility.system"), HIDDEN(2, "options.chat.visibility.hidden");

		private static final EntityPlayer.EnumChatVisibility[] ID_LOOKUP = new EntityPlayer.EnumChatVisibility[values().length];
		private final int chatVisibility;
		private final String resourceKey;

		private EnumChatVisibility(int id, String resourceKey) {
			this.chatVisibility = id;
			this.resourceKey = resourceKey;
		}

		public int getChatVisibility() {
			return this.chatVisibility;
		}

		public static EntityPlayer.EnumChatVisibility getEnumChatVisibility(int id) {
			return ID_LOOKUP[id % ID_LOOKUP.length];
		}

		public String getResourceKey() {
			return this.resourceKey;
		}

		static {
			for (EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility : values()) {
				ID_LOOKUP[entityplayer$enumchatvisibility.chatVisibility] = entityplayer$enumchatvisibility;
			}
		}
	}

	public static enum SleepResult {
		OK, NOT_POSSIBLE_HERE, NOT_POSSIBLE_NOW, TOO_FAR_AWAY, OTHER_PROBLEM, NOT_SAFE;
	}
}
