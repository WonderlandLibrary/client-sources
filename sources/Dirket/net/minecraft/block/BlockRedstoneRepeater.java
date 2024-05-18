package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneRepeater extends BlockRedstoneDiode {
	public static final PropertyBool LOCKED = PropertyBool.create("locked");
	public static final PropertyInteger DELAY = PropertyInteger.create("delay", 1, 4);

	protected BlockRedstoneRepeater(boolean powered) {
		super(powered);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DELAY, Integer.valueOf(1)).withProperty(LOCKED, Boolean.valueOf(false)));
	}

	/**
	 * Gets the localized name of this block. Used for the statistics page.
	 */
	@Override
	public String getLocalizedName() {
		return I18n.translateToLocal("item.diode.name");
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(LOCKED, Boolean.valueOf(this.isLocked(worldIn, pos, state)));
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY,
			float hitZ) {
		if (!playerIn.capabilities.allowEdit) {
			return false;
		} else {
			worldIn.setBlockState(pos, state.cycleProperty(DELAY), 3);
			return true;
		}
	}

	@Override
	protected int getDelay(IBlockState state) {
		return state.getValue(DELAY).intValue() * 2;
	}

	@Override
	protected IBlockState getPoweredState(IBlockState unpoweredState) {
		Integer integer = unpoweredState.getValue(DELAY);
		Boolean obool = unpoweredState.getValue(LOCKED);
		EnumFacing enumfacing = unpoweredState.getValue(FACING);
		return Blocks.POWERED_REPEATER.getDefaultState().withProperty(FACING, enumfacing).withProperty(DELAY, integer).withProperty(LOCKED, obool);
	}

	@Override
	protected IBlockState getUnpoweredState(IBlockState poweredState) {
		Integer integer = poweredState.getValue(DELAY);
		Boolean obool = poweredState.getValue(LOCKED);
		EnumFacing enumfacing = poweredState.getValue(FACING);
		return Blocks.UNPOWERED_REPEATER.getDefaultState().withProperty(FACING, enumfacing).withProperty(DELAY, integer).withProperty(LOCKED, obool);
	}

	@Override
	@Nullable

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.REPEATER;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Items.REPEATER);
	}

	@Override
	public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
		return this.getPowerOnSides(worldIn, pos, state) > 0;
	}

	@Override
	protected boolean isAlternateInput(IBlockState state) {
		return isDiode(state);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (this.isRepeaterPowered) {
			EnumFacing enumfacing = stateIn.getValue(FACING);
			double d0 = pos.getX() + 0.5F + ((rand.nextFloat() - 0.5F) * 0.2D);
			double d1 = pos.getY() + 0.4F + ((rand.nextFloat() - 0.5F) * 0.2D);
			double d2 = pos.getZ() + 0.5F + ((rand.nextFloat() - 0.5F) * 0.2D);
			float f = -5.0F;

			if (rand.nextBoolean()) {
				f = (stateIn.getValue(DELAY).intValue() * 2) - 1;
			}

			f = f / 16.0F;
			double d3 = f * enumfacing.getFrontOffsetX();
			double d4 = f * enumfacing.getFrontOffsetZ();
			worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		this.notifyNeighbors(worldIn, pos, state);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(LOCKED, Boolean.valueOf(false)).withProperty(DELAY, Integer.valueOf(1 + (meta >> 2)));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();
		i = i | ((state.getValue(DELAY).intValue() - 1) << 2);
		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, DELAY, LOCKED });
	}
}
