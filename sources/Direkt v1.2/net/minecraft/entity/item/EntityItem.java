package net.minecraft.entity.item;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Optional;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class EntityItem extends Entity {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final DataParameter<Optional<ItemStack>> ITEM = EntityDataManager.<Optional<ItemStack>> createKey(EntityItem.class, DataSerializers.OPTIONAL_ITEM_STACK);

	/**
	 * The age of this EntityItem (used to animate it up and down as well as expire it)
	 */
	private int age;
	private int delayBeforeCanPickup;

	/** The health of this EntityItem. (For example, damage for tools) */
	private int health;
	private String thrower;
	private String owner;

	/** The EntityItem's random initial float height. */
	public float hoverStart;

	public EntityItem(World worldIn, double x, double y, double z) {
		super(worldIn);
		this.health = 5;
		this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
		this.setSize(0.25F, 0.25F);
		this.setPosition(x, y, z);
		this.rotationYaw = (float) (Math.random() * 360.0D);
		this.motionX = ((float) ((Math.random() * 0.20000000298023224D) - 0.10000000149011612D));
		this.motionY = 0.20000000298023224D;
		this.motionZ = ((float) ((Math.random() * 0.20000000298023224D) - 0.10000000149011612D));
	}

	public EntityItem(World worldIn, double x, double y, double z, ItemStack stack) {
		this(worldIn, x, y, z);
		this.setEntityItemStack(stack);
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	public EntityItem(World worldIn) {
		super(worldIn);
		this.health = 5;
		this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
		this.setSize(0.25F, 0.25F);
		this.setEntityItemStack(new ItemStack(Blocks.AIR, 0));
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(ITEM, Optional.<ItemStack> absent());
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (this.getEntityItem() == null) {
			this.setDead();
		} else {
			super.onUpdate();

			if ((this.delayBeforeCanPickup > 0) && (this.delayBeforeCanPickup != 32767)) {
				--this.delayBeforeCanPickup;
			}

			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			if (!this.func_189652_ae()) {
				this.motionY -= 0.03999999910593033D;
			}

			this.noClip = this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			boolean flag = ((int) this.prevPosX != (int) this.posX) || ((int) this.prevPosY != (int) this.posY) || ((int) this.prevPosZ != (int) this.posZ);

			if (flag || ((this.ticksExisted % 25) == 0)) {
				if (this.worldObj.getBlockState(new BlockPos(this)).getMaterial() == Material.LAVA) {
					this.motionY = 0.20000000298023224D;
					this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
					this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
					this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + (this.rand.nextFloat() * 0.4F));
				}

				if (!this.worldObj.isRemote) {
					this.searchForOtherItemsNearby();
				}
			}

			float f = 0.98F;

			if (this.onGround) {
				f = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ)))
						.getBlock().slipperiness * 0.98F;
			}

			this.motionX *= f;
			this.motionY *= 0.9800000190734863D;
			this.motionZ *= f;

			if (this.onGround) {
				this.motionY *= -0.5D;
			}

			if (this.age != -32768) {
				++this.age;
			}

			this.handleWaterMovement();

			if (!this.worldObj.isRemote && (this.age >= 6000)) {
				this.setDead();
			}
		}
	}

	/**
	 * Looks for other itemstacks nearby and tries to stack them together
	 */
	private void searchForOtherItemsNearby() {
		for (EntityItem entityitem : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(0.5D, 0.0D, 0.5D))) {
			this.combineItems(entityitem);
		}
	}

	/**
	 * Tries to merge this item with the item passed as the parameter. Returns true if successful. Either this item or the other item will be removed from the world.
	 */
	private boolean combineItems(EntityItem other) {
		if (other == this) {
			return false;
		} else if (other.isEntityAlive() && this.isEntityAlive()) {
			ItemStack itemstack = this.getEntityItem();
			ItemStack itemstack1 = other.getEntityItem();

			if ((this.delayBeforeCanPickup != 32767) && (other.delayBeforeCanPickup != 32767)) {
				if ((this.age != -32768) && (other.age != -32768)) {
					if (itemstack1.getItem() != itemstack.getItem()) {
						return false;
					} else if (itemstack1.hasTagCompound() ^ itemstack.hasTagCompound()) {
						return false;
					} else if (itemstack1.hasTagCompound() && !itemstack1.getTagCompound().equals(itemstack.getTagCompound())) {
						return false;
					} else if (itemstack1.getItem() == null) {
						return false;
					} else if (itemstack1.getItem().getHasSubtypes() && (itemstack1.getMetadata() != itemstack.getMetadata())) {
						return false;
					} else if (itemstack1.stackSize < itemstack.stackSize) {
						return other.combineItems(this);
					} else if ((itemstack1.stackSize + itemstack.stackSize) > itemstack1.getMaxStackSize()) {
						return false;
					} else {
						itemstack1.stackSize += itemstack.stackSize;
						other.delayBeforeCanPickup = Math.max(other.delayBeforeCanPickup, this.delayBeforeCanPickup);
						other.age = Math.min(other.age, this.age);
						other.setEntityItemStack(itemstack1);
						this.setDead();
						return true;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * sets the age of the item so that it'll despawn one minute after it has been dropped (instead of five). Used when items are dropped from players in creative mode
	 */
	public void setAgeToCreativeDespawnTime() {
		this.age = 4800;
	}

	/**
	 * Returns if this entity is in water and will end up adding the waters velocity to the entity
	 */
	@Override
	public boolean handleWaterMovement() {
		if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this)) {
			if (!this.inWater && !this.firstUpdate) {
				this.resetHeight();
			}

			this.inWater = true;
		} else {
			this.inWater = false;
		}

		return this.inWater;
	}

	/**
	 * Will deal the specified amount of fire damage to the entity if the entity isn't immune to fire damage.
	 */
	@Override
	protected void dealFireDamage(int amount) {
		this.attackEntityFrom(DamageSource.inFire, amount);
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else if ((this.getEntityItem() != null) && (this.getEntityItem().getItem() == Items.NETHER_STAR) && source.isExplosion()) {
			return false;
		} else {
			this.setBeenAttacked();
			this.health = (int) (this.health - amount);

			if (this.health <= 0) {
				this.setDead();
			}

			return false;
		}
	}

	public static void func_189742_a(DataFixer p_189742_0_) {
		p_189742_0_.registerWalker(FixTypes.ENTITY, new ItemStackData("Item", new String[] { "Item" }));
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setShort("Health", (short) this.health);
		compound.setShort("Age", (short) this.age);
		compound.setShort("PickupDelay", (short) this.delayBeforeCanPickup);

		if (this.getThrower() != null) {
			compound.setString("Thrower", this.thrower);
		}

		if (this.getOwner() != null) {
			compound.setString("Owner", this.owner);
		}

		if (this.getEntityItem() != null) {
			compound.setTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
		}
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.health = compound.getShort("Health");
		this.age = compound.getShort("Age");

		if (compound.hasKey("PickupDelay")) {
			this.delayBeforeCanPickup = compound.getShort("PickupDelay");
		}

		if (compound.hasKey("Owner")) {
			this.owner = compound.getString("Owner");
		}

		if (compound.hasKey("Thrower")) {
			this.thrower = compound.getString("Thrower");
		}

		NBTTagCompound nbttagcompound = compound.getCompoundTag("Item");
		this.setEntityItemStack(ItemStack.loadItemStackFromNBT(nbttagcompound));

		if (this.getEntityItem() == null) {
			this.setDead();
		}
	}

	/**
	 * Called by a player entity when they collide with an entity
	 */
	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn) {
		if (!this.worldObj.isRemote) {
			ItemStack itemstack = this.getEntityItem();
			int i = itemstack.stackSize;

			if ((this.delayBeforeCanPickup == 0) && ((this.owner == null) || ((6000 - this.age) <= 200) || this.owner.equals(entityIn.getName()))
					&& entityIn.inventory.addItemStackToInventory(itemstack)) {
				if (itemstack.getItem() == Item.getItemFromBlock(Blocks.LOG)) {
					entityIn.addStat(AchievementList.MINE_WOOD);
				}

				if (itemstack.getItem() == Item.getItemFromBlock(Blocks.LOG2)) {
					entityIn.addStat(AchievementList.MINE_WOOD);
				}

				if (itemstack.getItem() == Items.LEATHER) {
					entityIn.addStat(AchievementList.KILL_COW);
				}

				if (itemstack.getItem() == Items.DIAMOND) {
					entityIn.addStat(AchievementList.DIAMONDS);
				}

				if (itemstack.getItem() == Items.BLAZE_ROD) {
					entityIn.addStat(AchievementList.BLAZE_ROD);
				}

				if ((itemstack.getItem() == Items.DIAMOND) && (this.getThrower() != null)) {
					EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(this.getThrower());

					if ((entityplayer != null) && (entityplayer != entityIn)) {
						entityplayer.addStat(AchievementList.DIAMONDS_TO_YOU);
					}
				}

				if (!this.isSilent()) {
					this.worldObj.playSound((EntityPlayer) null, entityIn.posX, entityIn.posY, entityIn.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F,
							(((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
				}

				entityIn.onItemPickup(this, i);

				if (itemstack.stackSize <= 0) {
					this.setDead();
				}

				entityIn.addStat(StatList.getObjectsPickedUpStats(itemstack.getItem()), i);
			}
		}
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName() {
		return this.hasCustomName() ? this.getCustomNameTag() : I18n.translateToLocal("item." + this.getEntityItem().getUnlocalizedName());
	}

	/**
	 * Returns true if it's possible to attack this entity with an item.
	 */
	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	@Override
	@Nullable
	public Entity changeDimension(int dimensionIn) {
		Entity entity = super.changeDimension(dimensionIn);

		if (!this.worldObj.isRemote && (entity instanceof EntityItem)) {
			((EntityItem) entity).searchForOtherItemsNearby();
		}

		return entity;
	}

	/**
	 * Returns the ItemStack corresponding to the Entity (Note: if no item exists, will log an error but still return an ItemStack containing Block.stone)
	 */
	public ItemStack getEntityItem() {
		ItemStack itemstack = (ItemStack) ((Optional) this.getDataManager().get(ITEM)).orNull();

		if (itemstack == null) {
			if (this.worldObj != null) {
				LOGGER.error("Item entity {} has no item?!", new Object[] { Integer.valueOf(this.getEntityId()) });
			}

			return new ItemStack(Blocks.STONE);
		} else {
			return itemstack;
		}
	}

	/**
	 * Sets the ItemStack for this entity
	 */
	public void setEntityItemStack(@Nullable ItemStack stack) {
		this.getDataManager().set(ITEM, Optional.fromNullable(stack));
		this.getDataManager().setDirty(ITEM);
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getThrower() {
		return this.thrower;
	}

	public void setThrower(String thrower) {
		this.thrower = thrower;
	}

	public int getAge() {
		return this.age;
	}

	public void setDefaultPickupDelay() {
		this.delayBeforeCanPickup = 10;
	}

	public void setNoPickupDelay() {
		this.delayBeforeCanPickup = 0;
	}

	public void setInfinitePickupDelay() {
		this.delayBeforeCanPickup = 32767;
	}

	public void setPickupDelay(int ticks) {
		this.delayBeforeCanPickup = ticks;
	}

	public boolean cannotPickup() {
		return this.delayBeforeCanPickup > 0;
	}

	public void setNoDespawn() {
		this.age = -6000;
	}

	public void makeFakeItem() {
		this.setInfinitePickupDelay();
		this.age = 5999;
	}
}
