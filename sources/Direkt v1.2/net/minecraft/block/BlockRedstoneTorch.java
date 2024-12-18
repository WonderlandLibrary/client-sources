package net.minecraft.block;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneTorch extends BlockTorch {
	private static final Map<World, List<BlockRedstoneTorch.Toggle>> toggles = Maps.<World, List<BlockRedstoneTorch.Toggle>> newHashMap();
	private final boolean isOn;

	private boolean isBurnedOut(World worldIn, BlockPos pos, boolean turnOff) {
		if (!toggles.containsKey(worldIn)) {
			toggles.put(worldIn, Lists.<BlockRedstoneTorch.Toggle> newArrayList());
		}

		List<BlockRedstoneTorch.Toggle> list = toggles.get(worldIn);

		if (turnOff) {
			list.add(new BlockRedstoneTorch.Toggle(pos, worldIn.getTotalWorldTime()));
		}

		int i = 0;

		for (int j = 0; j < list.size(); ++j) {
			BlockRedstoneTorch.Toggle blockredstonetorch$toggle = list.get(j);

			if (blockredstonetorch$toggle.pos.equals(pos)) {
				++i;

				if (i >= 8) { return true; }
			}
		}

		return false;
	}

	protected BlockRedstoneTorch(boolean isOn) {
		this.isOn = isOn;
		this.setTickRandomly(true);
		this.setCreativeTab((CreativeTabs) null);
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World worldIn) {
		return 2;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (this.isOn) {
			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if (this.isOn) {
			for (EnumFacing enumfacing : EnumFacing.values()) {
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
			}
		}
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return this.isOn && (blockState.getValue(FACING) != side) ? 15 : 0;
	}

	private boolean shouldBeOff(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING).getOpposite();
		return worldIn.isSidePowered(pos.offset(enumfacing), enumfacing);
	}

	/**
	 * Called randomly when setTickRandomly is set to true (used by e.g. crops to grow, etc.)
	 */
	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		boolean flag = this.shouldBeOff(worldIn, pos, state);
		List<BlockRedstoneTorch.Toggle> list = toggles.get(worldIn);

		while ((list != null) && !list.isEmpty() && ((worldIn.getTotalWorldTime() - list.get(0).time) > 60L)) {
			list.remove(0);
		}

		if (this.isOn) {
			if (flag) {
				worldIn.setBlockState(pos, Blocks.UNLIT_REDSTONE_TORCH.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);

				if (this.isBurnedOut(worldIn, pos, true)) {
					worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.5F,
							2.6F + ((worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F));

					for (int i = 0; i < 5; ++i) {
						double d0 = pos.getX() + (rand.nextDouble() * 0.6D) + 0.2D;
						double d1 = pos.getY() + (rand.nextDouble() * 0.6D) + 0.2D;
						double d2 = pos.getZ() + (rand.nextDouble() * 0.6D) + 0.2D;
						worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
					}

					worldIn.scheduleUpdate(pos, worldIn.getBlockState(pos).getBlock(), 160);
				}
			}
		} else if (!flag && !this.isBurnedOut(worldIn, pos, false)) {
			worldIn.setBlockState(pos, Blocks.REDSTONE_TORCH.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
		}
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		if (!this.onNeighborChangeInternal(p_189540_2_, p_189540_3_, p_189540_1_)) {
			if (this.isOn == this.shouldBeOff(p_189540_2_, p_189540_3_, p_189540_1_)) {
				p_189540_2_.scheduleUpdate(p_189540_3_, this, this.tickRate(p_189540_2_));
			}
		}
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return side == EnumFacing.DOWN ? blockState.getWeakPower(blockAccess, pos, side) : 0;
	}

	@Override
	@Nullable

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Blocks.REDSTONE_TORCH);
	}

	/**
	 * Can this block provide power. Only wire currently seems to have this change based on its state.
	 */
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this.isOn) {
			double d0 = pos.getX() + 0.5D + ((rand.nextDouble() - 0.5D) * 0.2D);
			double d1 = pos.getY() + 0.7D + ((rand.nextDouble() - 0.5D) * 0.2D);
			double d2 = pos.getZ() + 0.5D + ((rand.nextDouble() - 0.5D) * 0.2D);
			EnumFacing enumfacing = stateIn.getValue(FACING);

			if (enumfacing.getAxis().isHorizontal()) {
				EnumFacing enumfacing1 = enumfacing.getOpposite();
				double d3 = 0.27D;
				d0 += 0.27D * enumfacing1.getFrontOffsetX();
				d1 += 0.22D;
				d2 += 0.27D * enumfacing1.getFrontOffsetZ();
			}

			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Blocks.REDSTONE_TORCH);
	}

	@Override
	public boolean isAssociatedBlock(Block other) {
		return (other == Blocks.UNLIT_REDSTONE_TORCH) || (other == Blocks.REDSTONE_TORCH);
	}

	static class Toggle {
		BlockPos pos;
		long time;

		public Toggle(BlockPos pos, long time) {
			this.pos = pos;
			this.time = time;
		}
	}
}
