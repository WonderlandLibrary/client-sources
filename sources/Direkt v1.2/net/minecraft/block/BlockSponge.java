package net.minecraft.block;

import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class BlockSponge extends Block {
	public static final PropertyBool WET = PropertyBool.create("wet");

	protected BlockSponge() {
		super(Material.SPONGE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(WET, Boolean.valueOf(false)));
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	/**
	 * Gets the localized name of this block. Used for the statistics page.
	 */
	@Override
	public String getLocalizedName() {
		return I18n.translateToLocal(this.getUnlocalizedName() + ".dry.name");
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns the metadata of the dropped item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(WET).booleanValue() ? 1 : 0;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.tryAbsorb(worldIn, pos, state);
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		this.tryAbsorb(p_189540_2_, p_189540_3_, p_189540_1_);
		super.func_189540_a(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_);
	}

	protected void tryAbsorb(World worldIn, BlockPos pos, IBlockState state) {
		if (!state.getValue(WET).booleanValue() && this.absorb(worldIn, pos)) {
			worldIn.setBlockState(pos, state.withProperty(WET, Boolean.valueOf(true)), 2);
			worldIn.playEvent(2001, pos, Block.getIdFromBlock(Blocks.WATER));
		}
	}

	private boolean absorb(World worldIn, BlockPos pos) {
		Queue<Tuple<BlockPos, Integer>> queue = Lists.<Tuple<BlockPos, Integer>> newLinkedList();
		List<BlockPos> list = Lists.<BlockPos> newArrayList();
		queue.add(new Tuple(pos, Integer.valueOf(0)));
		int i = 0;

		while (!((Queue) queue).isEmpty()) {
			Tuple<BlockPos, Integer> tuple = queue.poll();
			BlockPos blockpos = tuple.getFirst();
			int j = tuple.getSecond().intValue();

			for (EnumFacing enumfacing : EnumFacing.values()) {
				BlockPos blockpos1 = blockpos.offset(enumfacing);

				if (worldIn.getBlockState(blockpos1).getMaterial() == Material.WATER) {
					worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 2);
					list.add(blockpos1);
					++i;

					if (j < 6) {
						queue.add(new Tuple(blockpos1, Integer.valueOf(j + 1)));
					}
				}
			}

			if (i > 64) {
				break;
			}
		}

		for (BlockPos blockpos2 : list) {
			worldIn.notifyNeighborsOfStateChange(blockpos2, Blocks.AIR);
		}

		return i > 0;
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 1));
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(WET, Boolean.valueOf((meta & 1) == 1));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(WET).booleanValue() ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { WET });
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(WET).booleanValue()) {
			EnumFacing enumfacing = EnumFacing.random(rand);

			if ((enumfacing != EnumFacing.UP) && !worldIn.getBlockState(pos.offset(enumfacing)).isFullyOpaque()) {
				double d0 = pos.getX();
				double d1 = pos.getY();
				double d2 = pos.getZ();

				if (enumfacing == EnumFacing.DOWN) {
					d1 = d1 - 0.05D;
					d0 += rand.nextDouble();
					d2 += rand.nextDouble();
				} else {
					d1 = d1 + (rand.nextDouble() * 0.8D);

					if (enumfacing.getAxis() == EnumFacing.Axis.X) {
						d2 += rand.nextDouble();

						if (enumfacing == EnumFacing.EAST) {
							++d0;
						} else {
							d0 += 0.05D;
						}
					} else {
						d0 += rand.nextDouble();

						if (enumfacing == EnumFacing.SOUTH) {
							++d2;
						} else {
							d2 += 0.05D;
						}
					}
				}

				worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
	}
}
