package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryDefaulted;
import net.minecraft.world.World;

public class BlockDispenser extends BlockContainer {
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	public static final PropertyBool TRIGGERED = PropertyBool.create("triggered");
	public static final RegistryDefaulted<Item, IBehaviorDispenseItem> DISPENSE_BEHAVIOR_REGISTRY = new RegistryDefaulted(new BehaviorDefaultDispenseItem());
	protected Random rand = new Random();

	protected BlockDispenser() {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TRIGGERED, Boolean.valueOf(false)));
		this.setCreativeTab(CreativeTabs.REDSTONE);
	}

	/**
	 * How many world ticks before ticking
	 */
	@Override
	public int tickRate(World worldIn) {
		return 4;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.setDefaultDirection(worldIn, pos, state);
	}

	private void setDefaultDirection(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			EnumFacing enumfacing = state.getValue(FACING);
			boolean flag = worldIn.getBlockState(pos.north()).isFullBlock();
			boolean flag1 = worldIn.getBlockState(pos.south()).isFullBlock();

			if ((enumfacing == EnumFacing.NORTH) && flag && !flag1) {
				enumfacing = EnumFacing.SOUTH;
			} else if ((enumfacing == EnumFacing.SOUTH) && flag1 && !flag) {
				enumfacing = EnumFacing.NORTH;
			} else {
				boolean flag2 = worldIn.getBlockState(pos.west()).isFullBlock();
				boolean flag3 = worldIn.getBlockState(pos.east()).isFullBlock();

				if ((enumfacing == EnumFacing.WEST) && flag2 && !flag3) {
					enumfacing = EnumFacing.EAST;
				} else if ((enumfacing == EnumFacing.EAST) && flag3 && !flag2) {
					enumfacing = EnumFacing.WEST;
				}
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(TRIGGERED, Boolean.valueOf(false)), 2);
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY,
			float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityDispenser) {
				playerIn.displayGUIChest((TileEntityDispenser) tileentity);

				if (tileentity instanceof TileEntityDropper) {
					playerIn.addStat(StatList.DROPPER_INSPECTED);
				} else {
					playerIn.addStat(StatList.DISPENSER_INSPECTED);
				}
			}

			return true;
		}
	}

	protected void dispense(World worldIn, BlockPos pos) {
		BlockSourceImpl blocksourceimpl = new BlockSourceImpl(worldIn, pos);
		TileEntityDispenser tileentitydispenser = (TileEntityDispenser) blocksourceimpl.getBlockTileEntity();

		if (tileentitydispenser != null) {
			int i = tileentitydispenser.getDispenseSlot();

			if (i < 0) {
				worldIn.playEvent(1001, pos, 0);
			} else {
				ItemStack itemstack = tileentitydispenser.getStackInSlot(i);
				IBehaviorDispenseItem ibehaviordispenseitem = this.getBehavior(itemstack);

				if (ibehaviordispenseitem != IBehaviorDispenseItem.DEFAULT_BEHAVIOR) {
					ItemStack itemstack1 = ibehaviordispenseitem.dispense(blocksourceimpl, itemstack);
					tileentitydispenser.setInventorySlotContents(i, itemstack1.stackSize <= 0 ? null : itemstack1);
				}
			}
		}
	}

	protected IBehaviorDispenseItem getBehavior(@Nullable ItemStack stack) {
		return DISPENSE_BEHAVIOR_REGISTRY.getObject(stack == null ? null : stack.getItem());
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		boolean flag = p_189540_2_.isBlockPowered(p_189540_3_) || p_189540_2_.isBlockPowered(p_189540_3_.up());
		boolean flag1 = p_189540_1_.getValue(TRIGGERED).booleanValue();

		if (flag && !flag1) {
			p_189540_2_.scheduleUpdate(p_189540_3_, this, this.tickRate(p_189540_2_));
			p_189540_2_.setBlockState(p_189540_3_, p_189540_1_.withProperty(TRIGGERED, Boolean.valueOf(true)), 4);
		} else if (!flag && flag1) {
			p_189540_2_.setBlockState(p_189540_3_, p_189540_1_.withProperty(TRIGGERED, Boolean.valueOf(false)), 4);
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			this.dispense(worldIn, pos);
		}
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDispenser();
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer)).withProperty(TRIGGERED, Boolean.valueOf(false));
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer)), 2);

		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityDispenser) {
				((TileEntityDispenser) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TileEntityDispenser) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityDispenser) tileentity);
			worldIn.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(worldIn, pos, state);
	}

	/**
	 * Get the position where the dispenser at the given Coordinates should dispense to.
	 */
	public static IPosition getDispensePosition(IBlockSource coords) {
		EnumFacing enumfacing = coords.func_189992_e().getValue(FACING);
		double d0 = coords.getX() + (0.7D * enumfacing.getFrontOffsetX());
		double d1 = coords.getY() + (0.7D * enumfacing.getFrontOffsetY());
		double d2 = coords.getZ() + (0.7D * enumfacing.getFrontOffsetZ());
		return new PositionImpl(d0, d1, d2);
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	/**
	 * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
	 */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7)).withProperty(TRIGGERED, Boolean.valueOf((meta & 8) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getIndex();

		if (state.getValue(TRIGGERED).booleanValue()) {
			i |= 8;
		}

		return i;
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, TRIGGERED });
	}
}
