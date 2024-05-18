package net.minecraft.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCauldron extends Block {
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);
	protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
	protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
	protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

	public BlockCauldron() {
		super(Material.IRON, MapColor.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(0)));
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
		addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FULL_BLOCK_AABB;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		int i = state.getValue(LEVEL).intValue();
		float f = pos.getY() + ((6.0F + 3 * i) / 16.0F);

		if (!worldIn.isRemote && entityIn.isBurning() && (i > 0) && (entityIn.getEntityBoundingBox().minY <= f)) {
			entityIn.extinguish();
			this.setWaterLevel(worldIn, pos, state, i - 1);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY,
			float hitZ) {
		if (heldItem == null) {
			return true;
		} else {
			int i = state.getValue(LEVEL).intValue();
			Item item = heldItem.getItem();

			if (item == Items.WATER_BUCKET) {
				if ((i < 3) && !worldIn.isRemote) {
					if (!playerIn.capabilities.isCreativeMode) {
						playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
					}

					playerIn.addStat(StatList.CAULDRON_FILLED);
					this.setWaterLevel(worldIn, pos, state, 3);
				}

				return true;
			} else if (item == Items.BUCKET) {
				if ((i == 3) && !worldIn.isRemote) {
					if (!playerIn.capabilities.isCreativeMode) {
						--heldItem.stackSize;

						if (heldItem.stackSize == 0) {
							playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
						} else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
							playerIn.dropItem(new ItemStack(Items.WATER_BUCKET), false);
						}
					}

					playerIn.addStat(StatList.CAULDRON_USED);
					this.setWaterLevel(worldIn, pos, state, 0);
				}

				return true;
			} else if (item == Items.GLASS_BOTTLE) {
				if ((i > 0) && !worldIn.isRemote) {
					if (!playerIn.capabilities.isCreativeMode) {
						ItemStack itemstack1 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
						playerIn.addStat(StatList.CAULDRON_USED);

						if (--heldItem.stackSize == 0) {
							playerIn.setHeldItem(hand, itemstack1);
						} else if (!playerIn.inventory.addItemStackToInventory(itemstack1)) {
							playerIn.dropItem(itemstack1, false);
						} else if (playerIn instanceof EntityPlayerMP) {
							((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
						}
					}

					this.setWaterLevel(worldIn, pos, state, i - 1);
				}

				return true;
			} else {
				if ((i > 0) && (item instanceof ItemArmor)) {
					ItemArmor itemarmor = (ItemArmor) item;

					if ((itemarmor.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER) && itemarmor.hasColor(heldItem) && !worldIn.isRemote) {
						itemarmor.removeColor(heldItem);
						this.setWaterLevel(worldIn, pos, state, i - 1);
						playerIn.addStat(StatList.ARMOR_CLEANED);
						return true;
					}
				}

				if ((i > 0) && (item instanceof ItemBanner)) {
					if ((TileEntityBanner.getPatterns(heldItem) > 0) && !worldIn.isRemote) {
						ItemStack itemstack = heldItem.copy();
						itemstack.stackSize = 1;
						TileEntityBanner.removeBannerData(itemstack);
						playerIn.addStat(StatList.BANNER_CLEANED);

						if (!playerIn.capabilities.isCreativeMode) {
							--heldItem.stackSize;
						}

						if (heldItem.stackSize == 0) {
							playerIn.setHeldItem(hand, itemstack);
						} else if (!playerIn.inventory.addItemStackToInventory(itemstack)) {
							playerIn.dropItem(itemstack, false);
						} else if (playerIn instanceof EntityPlayerMP) {
							((EntityPlayerMP) playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
						}

						if (!playerIn.capabilities.isCreativeMode) {
							this.setWaterLevel(worldIn, pos, state, i - 1);
						}
					}

					return true;
				} else {
					return false;
				}
			}
		}
	}

	public void setWaterLevel(World worldIn, BlockPos pos, IBlockState state, int level) {
		worldIn.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(MathHelper.clamp_int(level, 0, 3))), 2);
		worldIn.updateComparatorOutputLevel(pos, this);
	}

	/**
	 * Called similar to random ticks, but only when it is raining.
	 */
	@Override
	public void fillWithRain(World worldIn, BlockPos pos) {
		if (worldIn.rand.nextInt(20) == 1) {
			float f = worldIn.getBiomeGenForCoords(pos).getFloatTemperature(pos);

			if (worldIn.getBiomeProvider().getTemperatureAtHeight(f, pos.getY()) >= 0.15F) {
				IBlockState iblockstate = worldIn.getBlockState(pos);

				if (iblockstate.getValue(LEVEL).intValue() < 3) {
					worldIn.setBlockState(pos, iblockstate.cycleProperty(LEVEL), 2);
				}
			}
		}
	}

	@Override
	@Nullable

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.CAULDRON;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Items.CAULDRON);
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return blockState.getValue(LEVEL).intValue();
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(LEVEL).intValue();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { LEVEL });
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return true;
	}
}
