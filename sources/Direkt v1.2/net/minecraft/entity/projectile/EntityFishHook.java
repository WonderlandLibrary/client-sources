package net.minecraft.entity.projectile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityFishHook extends Entity {
	private static final DataParameter<Integer> DATA_HOOKED_ENTITY = EntityDataManager.<Integer> createKey(EntityFishHook.class, DataSerializers.VARINT);
	private BlockPos field_189740_d;
	private Block inTile;
	private boolean inGround;
	public EntityPlayer angler;
	private int ticksInGround;
	private int ticksInAir;
	private int ticksCatchable;
	private int ticksCaughtDelay;
	private int ticksCatchableDelay;
	private float fishApproachAngle;
	private int fishPosRotationIncrements;
	private double fishX;
	private double fishY;
	private double fishZ;
	private double fishYaw;
	private double fishPitch;
	private double clientMotionX;
	private double clientMotionY;
	private double clientMotionZ;
	public Entity caughtEntity;

	public EntityFishHook(World worldIn) {
		super(worldIn);
		this.field_189740_d = new BlockPos(-1, -1, -1);
		this.setSize(0.25F, 0.25F);
		this.ignoreFrustumCheck = true;
	}

	public EntityFishHook(World worldIn, double x, double y, double z, EntityPlayer anglerIn) {
		this(worldIn);
		this.setPosition(x, y, z);
		this.ignoreFrustumCheck = true;
		this.angler = anglerIn;
		anglerIn.fishEntity = this;
	}

	public EntityFishHook(World worldIn, EntityPlayer fishingPlayer) {
		super(worldIn);
		this.field_189740_d = new BlockPos(-1, -1, -1);
		this.ignoreFrustumCheck = true;
		this.angler = fishingPlayer;
		this.angler.fishEntity = this;
		this.setSize(0.25F, 0.25F);
		this.setLocationAndAngles(fishingPlayer.posX, fishingPlayer.posY + fishingPlayer.getEyeHeight(), fishingPlayer.posZ, fishingPlayer.rotationYaw, fishingPlayer.rotationPitch);
		this.posX -= MathHelper.cos(this.rotationYaw * 0.017453292F) * 0.16F;
		this.posY -= 0.10000000149011612D;
		this.posZ -= MathHelper.sin(this.rotationYaw * 0.017453292F) * 0.16F;
		this.setPosition(this.posX, this.posY, this.posZ);
		float f = 0.4F;
		this.motionX = -MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * 0.4F;
		this.motionZ = MathHelper.cos(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * 0.4F;
		this.motionY = -MathHelper.sin(this.rotationPitch * 0.017453292F) * 0.4F;
		this.handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(DATA_HOOKED_ENTITY, Integer.valueOf(0));
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		if (DATA_HOOKED_ENTITY.equals(key)) {
			int i = this.getDataManager().get(DATA_HOOKED_ENTITY).intValue();

			if ((i > 0) && (this.caughtEntity != null)) {
				this.caughtEntity = null;
			}
		}

		super.notifyDataManagerChange(key);
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@Override
	public boolean isInRangeToRenderDist(double distance) {
		double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;

		if (Double.isNaN(d0)) {
			d0 = 4.0D;
		}

		d0 = d0 * 64.0D;
		return distance < (d0 * d0);
	}

	public void handleHookCasting(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_) {
		float f = MathHelper.sqrt_double((p_146035_1_ * p_146035_1_) + (p_146035_3_ * p_146035_3_) + (p_146035_5_ * p_146035_5_));
		p_146035_1_ = p_146035_1_ / f;
		p_146035_3_ = p_146035_3_ / f;
		p_146035_5_ = p_146035_5_ / f;
		p_146035_1_ = p_146035_1_ + (this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_);
		p_146035_3_ = p_146035_3_ + (this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_);
		p_146035_5_ = p_146035_5_ + (this.rand.nextGaussian() * 0.007499999832361937D * p_146035_8_);
		p_146035_1_ = p_146035_1_ * p_146035_7_;
		p_146035_3_ = p_146035_3_ * p_146035_7_;
		p_146035_5_ = p_146035_5_ * p_146035_7_;
		this.motionX = p_146035_1_;
		this.motionY = p_146035_3_;
		this.motionZ = p_146035_5_;
		float f1 = MathHelper.sqrt_double((p_146035_1_ * p_146035_1_) + (p_146035_5_ * p_146035_5_));
		this.rotationYaw = (float) (MathHelper.atan2(p_146035_1_, p_146035_5_) * (180D / Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(p_146035_3_, f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		this.ticksInGround = 0;
	}

	/**
	 * Set the position and rotation values directly without any clamping.
	 */
	@Override
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.fishX = x;
		this.fishY = y;
		this.fishZ = z;
		this.fishYaw = yaw;
		this.fishPitch = pitch;
		this.fishPosRotationIncrements = posRotationIncrements;
		this.motionX = this.clientMotionX;
		this.motionY = this.clientMotionY;
		this.motionZ = this.clientMotionZ;
	}

	/**
	 * Updates the velocity of the entity to a new value.
	 */
	@Override
	public void setVelocity(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		this.clientMotionX = this.motionX;
		this.clientMotionY = this.motionY;
		this.clientMotionZ = this.motionZ;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.worldObj.isRemote) {
			int i = this.getDataManager().get(DATA_HOOKED_ENTITY).intValue();

			if ((i > 0) && (this.caughtEntity == null)) {
				this.caughtEntity = this.worldObj.getEntityByID(i - 1);
			}
		} else {
			ItemStack itemstack = this.angler.getHeldItemMainhand();

			if (this.angler.isDead || !this.angler.isEntityAlive() || (itemstack == null) || (itemstack.getItem() != Items.FISHING_ROD) || (this.getDistanceSqToEntity(this.angler) > 1024.0D)) {
				this.setDead();
				this.angler.fishEntity = null;
				return;
			}
		}

		if (this.caughtEntity != null) {
			if (!this.caughtEntity.isDead) {
				this.posX = this.caughtEntity.posX;
				double d17 = this.caughtEntity.height;
				this.posY = this.caughtEntity.getEntityBoundingBox().minY + (d17 * 0.8D);
				this.posZ = this.caughtEntity.posZ;
				return;
			}

			this.caughtEntity = null;
		}

		if (this.fishPosRotationIncrements > 0) {
			double d3 = this.posX + ((this.fishX - this.posX) / this.fishPosRotationIncrements);
			double d4 = this.posY + ((this.fishY - this.posY) / this.fishPosRotationIncrements);
			double d6 = this.posZ + ((this.fishZ - this.posZ) / this.fishPosRotationIncrements);
			double d8 = MathHelper.wrapDegrees(this.fishYaw - this.rotationYaw);
			this.rotationYaw = (float) (this.rotationYaw + (d8 / this.fishPosRotationIncrements));
			this.rotationPitch = (float) (this.rotationPitch + ((this.fishPitch - this.rotationPitch) / this.fishPosRotationIncrements));
			--this.fishPosRotationIncrements;
			this.setPosition(d3, d4, d6);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		} else {
			if (this.inGround) {
				if (this.worldObj.getBlockState(this.field_189740_d).getBlock() == this.inTile) {
					++this.ticksInGround;

					if (this.ticksInGround == 1200) {
						this.setDead();
					}

					return;
				}

				this.inGround = false;
				this.motionX *= this.rand.nextFloat() * 0.2F;
				this.motionY *= this.rand.nextFloat() * 0.2F;
				this.motionZ *= this.rand.nextFloat() * 0.2F;
				this.ticksInGround = 0;
				this.ticksInAir = 0;
			} else {
				++this.ticksInAir;
			}

			if (!this.worldObj.isRemote) {
				Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
				Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
				RayTraceResult raytraceresult = this.worldObj.rayTraceBlocks(vec3d1, vec3d);
				vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
				vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

				if (raytraceresult != null) {
					vec3d = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
				}

				Entity entity = null;
				List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(1.0D));
				double d0 = 0.0D;

				for (int j = 0; j < list.size(); ++j) {
					Entity entity1 = list.get(j);

					if (this.func_189739_a(entity1) && ((entity1 != this.angler) || (this.ticksInAir >= 5))) {
						AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
						RayTraceResult raytraceresult1 = axisalignedbb1.calculateIntercept(vec3d1, vec3d);

						if (raytraceresult1 != null) {
							double d1 = vec3d1.squareDistanceTo(raytraceresult1.hitVec);

							if ((d1 < d0) || (d0 == 0.0D)) {
								entity = entity1;
								d0 = d1;
							}
						}
					}
				}

				if (entity != null) {
					raytraceresult = new RayTraceResult(entity);
				}

				if (raytraceresult != null) {
					if (raytraceresult.entityHit != null) {
						this.caughtEntity = raytraceresult.entityHit;
						this.getDataManager().set(DATA_HOOKED_ENTITY, Integer.valueOf(this.caughtEntity.getEntityId() + 1));
					} else {
						this.inGround = true;
					}
				}
			}

			if (!this.inGround) {
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				float f2 = MathHelper.sqrt_double((this.motionX * this.motionX) + (this.motionZ * this.motionZ));
				this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

				for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, f2) * (180D / Math.PI)); (this.rotationPitch - this.prevRotationPitch) < -180.0F; this.prevRotationPitch -= 360.0F) {
					;
				}

				while ((this.rotationPitch - this.prevRotationPitch) >= 180.0F) {
					this.prevRotationPitch += 360.0F;
				}

				while ((this.rotationYaw - this.prevRotationYaw) < -180.0F) {
					this.prevRotationYaw -= 360.0F;
				}

				while ((this.rotationYaw - this.prevRotationYaw) >= 180.0F) {
					this.prevRotationYaw += 360.0F;
				}

				this.rotationPitch = this.prevRotationPitch + ((this.rotationPitch - this.prevRotationPitch) * 0.2F);
				this.rotationYaw = this.prevRotationYaw + ((this.rotationYaw - this.prevRotationYaw) * 0.2F);
				float f3 = 0.92F;

				if (this.onGround || this.isCollidedHorizontally) {
					f3 = 0.5F;
				}

				int k = 5;
				double d5 = 0.0D;

				for (int l = 0; l < 5; ++l) {
					AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
					double d9 = axisalignedbb.maxY - axisalignedbb.minY;
					double d10 = axisalignedbb.minY + ((d9 * l) / 5.0D);
					double d11 = axisalignedbb.minY + ((d9 * (l + 1)) / 5.0D);
					AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(axisalignedbb.minX, d10, axisalignedbb.minZ, axisalignedbb.maxX, d11, axisalignedbb.maxZ);

					if (this.worldObj.isAABBInMaterial(axisalignedbb2, Material.WATER)) {
						d5 += 0.2D;
					}
				}

				if (!this.worldObj.isRemote && (d5 > 0.0D)) {
					WorldServer worldserver = (WorldServer) this.worldObj;
					int i1 = 1;
					BlockPos blockpos = (new BlockPos(this)).up();

					if ((this.rand.nextFloat() < 0.25F) && this.worldObj.isRainingAt(blockpos)) {
						i1 = 2;
					}

					if ((this.rand.nextFloat() < 0.5F) && !this.worldObj.canSeeSky(blockpos)) {
						--i1;
					}

					if (this.ticksCatchable > 0) {
						--this.ticksCatchable;

						if (this.ticksCatchable <= 0) {
							this.ticksCaughtDelay = 0;
							this.ticksCatchableDelay = 0;
						}
					} else if (this.ticksCatchableDelay > 0) {
						this.ticksCatchableDelay -= i1;

						if (this.ticksCatchableDelay <= 0) {
							this.motionY -= 0.20000000298023224D;
							this.playSound(SoundEvents.ENTITY_BOBBER_SPLASH, 0.25F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F));
							float f6 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
							worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX, f6 + 1.0F, this.posZ, (int) (1.0F + (this.width * 20.0F)), this.width, 0.0D, this.width,
									0.20000000298023224D, new int[0]);
							worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, this.posX, f6 + 1.0F, this.posZ, (int) (1.0F + (this.width * 20.0F)), this.width, 0.0D, this.width,
									0.20000000298023224D, new int[0]);
							this.ticksCatchable = MathHelper.getRandomIntegerInRange(this.rand, 10, 30);
						} else {
							this.fishApproachAngle = (float) (this.fishApproachAngle + (this.rand.nextGaussian() * 4.0D));
							float f5 = this.fishApproachAngle * 0.017453292F;
							float f8 = MathHelper.sin(f5);
							float f10 = MathHelper.cos(f5);
							double d13 = this.posX + f8 * this.ticksCatchableDelay * 0.1F;
							double d15 = MathHelper.floor_double(this.getEntityBoundingBox().minY) + 1.0F;
							double d16 = this.posZ + f10 * this.ticksCatchableDelay * 0.1F;
							Block block1 = worldserver.getBlockState(new BlockPos((int) d13, (int) d15 - 1, (int) d16)).getBlock();

							if ((block1 == Blocks.WATER) || (block1 == Blocks.FLOWING_WATER)) {
								if (this.rand.nextFloat() < 0.15F) {
									worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d13, d15 - 0.10000000149011612D, d16, 1, f8, 0.1D, f10, 0.0D, new int[0]);
								}

								float f = f8 * 0.04F;
								float f1 = f10 * 0.04F;
								worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d13, d15, d16, 0, f1, 0.01D, (-f), 1.0D, new int[0]);
								worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d13, d15, d16, 0, (-f1), 0.01D, f, 1.0D, new int[0]);
							}
						}
					} else if (this.ticksCaughtDelay > 0) {
						this.ticksCaughtDelay -= i1;
						float f4 = 0.15F;

						if (this.ticksCaughtDelay < 20) {
							f4 = (float) (f4 + ((20 - this.ticksCaughtDelay) * 0.05D));
						} else if (this.ticksCaughtDelay < 40) {
							f4 = (float) (f4 + ((40 - this.ticksCaughtDelay) * 0.02D));
						} else if (this.ticksCaughtDelay < 60) {
							f4 = (float) (f4 + ((60 - this.ticksCaughtDelay) * 0.01D));
						}

						if (this.rand.nextFloat() < f4) {
							float f7 = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F) * 0.017453292F;
							float f9 = MathHelper.randomFloatClamp(this.rand, 25.0F, 60.0F);
							double d12 = this.posX + MathHelper.sin(f7) * f9 * 0.1F;
							double d14 = MathHelper.floor_double(this.getEntityBoundingBox().minY) + 1.0F;
							double d2 = this.posZ + MathHelper.cos(f7) * f9 * 0.1F;
							Block block = worldserver.getBlockState(new BlockPos((int) d12, (int) d14 - 1, (int) d2)).getBlock();

							if ((block == Blocks.WATER) || (block == Blocks.FLOWING_WATER)) {
								worldserver.spawnParticle(EnumParticleTypes.WATER_SPLASH, d12, d14, d2, 2 + this.rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
							}
						}

						if (this.ticksCaughtDelay <= 0) {
							this.fishApproachAngle = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F);
							this.ticksCatchableDelay = MathHelper.getRandomIntegerInRange(this.rand, 20, 80);
						}
					} else {
						this.ticksCaughtDelay = MathHelper.getRandomIntegerInRange(this.rand, 100, 900);
						this.ticksCaughtDelay -= EnchantmentHelper.getLureModifier(this.angler) * 20 * 5;
					}

					if (this.ticksCatchable > 0) {
						this.motionY -= this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2D;
					}
				}

				double d7 = (d5 * 2.0D) - 1.0D;
				this.motionY += 0.03999999910593033D * d7;

				if (d5 > 0.0D) {
					f3 = (float) (f3 * 0.9D);
					this.motionY *= 0.8D;
				}

				this.motionX *= f3;
				this.motionY *= f3;
				this.motionZ *= f3;
				this.setPosition(this.posX, this.posY, this.posZ);
			}
		}
	}

	protected boolean func_189739_a(Entity p_189739_1_) {
		return p_189739_1_.canBeCollidedWith() || (p_189739_1_ instanceof EntityItem);
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("xTile", this.field_189740_d.getX());
		compound.setInteger("yTile", this.field_189740_d.getY());
		compound.setInteger("zTile", this.field_189740_d.getZ());
		ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(this.inTile);
		compound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
		compound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		this.field_189740_d = new BlockPos(compound.getInteger("xTile"), compound.getInteger("yTile"), compound.getInteger("zTile"));

		if (compound.hasKey("inTile", 8)) {
			this.inTile = Block.getBlockFromName(compound.getString("inTile"));
		} else {
			this.inTile = Block.getBlockById(compound.getByte("inTile") & 255);
		}

		this.inGround = compound.getByte("inGround") == 1;
	}

	public int handleHookRetraction() {
		if (this.worldObj.isRemote) {
			return 0;
		} else {
			int i = 0;

			if (this.caughtEntity != null) {
				this.bringInHookedEntity();
				this.worldObj.setEntityState(this, (byte) 31);
				i = this.caughtEntity instanceof EntityItem ? 3 : 5;
			} else if (this.ticksCatchable > 0) {
				LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer) this.worldObj);
				lootcontext$builder.withLuck(EnchantmentHelper.getLuckOfSeaModifier(this.angler) + this.angler.getLuck());

				for (ItemStack itemstack : this.worldObj.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(this.rand, lootcontext$builder.build())) {
					EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, itemstack);
					double d0 = this.angler.posX - this.posX;
					double d1 = this.angler.posY - this.posY;
					double d2 = this.angler.posZ - this.posZ;
					double d3 = MathHelper.sqrt_double((d0 * d0) + (d1 * d1) + (d2 * d2));
					double d4 = 0.1D;
					entityitem.motionX = d0 * 0.1D;
					entityitem.motionY = (d1 * 0.1D) + (MathHelper.sqrt_double(d3) * 0.08D);
					entityitem.motionZ = d2 * 0.1D;
					this.worldObj.spawnEntityInWorld(entityitem);
					this.angler.worldObj.spawnEntityInWorld(new EntityXPOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5D, this.angler.posZ + 0.5D, this.rand.nextInt(6) + 1));
				}

				i = 1;
			}

			if (this.inGround) {
				i = 2;
			}

			this.setDead();
			this.angler.fishEntity = null;
			return i;
		}
	}

	@Override
	public void handleStatusUpdate(byte id) {
		if ((id == 31) && this.worldObj.isRemote && (this.caughtEntity instanceof EntityPlayer) && ((EntityPlayer) this.caughtEntity).isUser()) {
			this.bringInHookedEntity();
		}

		super.handleStatusUpdate(id);
	}

	protected void bringInHookedEntity() {
		double d0 = this.angler.posX - this.posX;
		double d1 = this.angler.posY - this.posY;
		double d2 = this.angler.posZ - this.posZ;
		double d3 = MathHelper.sqrt_double((d0 * d0) + (d1 * d1) + (d2 * d2));
		double d4 = 0.1D;
		this.caughtEntity.motionX += d0 * 0.1D;
		this.caughtEntity.motionY += (d1 * 0.1D) + (MathHelper.sqrt_double(d3) * 0.08D);
		this.caughtEntity.motionZ += d2 * 0.1D;
	}

	/**
	 * Will get destroyed next tick.
	 */
	@Override
	public void setDead() {
		super.setDead();

		if (this.angler != null) {
			this.angler.fishEntity = null;
		}
	}
}
