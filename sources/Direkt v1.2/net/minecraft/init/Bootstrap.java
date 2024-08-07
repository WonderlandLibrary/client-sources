package net.minecraft.init;

import java.io.PrintStream;
import java.util.Random;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Bootstrap {
	public static final PrintStream SYSOUT = System.out;

	/** Whether the blocks, items, etc have already been registered */
	private static boolean alreadyRegistered;
	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Is Bootstrap registration already done?
	 */
	public static boolean isRegistered() {
		return alreadyRegistered;
	}

	static void registerDispenserBehaviors() {
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.ARROW, new BehaviorProjectileDispense() {
			@Override
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				EntityTippedArrow entitytippedarrow = new EntityTippedArrow(worldIn, position.getX(), position.getY(), position.getZ());
				entitytippedarrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
				return entitytippedarrow;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.TIPPED_ARROW, new BehaviorProjectileDispense() {
			@Override
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				EntityTippedArrow entitytippedarrow = new EntityTippedArrow(worldIn, position.getX(), position.getY(), position.getZ());
				entitytippedarrow.setPotionEffect(stackIn);
				entitytippedarrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
				return entitytippedarrow;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SPECTRAL_ARROW, new BehaviorProjectileDispense() {
			@Override
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				EntityArrow entityarrow = new EntitySpectralArrow(worldIn, position.getX(), position.getY(), position.getZ());
				entityarrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
				return entityarrow;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.EGG, new BehaviorProjectileDispense() {
			@Override
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				return new EntityEgg(worldIn, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SNOWBALL, new BehaviorProjectileDispense() {
			@Override
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				return new EntitySnowball(worldIn, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.EXPERIENCE_BOTTLE, new BehaviorProjectileDispense() {
			@Override
			protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				return new EntityExpBottle(worldIn, position.getX(), position.getY(), position.getZ());
			}

			@Override
			protected float getProjectileInaccuracy() {
				return super.getProjectileInaccuracy() * 0.5F;
			}

			@Override
			protected float getProjectileVelocity() {
				return super.getProjectileVelocity() * 1.25F;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SPLASH_POTION, new IBehaviorDispenseItem() {
			@Override
			public ItemStack dispense(IBlockSource source, final ItemStack stack) {
				return (new BehaviorProjectileDispense() {
					@Override
					protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
						return new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
					}

					@Override
					protected float getProjectileInaccuracy() {
						return super.getProjectileInaccuracy() * 0.5F;
					}

					@Override
					protected float getProjectileVelocity() {
						return super.getProjectileVelocity() * 1.25F;
					}
				}).dispense(source, stack);
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.LINGERING_POTION, new IBehaviorDispenseItem() {
			@Override
			public ItemStack dispense(IBlockSource source, final ItemStack stack) {
				return (new BehaviorProjectileDispense() {
					@Override
					protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
						return new EntityPotion(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy());
					}

					@Override
					protected float getProjectileInaccuracy() {
						return super.getProjectileInaccuracy() * 0.5F;
					}

					@Override
					protected float getProjectileVelocity() {
						return super.getProjectileVelocity() * 1.25F;
					}
				}).dispense(source, stack);
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SPAWN_EGG, new BehaviorDefaultDispenseItem() {
			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				EnumFacing enumfacing = source.func_189992_e().getValue(BlockDispenser.FACING);
				double d0 = source.getX() + enumfacing.getFrontOffsetX();
				double d1 = source.getBlockPos().getY() + 0.2F;
				double d2 = source.getZ() + enumfacing.getFrontOffsetZ();
				Entity entity = ItemMonsterPlacer.spawnCreature(source.getWorld(), ItemMonsterPlacer.getEntityIdFromItem(stack), d0, d1, d2);

				if ((entity instanceof EntityLivingBase) && stack.hasDisplayName()) {
					entity.setCustomNameTag(stack.getDisplayName());
				}

				ItemMonsterPlacer.applyItemEntityDataToEntity(source.getWorld(), (EntityPlayer) null, stack, entity);
				stack.splitStack(1);
				return stack;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.FIREWORKS, new BehaviorDefaultDispenseItem() {
			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				EnumFacing enumfacing = source.func_189992_e().getValue(BlockDispenser.FACING);
				double d0 = source.getX() + enumfacing.getFrontOffsetX();
				double d1 = source.getBlockPos().getY() + 0.2F;
				double d2 = source.getZ() + enumfacing.getFrontOffsetZ();
				EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(source.getWorld(), d0, d1, d2, stack);
				source.getWorld().spawnEntityInWorld(entityfireworkrocket);
				stack.splitStack(1);
				return stack;
			}

			@Override
			protected void playDispenseSound(IBlockSource source) {
				source.getWorld().playEvent(1004, source.getBlockPos(), 0);
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.FIRE_CHARGE, new BehaviorDefaultDispenseItem() {
			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				EnumFacing enumfacing = source.func_189992_e().getValue(BlockDispenser.FACING);
				IPosition iposition = BlockDispenser.getDispensePosition(source);
				double d0 = iposition.getX() + enumfacing.getFrontOffsetX() * 0.3F;
				double d1 = iposition.getY() + enumfacing.getFrontOffsetY() * 0.3F;
				double d2 = iposition.getZ() + enumfacing.getFrontOffsetZ() * 0.3F;
				World world = source.getWorld();
				Random random = world.rand;
				double d3 = (random.nextGaussian() * 0.05D) + enumfacing.getFrontOffsetX();
				double d4 = (random.nextGaussian() * 0.05D) + enumfacing.getFrontOffsetY();
				double d5 = (random.nextGaussian() * 0.05D) + enumfacing.getFrontOffsetZ();
				world.spawnEntityInWorld(new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5));
				stack.splitStack(1);
				return stack;
			}

			@Override
			protected void playDispenseSound(IBlockSource source) {
				source.getWorld().playEvent(1018, source.getBlockPos(), 0);
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.BOAT, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.OAK));
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SPRUCE_BOAT, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.SPRUCE));
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.BIRCH_BOAT, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.BIRCH));
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.JUNGLE_BOAT, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.JUNGLE));
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.DARK_OAK_BOAT, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.DARK_OAK));
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.ACACIA_BOAT, new Bootstrap.BehaviorDispenseBoat(EntityBoat.Type.ACACIA));
		IBehaviorDispenseItem ibehaviordispenseitem = new BehaviorDefaultDispenseItem() {
			private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();

			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				ItemBucket itembucket = (ItemBucket) stack.getItem();
				BlockPos blockpos = source.getBlockPos().offset(source.func_189992_e().getValue(BlockDispenser.FACING));

				if (itembucket.tryPlaceContainedLiquid((EntityPlayer) null, source.getWorld(), blockpos)) {
					stack.setItem(Items.BUCKET);
					stack.stackSize = 1;
					return stack;
				} else {
					return this.dispenseBehavior.dispense(source, stack);
				}
			}
		};
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.LAVA_BUCKET, ibehaviordispenseitem);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.WATER_BUCKET, ibehaviordispenseitem);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.BUCKET, new BehaviorDefaultDispenseItem() {
			private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();

			@Override
			public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				BlockPos blockpos = source.getBlockPos().offset(source.func_189992_e().getValue(BlockDispenser.FACING));
				IBlockState iblockstate = world.getBlockState(blockpos);
				Block block = iblockstate.getBlock();
				Material material = iblockstate.getMaterial();
				Item item;

				if (Material.WATER.equals(material) && (block instanceof BlockLiquid) && (iblockstate.getValue(BlockLiquid.LEVEL).intValue() == 0)) {
					item = Items.WATER_BUCKET;
				} else {
					if (!Material.LAVA.equals(material) || !(block instanceof BlockLiquid) || (iblockstate.getValue(BlockLiquid.LEVEL).intValue() != 0)) { return super.dispenseStack(source, stack); }

					item = Items.LAVA_BUCKET;
				}

				world.setBlockToAir(blockpos);

				if (--stack.stackSize == 0) {
					stack.setItem(item);
					stack.stackSize = 1;
				} else if (((TileEntityDispenser) source.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0) {
					this.dispenseBehavior.dispense(source, new ItemStack(item));
				}

				return stack;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.FLINT_AND_STEEL, new BehaviorDefaultDispenseItem() {
			private boolean succeeded = true;

			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				BlockPos blockpos = source.getBlockPos().offset(source.func_189992_e().getValue(BlockDispenser.FACING));

				if (world.isAirBlock(blockpos)) {
					world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());

					if (stack.attemptDamageItem(1, world.rand)) {
						stack.stackSize = 0;
					}
				} else if (world.getBlockState(blockpos).getBlock() == Blocks.TNT) {
					Blocks.TNT.onBlockDestroyedByPlayer(world, blockpos, Blocks.TNT.getDefaultState().withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)));
					world.setBlockToAir(blockpos);
				} else {
					this.succeeded = false;
				}

				return stack;
			}

			@Override
			protected void playDispenseSound(IBlockSource source) {
				if (this.succeeded) {
					source.getWorld().playEvent(1000, source.getBlockPos(), 0);
				} else {
					source.getWorld().playEvent(1001, source.getBlockPos(), 0);
				}
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.DYE, new BehaviorDefaultDispenseItem() {
			private boolean succeeded = true;

			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				if (EnumDyeColor.WHITE == EnumDyeColor.byDyeDamage(stack.getMetadata())) {
					World world = source.getWorld();
					BlockPos blockpos = source.getBlockPos().offset(source.func_189992_e().getValue(BlockDispenser.FACING));

					if (ItemDye.applyBonemeal(stack, world, blockpos)) {
						if (!world.isRemote) {
							world.playEvent(2005, blockpos, 0);
						}
					} else {
						this.succeeded = false;
					}

					return stack;
				} else {
					return super.dispenseStack(source, stack);
				}
			}

			@Override
			protected void playDispenseSound(IBlockSource source) {
				if (this.succeeded) {
					source.getWorld().playEvent(1000, source.getBlockPos(), 0);
				} else {
					source.getWorld().playEvent(1001, source.getBlockPos(), 0);
				}
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Item.getItemFromBlock(Blocks.TNT), new BehaviorDefaultDispenseItem() {
			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				BlockPos blockpos = source.getBlockPos().offset(source.func_189992_e().getValue(BlockDispenser.FACING));
				EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, blockpos.getX() + 0.5D, blockpos.getY(), blockpos.getZ() + 0.5D, (EntityLivingBase) null);
				world.spawnEntityInWorld(entitytntprimed);
				world.playSound((EntityPlayer) null, entitytntprimed.posX, entitytntprimed.posY, entitytntprimed.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
				--stack.stackSize;
				return stack;
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.SKULL, new BehaviorDefaultDispenseItem() {
			private boolean succeeded = true;

			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				EnumFacing enumfacing = source.func_189992_e().getValue(BlockDispenser.FACING);
				BlockPos blockpos = source.getBlockPos().offset(enumfacing);
				BlockSkull blockskull = Blocks.SKULL;

				if (world.isAirBlock(blockpos) && blockskull.canDispenserPlace(world, blockpos, stack)) {
					if (!world.isRemote) {
						world.setBlockState(blockpos, blockskull.getDefaultState().withProperty(BlockSkull.FACING, EnumFacing.UP), 3);
						TileEntity tileentity = world.getTileEntity(blockpos);

						if (tileentity instanceof TileEntitySkull) {
							if (stack.getMetadata() == 3) {
								GameProfile gameprofile = null;

								if (stack.hasTagCompound()) {
									NBTTagCompound nbttagcompound = stack.getTagCompound();

									if (nbttagcompound.hasKey("SkullOwner", 10)) {
										gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
									} else if (nbttagcompound.hasKey("SkullOwner", 8)) {
										String s = nbttagcompound.getString("SkullOwner");

										if (!StringUtils.isNullOrEmpty(s)) {
											gameprofile = new GameProfile((UUID) null, s);
										}
									}
								}

								((TileEntitySkull) tileentity).setPlayerProfile(gameprofile);
							} else {
								((TileEntitySkull) tileentity).setType(stack.getMetadata());
							}

							((TileEntitySkull) tileentity).setSkullRotation(enumfacing.getOpposite().getHorizontalIndex() * 4);
							Blocks.SKULL.checkWitherSpawn(world, blockpos, (TileEntitySkull) tileentity);
						}

						--stack.stackSize;
					}
				} else if (ItemArmor.dispenseArmor(source, stack) == null) {
					this.succeeded = false;
				}

				return stack;
			}

			@Override
			protected void playDispenseSound(IBlockSource source) {
				if (this.succeeded) {
					source.getWorld().playEvent(1000, source.getBlockPos(), 0);
				} else {
					source.getWorld().playEvent(1001, source.getBlockPos(), 0);
				}
			}
		});
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Item.getItemFromBlock(Blocks.PUMPKIN), new BehaviorDefaultDispenseItem() {
			private boolean succeeded = true;

			@Override
			protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				World world = source.getWorld();
				BlockPos blockpos = source.getBlockPos().offset(source.func_189992_e().getValue(BlockDispenser.FACING));
				BlockPumpkin blockpumpkin = (BlockPumpkin) Blocks.PUMPKIN;

				if (world.isAirBlock(blockpos) && blockpumpkin.canDispenserPlace(world, blockpos)) {
					if (!world.isRemote) {
						world.setBlockState(blockpos, blockpumpkin.getDefaultState(), 3);
					}

					--stack.stackSize;
				} else {
					ItemStack itemstack = ItemArmor.dispenseArmor(source, stack);

					if (itemstack == null) {
						this.succeeded = false;
					}
				}

				return stack;
			}

			@Override
			protected void playDispenseSound(IBlockSource source) {
				if (this.succeeded) {
					source.getWorld().playEvent(1000, source.getBlockPos(), 0);
				} else {
					source.getWorld().playEvent(1001, source.getBlockPos(), 0);
				}
			}
		});
	}

	/**
	 * Registers blocks, items, stats, etc.
	 */
	public static void register() {
		if (!alreadyRegistered) {
			alreadyRegistered = true;

			if (LOGGER.isDebugEnabled()) {
				redirectOutputToLog();
			}

			SoundEvent.registerSounds();
			Block.registerBlocks();
			BlockFire.init();
			Potion.registerPotions();
			Enchantment.registerEnchantments();
			Item.registerItems();
			PotionType.registerPotionTypes();
			PotionHelper.init();
			StatList.init();
			Biome.registerBiomes();
			registerDispenserBehaviors();
		}
	}

	/**
	 * redirect standard streams to logger
	 */
	private static void redirectOutputToLog() {
		System.setErr(new LoggingPrintStream("STDERR", System.err));
		System.setOut(new LoggingPrintStream("STDOUT", SYSOUT));
	}

	public static void printToSYSOUT(String message) {
		SYSOUT.println(message);
	}

	public static class BehaviorDispenseBoat extends BehaviorDefaultDispenseItem {
		private final BehaviorDefaultDispenseItem dispenseBehavior = new BehaviorDefaultDispenseItem();
		private final EntityBoat.Type boatType;

		public BehaviorDispenseBoat(EntityBoat.Type boatTypeIn) {
			this.boatType = boatTypeIn;
		}

		@Override
		public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
			EnumFacing enumfacing = source.func_189992_e().getValue(BlockDispenser.FACING);
			World world = source.getWorld();
			double d0 = source.getX() + enumfacing.getFrontOffsetX() * 1.125F;
			double d1 = source.getY() + enumfacing.getFrontOffsetY() * 1.125F;
			double d2 = source.getZ() + enumfacing.getFrontOffsetZ() * 1.125F;
			BlockPos blockpos = source.getBlockPos().offset(enumfacing);
			Material material = world.getBlockState(blockpos).getMaterial();
			double d3;

			if (Material.WATER.equals(material)) {
				d3 = 1.0D;
			} else {
				if (!Material.AIR.equals(material) || !Material.WATER.equals(world.getBlockState(blockpos.down()).getMaterial())) { return this.dispenseBehavior.dispense(source, stack); }

				d3 = 0.0D;
			}

			EntityBoat entityboat = new EntityBoat(world, d0, d1 + d3, d2);
			entityboat.setBoatType(this.boatType);
			entityboat.rotationYaw = enumfacing.getOpposite().getHorizontalAngle();
			world.spawnEntityInWorld(entityboat);
			stack.splitStack(1);
			return stack;
		}

		@Override
		protected void playDispenseSound(IBlockSource source) {
			source.getWorld().playEvent(1000, source.getBlockPos(), 0);
		}
	}
}
