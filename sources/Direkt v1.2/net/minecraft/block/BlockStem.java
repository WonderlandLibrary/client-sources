package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStem extends BlockBush implements IGrowable {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
	public static final PropertyDirection FACING = BlockTorch.FACING;
	private final Block crop;
	protected static final AxisAlignedBB[] STEM_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.125D, 0.625D),
			new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.25D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.375D, 0.625D),
			new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.625D, 0.625D),
			new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.75D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.875D, 0.625D),
			new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D) };

	protected BlockStem(Block crop) {
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)).withProperty(FACING, EnumFacing.UP));
		this.crop = crop;
		this.setTickRandomly(true);
		this.setCreativeTab((CreativeTabs) null);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return STEM_AABB[state.getValue(AGE).intValue()];
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		int i = state.getValue(AGE).intValue();
		state = state.withProperty(FACING, EnumFacing.UP);

		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
			if ((worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop) && (i == 7)) {
				state = state.withProperty(FACING, enumfacing);
				break;
			}
		}

		return state;
	}

	/**
	 * Return true if the block can sustain a Bush
	 */
	@Override
	protected boolean canSustainBush(IBlockState state) {
		return state.getBlock() == Blocks.FARMLAND;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);

		if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
			float f = BlockCrops.getGrowthChance(this, worldIn, pos);

			if (rand.nextInt((int) (25.0F / f) + 1) == 0) {
				int i = state.getValue(AGE).intValue();

				if (i < 7) {
					state = state.withProperty(AGE, Integer.valueOf(i + 1));
					worldIn.setBlockState(pos, state, 2);
				} else {
					for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
						if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop) { return; }
					}

					pos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(rand));
					Block block = worldIn.getBlockState(pos.down()).getBlock();

					if ((worldIn.getBlockState(pos).getBlock().blockMaterial == Material.AIR) && ((block == Blocks.FARMLAND) || (block == Blocks.DIRT) || (block == Blocks.GRASS))) {
						worldIn.setBlockState(pos, this.crop.getDefaultState());
					}
				}
			}
		}
	}

	public void growStem(World worldIn, BlockPos pos, IBlockState state) {
		int i = state.getValue(AGE).intValue() + MathHelper.getRandomIntegerInRange(worldIn.rand, 2, 5);
		worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(Math.min(7, i))), 2);
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);

		if (!worldIn.isRemote) {
			Item item = this.getSeedItem();

			if (item != null) {
				int i = state.getValue(AGE).intValue();

				for (int j = 0; j < 3; ++j) {
					if (worldIn.rand.nextInt(15) <= i) {
						spawnAsEntity(worldIn, pos, new ItemStack(item));
					}
				}
			}
		}
	}

	@Nullable
	protected Item getSeedItem() {
		return this.crop == Blocks.PUMPKIN ? Items.PUMPKIN_SEEDS : (this.crop == Blocks.MELON_BLOCK ? Items.MELON_SEEDS : null);
	}

	@Override
	@Nullable

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}

	@Override
	@Nullable
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		Item item = this.getSeedItem();
		return item == null ? null : new ItemStack(item);
	}

	/**
	 * Whether this IGrowable can grow
	 */
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return state.getValue(AGE).intValue() != 7;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		this.growStem(worldIn, pos, state);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, Integer.valueOf(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(AGE).intValue();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE, FACING });
	}
}
