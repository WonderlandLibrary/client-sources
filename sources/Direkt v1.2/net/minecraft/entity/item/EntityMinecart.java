package net.minecraft.entity.item;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityMinecart extends Entity implements IWorldNameable {
	private static final DataParameter<Integer> ROLLING_AMPLITUDE = EntityDataManager.<Integer> createKey(EntityMinecart.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> ROLLING_DIRECTION = EntityDataManager.<Integer> createKey(EntityMinecart.class, DataSerializers.VARINT);
	private static final DataParameter<Float> DAMAGE = EntityDataManager.<Float> createKey(EntityMinecart.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> DISPLAY_TILE = EntityDataManager.<Integer> createKey(EntityMinecart.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> DISPLAY_TILE_OFFSET = EntityDataManager.<Integer> createKey(EntityMinecart.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> SHOW_BLOCK = EntityDataManager.<Boolean> createKey(EntityMinecart.class, DataSerializers.BOOLEAN);
	private boolean isInReverse;

	/** Minecart rotational logic matrix */
	private static final int[][][] MATRIX = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1, 0, 0 }, { 1, 0, 0 } }, { { -1, -1, 0 }, { 1, 0, 0 } }, { { -1, 0, 0 }, { 1, -1, 0 } },
			{ { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1, 0, 0 } }, { { 0, 0, 1 }, { -1, 0, 0 } }, { { 0, 0, -1 }, { -1, 0, 0 } },
			{ { 0, 0, -1 }, { 1, 0, 0 } } };

	/** appears to be the progress of the turn */
	private int turnProgress;
	private double minecartX;
	private double minecartY;
	private double minecartZ;
	private double minecartYaw;
	private double minecartPitch;
	private double velocityX;
	private double velocityY;
	private double velocityZ;

	public EntityMinecart(World worldIn) {
		super(worldIn);
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.7F);
	}

	public static EntityMinecart create(World worldIn, double x, double y, double z, EntityMinecart.Type typeIn) {
		switch (typeIn) {
		case CHEST:
			return new EntityMinecartChest(worldIn, x, y, z);

		case FURNACE:
			return new EntityMinecartFurnace(worldIn, x, y, z);

		case TNT:
			return new EntityMinecartTNT(worldIn, x, y, z);

		case SPAWNER:
			return new EntityMinecartMobSpawner(worldIn, x, y, z);

		case HOPPER:
			return new EntityMinecartHopper(worldIn, x, y, z);

		case COMMAND_BLOCK:
			return new EntityMinecartCommandBlock(worldIn, x, y, z);

		default:
			return new EntityMinecartEmpty(worldIn, x, y, z);
		}
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(ROLLING_AMPLITUDE, Integer.valueOf(0));
		this.dataManager.register(ROLLING_DIRECTION, Integer.valueOf(1));
		this.dataManager.register(DAMAGE, Float.valueOf(0.0F));
		this.dataManager.register(DISPLAY_TILE, Integer.valueOf(0));
		this.dataManager.register(DISPLAY_TILE_OFFSET, Integer.valueOf(6));
		this.dataManager.register(SHOW_BLOCK, Boolean.valueOf(false));
	}

	@Override
	@Nullable

	/**
	 * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be pushable on contact, like boats or minecarts.
	 */
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
	}

	@Override
	@Nullable

	/**
	 * Returns the collision bounding box for this entity
	 */
	public AxisAlignedBB getCollisionBoundingBox() {
		return null;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return true;
	}

	public EntityMinecart(World worldIn, double x, double y, double z) {
		this(worldIn);
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
	@Override
	public double getMountedYOffset() {
		return 0.0D;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.worldObj.isRemote && !this.isDead) {
			if (this.isEntityInvulnerable(source)) {
				return false;
			} else {
				this.setRollingDirection(-this.getRollingDirection());
				this.setRollingAmplitude(10);
				this.setBeenAttacked();
				this.setDamage(this.getDamage() + (amount * 10.0F));
				boolean flag = (source.getEntity() instanceof EntityPlayer) && ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

				if (flag || (this.getDamage() > 40.0F)) {
					this.removePassengers();

					if (flag && !this.hasCustomName()) {
						this.setDead();
					} else {
						this.killMinecart(source);
					}
				}

				return true;
			}
		} else {
			return true;
		}
	}

	public void killMinecart(DamageSource source) {
		this.setDead();

		if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
			ItemStack itemstack = new ItemStack(Items.MINECART, 1);

			if (this.getName() != null) {
				itemstack.setStackDisplayName(this.getName());
			}

			this.entityDropItem(itemstack, 0.0F);
		}
	}

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
	 */
	@Override
	public void performHurtAnimation() {
		this.setRollingDirection(-this.getRollingDirection());
		this.setRollingAmplitude(10);
		this.setDamage(this.getDamage() + (this.getDamage() * 10.0F));
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		super.setDead();
	}

	/**
	 * Gets the horizontal facing direction of this Entity, adjusted to take specially-treated entity types into account.
	 */
	@Override
	public EnumFacing getAdjustedHorizontalFacing() {
		return this.isInReverse ? this.getHorizontalFacing().getOpposite().rotateY() : this.getHorizontalFacing().rotateY();
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		if (this.getRollingAmplitude() > 0) {
			this.setRollingAmplitude(this.getRollingAmplitude() - 1);
		}

		if (this.getDamage() > 0.0F) {
			this.setDamage(this.getDamage() - 1.0F);
		}

		if (this.posY < -64.0D) {
			this.kill();
		}

		if (!this.worldObj.isRemote && (this.worldObj instanceof WorldServer)) {
			this.worldObj.theProfiler.startSection("portal");
			MinecraftServer minecraftserver = this.worldObj.getMinecraftServer();
			int i = this.getMaxInPortalTime();

			if (this.inPortal) {
				if (minecraftserver.getAllowNether()) {
					if (!this.isRiding() && (this.portalCounter++ >= i)) {
						this.portalCounter = i;
						this.timeUntilPortal = this.getPortalCooldown();
						int j;

						if (this.worldObj.provider.getDimensionType().getId() == -1) {
							j = 0;
						} else {
							j = -1;
						}

						this.changeDimension(j);
					}

					this.inPortal = false;
				}
			} else {
				if (this.portalCounter > 0) {
					this.portalCounter -= 4;
				}

				if (this.portalCounter < 0) {
					this.portalCounter = 0;
				}
			}

			if (this.timeUntilPortal > 0) {
				--this.timeUntilPortal;
			}

			this.worldObj.theProfiler.endSection();
		}

		if (this.worldObj.isRemote) {
			if (this.turnProgress > 0) {
				double d4 = this.posX + ((this.minecartX - this.posX) / this.turnProgress);
				double d5 = this.posY + ((this.minecartY - this.posY) / this.turnProgress);
				double d6 = this.posZ + ((this.minecartZ - this.posZ) / this.turnProgress);
				double d1 = MathHelper.wrapDegrees(this.minecartYaw - this.rotationYaw);
				this.rotationYaw = (float) (this.rotationYaw + (d1 / this.turnProgress));
				this.rotationPitch = (float) (this.rotationPitch + ((this.minecartPitch - this.rotationPitch) / this.turnProgress));
				--this.turnProgress;
				this.setPosition(d4, d5, d6);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}
		} else {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			if (!this.func_189652_ae()) {
				this.motionY -= 0.03999999910593033D;
			}

			int k = MathHelper.floor_double(this.posX);
			int l = MathHelper.floor_double(this.posY);
			int i1 = MathHelper.floor_double(this.posZ);

			if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(k, l - 1, i1))) {
				--l;
			}

			BlockPos blockpos = new BlockPos(k, l, i1);
			IBlockState iblockstate = this.worldObj.getBlockState(blockpos);

			if (BlockRailBase.isRailBlock(iblockstate)) {
				this.moveAlongTrack(blockpos, iblockstate);

				if (iblockstate.getBlock() == Blocks.ACTIVATOR_RAIL) {
					this.onActivatorRailPass(k, l, i1, iblockstate.getValue(BlockRailPowered.POWERED).booleanValue());
				}
			} else {
				this.moveDerailedMinecart();
			}

			this.doBlockCollisions();
			this.rotationPitch = 0.0F;
			double d0 = this.prevPosX - this.posX;
			double d2 = this.prevPosZ - this.posZ;

			if (((d0 * d0) + (d2 * d2)) > 0.001D) {
				this.rotationYaw = (float) ((MathHelper.atan2(d2, d0) * 180.0D) / Math.PI);

				if (this.isInReverse) {
					this.rotationYaw += 180.0F;
				}
			}

			double d3 = MathHelper.wrapDegrees(this.rotationYaw - this.prevRotationYaw);

			if ((d3 < -170.0D) || (d3 >= 170.0D)) {
				this.rotationYaw += 180.0F;
				this.isInReverse = !this.isInReverse;
			}

			this.setRotation(this.rotationYaw, this.rotationPitch);

			if ((this.getType() == EntityMinecart.Type.RIDEABLE) && (((this.motionX * this.motionX) + (this.motionZ * this.motionZ)) > 0.01D)) {
				List<Entity> list = this.worldObj.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D),
						EntitySelectors.<Entity> getTeamCollisionPredicate(this));

				if (!list.isEmpty()) {
					for (int j1 = 0; j1 < list.size(); ++j1) {
						Entity entity1 = list.get(j1);

						if (!(entity1 instanceof EntityPlayer) && !(entity1 instanceof EntityIronGolem) && !(entity1 instanceof EntityMinecart) && !this.isBeingRidden() && !entity1.isRiding()) {
							entity1.startRiding(this);
						} else {
							entity1.applyEntityCollision(this);
						}
					}
				}
			} else {
				for (Entity entity : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D))) {
					if (!this.isPassenger(entity) && entity.canBePushed() && (entity instanceof EntityMinecart)) {
						entity.applyEntityCollision(this);
					}
				}
			}

			this.handleWaterMovement();
		}
	}

	/**
	 * Get's the maximum speed for a minecart
	 */
	protected double getMaximumSpeed() {
		return 0.4D;
	}

	/**
	 * Called every tick the minecart is on an activator rail.
	 */
	public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {
	}

	/**
	 * Moves a minecart that is not attached to a rail
	 */
	protected void moveDerailedMinecart() {
		double d0 = this.getMaximumSpeed();
		this.motionX = MathHelper.clamp_double(this.motionX, -d0, d0);
		this.motionZ = MathHelper.clamp_double(this.motionZ, -d0, d0);

		if (this.onGround) {
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
		}

		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		if (!this.onGround) {
			this.motionX *= 0.949999988079071D;
			this.motionY *= 0.949999988079071D;
			this.motionZ *= 0.949999988079071D;
		}
	}

	@SuppressWarnings("incomplete-switch")
	protected void moveAlongTrack(BlockPos p_180460_1_, IBlockState p_180460_2_) {
		this.fallDistance = 0.0F;
		Vec3d vec3d = this.getPos(this.posX, this.posY, this.posZ);
		this.posY = p_180460_1_.getY();
		boolean flag = false;
		boolean flag1 = false;
		BlockRailBase blockrailbase = (BlockRailBase) p_180460_2_.getBlock();

		if (blockrailbase == Blocks.GOLDEN_RAIL) {
			flag = p_180460_2_.getValue(BlockRailPowered.POWERED).booleanValue();
			flag1 = !flag;
		}

		double d0 = 0.0078125D;
		BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = p_180460_2_.getValue(blockrailbase.getShapeProperty());

		switch (blockrailbase$enumraildirection) {
		case ASCENDING_EAST:
			this.motionX -= 0.0078125D;
			++this.posY;
			break;

		case ASCENDING_WEST:
			this.motionX += 0.0078125D;
			++this.posY;
			break;

		case ASCENDING_NORTH:
			this.motionZ += 0.0078125D;
			++this.posY;
			break;

		case ASCENDING_SOUTH:
			this.motionZ -= 0.0078125D;
			++this.posY;
		}

		int[][] aint = MATRIX[blockrailbase$enumraildirection.getMetadata()];
		double d1 = aint[1][0] - aint[0][0];
		double d2 = aint[1][2] - aint[0][2];
		double d3 = Math.sqrt((d1 * d1) + (d2 * d2));
		double d4 = (this.motionX * d1) + (this.motionZ * d2);

		if (d4 < 0.0D) {
			d1 = -d1;
			d2 = -d2;
		}

		double d5 = Math.sqrt((this.motionX * this.motionX) + (this.motionZ * this.motionZ));

		if (d5 > 2.0D) {
			d5 = 2.0D;
		}

		this.motionX = (d5 * d1) / d3;
		this.motionZ = (d5 * d2) / d3;
		Entity entity = this.getPassengers().isEmpty() ? null : (Entity) this.getPassengers().get(0);

		if (entity instanceof EntityLivingBase) {
			double d6 = ((EntityLivingBase) entity).moveForward;

			if (d6 > 0.0D) {
				double d7 = -Math.sin(entity.rotationYaw * 0.017453292F);
				double d8 = Math.cos(entity.rotationYaw * 0.017453292F);
				double d9 = (this.motionX * this.motionX) + (this.motionZ * this.motionZ);

				if (d9 < 0.01D) {
					this.motionX += d7 * 0.1D;
					this.motionZ += d8 * 0.1D;
					flag1 = false;
				}
			}
		}

		if (flag1) {
			double d17 = Math.sqrt((this.motionX * this.motionX) + (this.motionZ * this.motionZ));

			if (d17 < 0.03D) {
				this.motionX *= 0.0D;
				this.motionY *= 0.0D;
				this.motionZ *= 0.0D;
			} else {
				this.motionX *= 0.5D;
				this.motionY *= 0.0D;
				this.motionZ *= 0.5D;
			}
		}

		double d18 = p_180460_1_.getX() + 0.5D + (aint[0][0] * 0.5D);
		double d19 = p_180460_1_.getZ() + 0.5D + (aint[0][2] * 0.5D);
		double d20 = p_180460_1_.getX() + 0.5D + (aint[1][0] * 0.5D);
		double d21 = p_180460_1_.getZ() + 0.5D + (aint[1][2] * 0.5D);
		d1 = d20 - d18;
		d2 = d21 - d19;
		double d10;

		if (d1 == 0.0D) {
			this.posX = p_180460_1_.getX() + 0.5D;
			d10 = this.posZ - p_180460_1_.getZ();
		} else if (d2 == 0.0D) {
			this.posZ = p_180460_1_.getZ() + 0.5D;
			d10 = this.posX - p_180460_1_.getX();
		} else {
			double d11 = this.posX - d18;
			double d12 = this.posZ - d19;
			d10 = ((d11 * d1) + (d12 * d2)) * 2.0D;
		}

		this.posX = d18 + (d1 * d10);
		this.posZ = d19 + (d2 * d10);
		this.setPosition(this.posX, this.posY, this.posZ);
		double d22 = this.motionX;
		double d23 = this.motionZ;

		if (this.isBeingRidden()) {
			d22 *= 0.75D;
			d23 *= 0.75D;
		}

		double d13 = this.getMaximumSpeed();
		d22 = MathHelper.clamp_double(d22, -d13, d13);
		d23 = MathHelper.clamp_double(d23, -d13, d13);
		this.moveEntity(d22, 0.0D, d23);

		if ((aint[0][1] != 0) && ((MathHelper.floor_double(this.posX) - p_180460_1_.getX()) == aint[0][0]) && ((MathHelper.floor_double(this.posZ) - p_180460_1_.getZ()) == aint[0][2])) {
			this.setPosition(this.posX, this.posY + aint[0][1], this.posZ);
		} else if ((aint[1][1] != 0) && ((MathHelper.floor_double(this.posX) - p_180460_1_.getX()) == aint[1][0]) && ((MathHelper.floor_double(this.posZ) - p_180460_1_.getZ()) == aint[1][2])) {
			this.setPosition(this.posX, this.posY + aint[1][1], this.posZ);
		}

		this.applyDrag();
		Vec3d vec3d1 = this.getPos(this.posX, this.posY, this.posZ);

		if ((vec3d1 != null) && (vec3d != null)) {
			double d14 = (vec3d.yCoord - vec3d1.yCoord) * 0.05D;
			d5 = Math.sqrt((this.motionX * this.motionX) + (this.motionZ * this.motionZ));

			if (d5 > 0.0D) {
				this.motionX = (this.motionX / d5) * (d5 + d14);
				this.motionZ = (this.motionZ / d5) * (d5 + d14);
			}

			this.setPosition(this.posX, vec3d1.yCoord, this.posZ);
		}

		int j = MathHelper.floor_double(this.posX);
		int i = MathHelper.floor_double(this.posZ);

		if ((j != p_180460_1_.getX()) || (i != p_180460_1_.getZ())) {
			d5 = Math.sqrt((this.motionX * this.motionX) + (this.motionZ * this.motionZ));
			this.motionX = d5 * (j - p_180460_1_.getX());
			this.motionZ = d5 * (i - p_180460_1_.getZ());
		}

		if (flag) {
			double d15 = Math.sqrt((this.motionX * this.motionX) + (this.motionZ * this.motionZ));

			if (d15 > 0.01D) {
				double d16 = 0.06D;
				this.motionX += (this.motionX / d15) * 0.06D;
				this.motionZ += (this.motionZ / d15) * 0.06D;
			} else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
				if (this.worldObj.getBlockState(p_180460_1_.west()).isNormalCube()) {
					this.motionX = 0.02D;
				} else if (this.worldObj.getBlockState(p_180460_1_.east()).isNormalCube()) {
					this.motionX = -0.02D;
				}
			} else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
				if (this.worldObj.getBlockState(p_180460_1_.north()).isNormalCube()) {
					this.motionZ = 0.02D;
				} else if (this.worldObj.getBlockState(p_180460_1_.south()).isNormalCube()) {
					this.motionZ = -0.02D;
				}
			}
		}
	}

	protected void applyDrag() {
		if (this.isBeingRidden()) {
			this.motionX *= 0.996999979019165D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.996999979019165D;
		} else {
			this.motionX *= 0.9599999785423279D;
			this.motionY *= 0.0D;
			this.motionZ *= 0.9599999785423279D;
		}
	}

	/**
	 * Sets the x,y,z of the entity from the given parameters. Also seems to set up a bounding box.
	 */
	@Override
	public void setPosition(double x, double y, double z) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		float f = this.width / 2.0F;
		float f1 = this.height;
		this.setEntityBoundingBox(new AxisAlignedBB(x - f, y, z - f, x + f, y + f1, z + f));
	}

	public Vec3d getPosOffset(double p_70495_1_, double p_70495_3_, double p_70495_5_, double p_70495_7_) {
		int i = MathHelper.floor_double(p_70495_1_);
		int j = MathHelper.floor_double(p_70495_3_);
		int k = MathHelper.floor_double(p_70495_5_);

		if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(i, j - 1, k))) {
			--j;
		}

		IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));

		if (BlockRailBase.isRailBlock(iblockstate)) {
			BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.getValue(((BlockRailBase) iblockstate.getBlock()).getShapeProperty());
			p_70495_3_ = j;

			if (blockrailbase$enumraildirection.isAscending()) {
				p_70495_3_ = j + 1;
			}

			int[][] aint = MATRIX[blockrailbase$enumraildirection.getMetadata()];
			double d0 = aint[1][0] - aint[0][0];
			double d1 = aint[1][2] - aint[0][2];
			double d2 = Math.sqrt((d0 * d0) + (d1 * d1));
			d0 = d0 / d2;
			d1 = d1 / d2;
			p_70495_1_ = p_70495_1_ + (d0 * p_70495_7_);
			p_70495_5_ = p_70495_5_ + (d1 * p_70495_7_);

			if ((aint[0][1] != 0) && ((MathHelper.floor_double(p_70495_1_) - i) == aint[0][0]) && ((MathHelper.floor_double(p_70495_5_) - k) == aint[0][2])) {
				p_70495_3_ += aint[0][1];
			} else if ((aint[1][1] != 0) && ((MathHelper.floor_double(p_70495_1_) - i) == aint[1][0]) && ((MathHelper.floor_double(p_70495_5_) - k) == aint[1][2])) {
				p_70495_3_ += aint[1][1];
			}

			return this.getPos(p_70495_1_, p_70495_3_, p_70495_5_);
		} else {
			return null;
		}
	}

	public Vec3d getPos(double p_70489_1_, double p_70489_3_, double p_70489_5_) {
		int i = MathHelper.floor_double(p_70489_1_);
		int j = MathHelper.floor_double(p_70489_3_);
		int k = MathHelper.floor_double(p_70489_5_);

		if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(i, j - 1, k))) {
			--j;
		}

		IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));

		if (BlockRailBase.isRailBlock(iblockstate)) {
			BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = iblockstate.getValue(((BlockRailBase) iblockstate.getBlock()).getShapeProperty());
			int[][] aint = MATRIX[blockrailbase$enumraildirection.getMetadata()];
			double d0 = i + 0.5D + (aint[0][0] * 0.5D);
			double d1 = j + 0.0625D + (aint[0][1] * 0.5D);
			double d2 = k + 0.5D + (aint[0][2] * 0.5D);
			double d3 = i + 0.5D + (aint[1][0] * 0.5D);
			double d4 = j + 0.0625D + (aint[1][1] * 0.5D);
			double d5 = k + 0.5D + (aint[1][2] * 0.5D);
			double d6 = d3 - d0;
			double d7 = (d4 - d1) * 2.0D;
			double d8 = d5 - d2;
			double d9;

			if (d6 == 0.0D) {
				d9 = p_70489_5_ - k;
			} else if (d8 == 0.0D) {
				d9 = p_70489_1_ - i;
			} else {
				double d10 = p_70489_1_ - d0;
				double d11 = p_70489_5_ - d2;
				d9 = ((d10 * d6) + (d11 * d8)) * 2.0D;
			}

			p_70489_1_ = d0 + (d6 * d9);
			p_70489_3_ = d1 + (d7 * d9);
			p_70489_5_ = d2 + (d8 * d9);

			if (d7 < 0.0D) {
				++p_70489_3_;
			}

			if (d7 > 0.0D) {
				p_70489_3_ += 0.5D;
			}

			return new Vec3d(p_70489_1_, p_70489_3_, p_70489_5_);
		} else {
			return null;
		}
	}

	/**
	 * Gets the bounding box of this Entity, adjusted to take auxiliary entities into account (e.g. the tile contained by a minecart, such as a command block).
	 */
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
		return this.hasDisplayTile() ? axisalignedbb.expandXyz(Math.abs(this.getDisplayTileOffset()) / 16.0D) : axisalignedbb;
	}

	public static void func_189669_a(DataFixer p_189669_0_, String p_189669_1_) {
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if (compound.getBoolean("CustomDisplayTile")) {
			Block block;

			if (compound.hasKey("DisplayTile", 8)) {
				block = Block.getBlockFromName(compound.getString("DisplayTile"));
			} else {
				block = Block.getBlockById(compound.getInteger("DisplayTile"));
			}

			int i = compound.getInteger("DisplayData");
			this.setDisplayTile(block == null ? Blocks.AIR.getDefaultState() : block.getStateFromMeta(i));
			this.setDisplayTileOffset(compound.getInteger("DisplayOffset"));
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		if (this.hasDisplayTile()) {
			compound.setBoolean("CustomDisplayTile", true);
			IBlockState iblockstate = this.getDisplayTile();
			ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(iblockstate.getBlock());
			compound.setString("DisplayTile", resourcelocation == null ? "" : resourcelocation.toString());
			compound.setInteger("DisplayData", iblockstate.getBlock().getMetaFromState(iblockstate));
			compound.setInteger("DisplayOffset", this.getDisplayTileOffset());
		}
	}

	/**
	 * Applies a velocity to the entities, to push them away from eachother.
	 */
	@Override
	public void applyEntityCollision(Entity entityIn) {
		if (!this.worldObj.isRemote) {
			if (!entityIn.noClip && !this.noClip) {
				if (!this.isPassenger(entityIn)) {
					double d0 = entityIn.posX - this.posX;
					double d1 = entityIn.posZ - this.posZ;
					double d2 = (d0 * d0) + (d1 * d1);

					if (d2 >= 9.999999747378752E-5D) {
						d2 = MathHelper.sqrt_double(d2);
						d0 = d0 / d2;
						d1 = d1 / d2;
						double d3 = 1.0D / d2;

						if (d3 > 1.0D) {
							d3 = 1.0D;
						}

						d0 = d0 * d3;
						d1 = d1 * d3;
						d0 = d0 * 0.10000000149011612D;
						d1 = d1 * 0.10000000149011612D;
						d0 = d0 * (1.0F - this.entityCollisionReduction);
						d1 = d1 * (1.0F - this.entityCollisionReduction);
						d0 = d0 * 0.5D;
						d1 = d1 * 0.5D;

						if (entityIn instanceof EntityMinecart) {
							double d4 = entityIn.posX - this.posX;
							double d5 = entityIn.posZ - this.posZ;
							Vec3d vec3d = (new Vec3d(d4, 0.0D, d5)).normalize();
							Vec3d vec3d1 = (new Vec3d(MathHelper.cos(this.rotationYaw * 0.017453292F), 0.0D, MathHelper.sin(this.rotationYaw * 0.017453292F))).normalize();
							double d6 = Math.abs(vec3d.dotProduct(vec3d1));

							if (d6 < 0.800000011920929D) { return; }

							double d7 = entityIn.motionX + this.motionX;
							double d8 = entityIn.motionZ + this.motionZ;

							if ((((EntityMinecart) entityIn).getType() == EntityMinecart.Type.FURNACE) && (this.getType() != EntityMinecart.Type.FURNACE)) {
								this.motionX *= 0.20000000298023224D;
								this.motionZ *= 0.20000000298023224D;
								this.addVelocity(entityIn.motionX - d0, 0.0D, entityIn.motionZ - d1);
								entityIn.motionX *= 0.949999988079071D;
								entityIn.motionZ *= 0.949999988079071D;
							} else if ((((EntityMinecart) entityIn).getType() != EntityMinecart.Type.FURNACE) && (this.getType() == EntityMinecart.Type.FURNACE)) {
								entityIn.motionX *= 0.20000000298023224D;
								entityIn.motionZ *= 0.20000000298023224D;
								entityIn.addVelocity(this.motionX + d0, 0.0D, this.motionZ + d1);
								this.motionX *= 0.949999988079071D;
								this.motionZ *= 0.949999988079071D;
							} else {
								d7 = d7 / 2.0D;
								d8 = d8 / 2.0D;
								this.motionX *= 0.20000000298023224D;
								this.motionZ *= 0.20000000298023224D;
								this.addVelocity(d7 - d0, 0.0D, d8 - d1);
								entityIn.motionX *= 0.20000000298023224D;
								entityIn.motionZ *= 0.20000000298023224D;
								entityIn.addVelocity(d7 + d0, 0.0D, d8 + d1);
							}
						} else {
							this.addVelocity(-d0, 0.0D, -d1);
							entityIn.addVelocity(d0 / 4.0D, 0.0D, d1 / 4.0D);
						}
					}
				}
			}
		}
	}

	/**
	 * Set the position and rotation values directly without any clamping.
	 */
	@Override
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.minecartX = x;
		this.minecartY = y;
		this.minecartZ = z;
		this.minecartYaw = yaw;
		this.minecartPitch = pitch;
		this.turnProgress = posRotationIncrements + 2;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	/**
	 * Updates the velocity of the entity to a new value.
	 */
	@Override
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		this.velocityX = this.motionX;
		this.velocityY = this.motionY;
		this.velocityZ = this.motionZ;
	}

	/**
	 * Sets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over 40.
	 */
	public void setDamage(float damage) {
		this.dataManager.set(DAMAGE, Float.valueOf(damage));
	}

	/**
	 * Gets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over 40.
	 */
	public float getDamage() {
		return this.dataManager.get(DAMAGE).floatValue();
	}

	/**
	 * Sets the rolling amplitude the cart rolls while being attacked.
	 */
	public void setRollingAmplitude(int rollingAmplitude) {
		this.dataManager.set(ROLLING_AMPLITUDE, Integer.valueOf(rollingAmplitude));
	}

	/**
	 * Gets the rolling amplitude the cart rolls while being attacked.
	 */
	public int getRollingAmplitude() {
		return this.dataManager.get(ROLLING_AMPLITUDE).intValue();
	}

	/**
	 * Sets the rolling direction the cart rolls while being attacked. Can be 1 or -1.
	 */
	public void setRollingDirection(int rollingDirection) {
		this.dataManager.set(ROLLING_DIRECTION, Integer.valueOf(rollingDirection));
	}

	/**
	 * Gets the rolling direction the cart rolls while being attacked. Can be 1 or -1.
	 */
	public int getRollingDirection() {
		return this.dataManager.get(ROLLING_DIRECTION).intValue();
	}

	public abstract EntityMinecart.Type getType();

	public IBlockState getDisplayTile() {
		return !this.hasDisplayTile() ? this.getDefaultDisplayTile() : Block.getStateById(this.getDataManager().get(DISPLAY_TILE).intValue());
	}

	public IBlockState getDefaultDisplayTile() {
		return Blocks.AIR.getDefaultState();
	}

	public int getDisplayTileOffset() {
		return !this.hasDisplayTile() ? this.getDefaultDisplayTileOffset() : this.getDataManager().get(DISPLAY_TILE_OFFSET).intValue();
	}

	public int getDefaultDisplayTileOffset() {
		return 6;
	}

	public void setDisplayTile(IBlockState displayTile) {
		this.getDataManager().set(DISPLAY_TILE, Integer.valueOf(Block.getStateId(displayTile)));
		this.setHasDisplayTile(true);
	}

	public void setDisplayTileOffset(int displayTileOffset) {
		this.getDataManager().set(DISPLAY_TILE_OFFSET, Integer.valueOf(displayTileOffset));
		this.setHasDisplayTile(true);
	}

	public boolean hasDisplayTile() {
		return this.getDataManager().get(SHOW_BLOCK).booleanValue();
	}

	public void setHasDisplayTile(boolean showBlock) {
		this.getDataManager().set(SHOW_BLOCK, Boolean.valueOf(showBlock));
	}

	public static enum Type {
		RIDEABLE(0, "MinecartRideable"), CHEST(1, "MinecartChest"), FURNACE(2, "MinecartFurnace"), TNT(3, "MinecartTNT"), SPAWNER(4, "MinecartSpawner"), HOPPER(5, "MinecartHopper"), COMMAND_BLOCK(6,
				"MinecartCommandBlock");

		private static final Map<Integer, EntityMinecart.Type> BY_ID = Maps.<Integer, EntityMinecart.Type> newHashMap();
		private final int id;
		private final String name;

		private Type(int idIn, String nameIn) {
			this.id = idIn;
			this.name = nameIn;
		}

		public int getId() {
			return this.id;
		}

		public String getName() {
			return this.name;
		}

		public static EntityMinecart.Type getById(int idIn) {
			EntityMinecart.Type entityminecart$type = BY_ID.get(Integer.valueOf(idIn));
			return entityminecart$type == null ? RIDEABLE : entityminecart$type;
		}

		static {
			for (EntityMinecart.Type entityminecart$type : values()) {
				BY_ID.put(Integer.valueOf(entityminecart$type.getId()), entityminecart$type);
			}
		}
	}
}
