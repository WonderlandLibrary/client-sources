package net.minecraft.entity.monster;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
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
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityCreeper extends EntityMob {
	private static final DataParameter<Integer> STATE = EntityDataManager.<Integer> createKey(EntityCreeper.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> POWERED = EntityDataManager.<Boolean> createKey(EntityCreeper.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IGNITED = EntityDataManager.<Boolean> createKey(EntityCreeper.class, DataSerializers.BOOLEAN);

	/**
	 * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go weird)
	 */
	private int lastActiveTime;

	/**
	 * The amount of time since the creeper was close enough to the player to ignite
	 */
	private int timeSinceIgnited;
	private int fuseTime = 30;

	/** Explosion radius for this creeper. */
	private int explosionRadius = 3;
	private int droppedSkulls;

	public EntityCreeper(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.7F);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAICreeperSwell(this));
		this.tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	/**
	 * The maximum height from where the entity is alowed to jump (used in pathfinder)
	 */
	@Override
	public int getMaxFallHeight() {
		return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		super.fall(distance, damageMultiplier);
		this.timeSinceIgnited = (int) (this.timeSinceIgnited + (distance * 1.5F));

		if (this.timeSinceIgnited > (this.fuseTime - 5)) {
			this.timeSinceIgnited = this.fuseTime - 5;
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(STATE, Integer.valueOf(-1));
		this.dataManager.register(POWERED, Boolean.valueOf(false));
		this.dataManager.register(IGNITED, Boolean.valueOf(false));
	}

	public static void func_189762_b(DataFixer p_189762_0_) {
		EntityLiving.func_189752_a(p_189762_0_, "Creeper");
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		if (this.dataManager.get(POWERED).booleanValue()) {
			compound.setBoolean("powered", true);
		}

		compound.setShort("Fuse", (short) this.fuseTime);
		compound.setByte("ExplosionRadius", (byte) this.explosionRadius);
		compound.setBoolean("ignited", this.hasIgnited());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.dataManager.set(POWERED, Boolean.valueOf(compound.getBoolean("powered")));

		if (compound.hasKey("Fuse", 99)) {
			this.fuseTime = compound.getShort("Fuse");
		}

		if (compound.hasKey("ExplosionRadius", 99)) {
			this.explosionRadius = compound.getByte("ExplosionRadius");
		}

		if (compound.getBoolean("ignited")) {
			this.ignite();
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (this.isEntityAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;

			if (this.hasIgnited()) {
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();

			if ((i > 0) && (this.timeSinceIgnited == 0)) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.timeSinceIgnited += i;

			if (this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if (this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				this.explode();
			}
		}

		super.onUpdate();
	}

	@Override
	protected SoundEvent getHurtSound() {
		return SoundEvents.ENTITY_CREEPER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_CREEPER_DEATH;
	}

	/**
	 * Called when the mob's health reaches 0.
	 */
	@Override
	public void onDeath(DamageSource cause) {
		super.onDeath(cause);

		if (this.worldObj.getGameRules().getBoolean("doMobLoot")) {
			if (cause.getEntity() instanceof EntitySkeleton) {
				int i = Item.getIdFromItem(Items.RECORD_13);
				int j = Item.getIdFromItem(Items.RECORD_WAIT);
				int k = i + this.rand.nextInt((j - i) + 1);
				this.dropItem(Item.getItemById(k), 1);
			} else if ((cause.getEntity() instanceof EntityCreeper) && (cause.getEntity() != this) && ((EntityCreeper) cause.getEntity()).getPowered()
					&& ((EntityCreeper) cause.getEntity()).isAIEnabled()) {
				((EntityCreeper) cause.getEntity()).incrementDroppedSkulls();
				this.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0F);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		return true;
	}

	/**
	 * Returns true if the creeper is powered by a lightning bolt.
	 */
	public boolean getPowered() {
		return this.dataManager.get(POWERED).booleanValue();
	}

	/**
	 * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
	 */
	public float getCreeperFlashIntensity(float p_70831_1_) {
		return (this.lastActiveTime + ((this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_)) / (this.fuseTime - 2);
	}

	@Override
	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_CREEPER;
	}

	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public int getCreeperState() {
		return this.dataManager.get(STATE).intValue();
	}

	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(int state) {
		this.dataManager.set(STATE, Integer.valueOf(state));
	}

	/**
	 * Called when a lightning bolt hits the entity.
	 */
	@Override
	public void onStruckByLightning(EntityLightningBolt lightningBolt) {
		super.onStruckByLightning(lightningBolt);
		this.dataManager.set(POWERED, Boolean.valueOf(true));
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
		if ((stack != null) && (stack.getItem() == Items.FLINT_AND_STEEL)) {
			this.worldObj.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, this.getSoundCategory(), 1.0F, (this.rand.nextFloat() * 0.4F) + 0.8F);
			player.swingArm(hand);

			if (!this.worldObj.isRemote) {
				this.ignite();
				stack.damageItem(1, player);
				return true;
			}
		}

		return super.processInteract(player, hand, stack);
	}

	/**
	 * Creates an explosion as determined by this creeper's power and explosion radius.
	 */
	private void explode() {
		if (!this.worldObj.isRemote) {
			boolean flag = this.worldObj.getGameRules().getBoolean("mobGriefing");
			float f = this.getPowered() ? 2.0F : 1.0F;
			this.dead = true;
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.explosionRadius * f, flag);
			this.setDead();
		}
	}

	public boolean hasIgnited() {
		return this.dataManager.get(IGNITED).booleanValue();
	}

	public void ignite() {
		this.dataManager.set(IGNITED, Boolean.valueOf(true));
	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	public boolean isAIEnabled() {
		return (this.droppedSkulls < 1) && this.worldObj.getGameRules().getBoolean("doMobLoot");
	}

	public void incrementDroppedSkulls() {
		++this.droppedSkulls;
	}
}
