package net.minecraft.block;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockPistonStructureHelper;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonBase extends BlockDirectional {
	public static final PropertyBool EXTENDED = PropertyBool.create("extended");
	protected static final AxisAlignedBB PISTON_BASE_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
	protected static final AxisAlignedBB PISTON_BASE_WEST_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB PISTON_BASE_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
	protected static final AxisAlignedBB PISTON_BASE_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB PISTON_BASE_UP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
	protected static final AxisAlignedBB PISTON_BASE_DOWN_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);

	/** This piston is the sticky one? */
	private final boolean isSticky;

	public BlockPistonBase(boolean isSticky) {
		super(Material.PISTON);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(EXTENDED, Boolean.valueOf(false)));
		this.isSticky = isSticky;
		this.setSoundType(SoundType.STONE);
		this.setHardness(0.5F);
		this.setCreativeTab(CreativeTabs.REDSTONE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if (state.getValue(EXTENDED).booleanValue()) {
			switch (state.getValue(FACING)) {
			case DOWN:
				return PISTON_BASE_DOWN_AABB;

			case UP:
			default:
				return PISTON_BASE_UP_AABB;

			case NORTH:
				return PISTON_BASE_NORTH_AABB;

			case SOUTH:
				return PISTON_BASE_SOUTH_AABB;

			case WEST:
				return PISTON_BASE_WEST_AABB;

			case EAST:
				return PISTON_BASE_EAST_AABB;
			}
		} else {
			return FULL_BLOCK_AABB;
		}
	}

	/**
	 * Checks if an IBlockState represents a block that is opaque and a full cube.
	 */
	@Override
	public boolean isFullyOpaque(IBlockState state) {
		return !state.getValue(EXTENDED).booleanValue() || (state.getValue(FACING) == EnumFacing.DOWN);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getBoundingBox(worldIn, pos));
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);

		if (!worldIn.isRemote) {
			this.checkForMove(worldIn, pos, state);
		}
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		if (!p_189540_2_.isRemote) {
			this.checkForMove(p_189540_2_, p_189540_3_, p_189540_1_);
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote && (worldIn.getTileEntity(pos) == null)) {
			this.checkForMove(worldIn, pos, state);
		}
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, getFacingFromEntity(pos, placer)).withProperty(EXTENDED, Boolean.valueOf(false));
	}

	private void checkForMove(World worldIn, BlockPos pos, IBlockState state) {
		EnumFacing enumfacing = state.getValue(FACING);
		boolean flag = this.shouldBeExtended(worldIn, pos, enumfacing);

		if (flag && !state.getValue(EXTENDED).booleanValue()) {
			if ((new BlockPistonStructureHelper(worldIn, pos, enumfacing, true)).canMove()) {
				worldIn.addBlockEvent(pos, this, 0, enumfacing.getIndex());
			}
		} else if (!flag && state.getValue(EXTENDED).booleanValue()) {
			worldIn.addBlockEvent(pos, this, 1, enumfacing.getIndex());
		}
	}

	private boolean shouldBeExtended(World worldIn, BlockPos pos, EnumFacing facing) {
		for (EnumFacing enumfacing : EnumFacing.values()) {
			if ((enumfacing != facing) && worldIn.isSidePowered(pos.offset(enumfacing), enumfacing)) { return true; }
		}

		if (worldIn.isSidePowered(pos, EnumFacing.DOWN)) {
			return true;
		} else {
			BlockPos blockpos = pos.up();

			for (EnumFacing enumfacing1 : EnumFacing.values()) {
				if ((enumfacing1 != EnumFacing.DOWN) && worldIn.isSidePowered(blockpos.offset(enumfacing1), enumfacing1)) { return true; }
			}

			return false;
		}
	}

	@Override
	public boolean func_189539_a(IBlockState p_189539_1_, World p_189539_2_, BlockPos p_189539_3_, int p_189539_4_, int p_189539_5_) {
		EnumFacing enumfacing = p_189539_1_.getValue(FACING);

		if (!p_189539_2_.isRemote) {
			boolean flag = this.shouldBeExtended(p_189539_2_, p_189539_3_, enumfacing);

			if (flag && (p_189539_4_ == 1)) {
				p_189539_2_.setBlockState(p_189539_3_, p_189539_1_.withProperty(EXTENDED, Boolean.valueOf(true)), 2);
				return false;
			}

			if (!flag && (p_189539_4_ == 0)) { return false; }
		}

		if (p_189539_4_ == 0) {
			if (!this.doMove(p_189539_2_, p_189539_3_, enumfacing, true)) { return false; }

			p_189539_2_.setBlockState(p_189539_3_, p_189539_1_.withProperty(EXTENDED, Boolean.valueOf(true)), 2);
			p_189539_2_.playSound((EntityPlayer) null, p_189539_3_, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, (p_189539_2_.rand.nextFloat() * 0.25F) + 0.6F);
		} else if (p_189539_4_ == 1) {
			TileEntity tileentity1 = p_189539_2_.getTileEntity(p_189539_3_.offset(enumfacing));

			if (tileentity1 instanceof TileEntityPiston) {
				((TileEntityPiston) tileentity1).clearPistonTileEntity();
			}

			p_189539_2_.setBlockState(p_189539_3_, Blocks.PISTON_EXTENSION.getDefaultState().withProperty(BlockPistonMoving.FACING, enumfacing).withProperty(BlockPistonMoving.TYPE,
					this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
			p_189539_2_.setTileEntity(p_189539_3_, BlockPistonMoving.createTilePiston(this.getStateFromMeta(p_189539_5_), enumfacing, false, true));

			if (this.isSticky) {
				BlockPos blockpos = p_189539_3_.add(enumfacing.getFrontOffsetX() * 2, enumfacing.getFrontOffsetY() * 2, enumfacing.getFrontOffsetZ() * 2);
				IBlockState iblockstate = p_189539_2_.getBlockState(blockpos);
				Block block = iblockstate.getBlock();
				boolean flag1 = false;

				if (block == Blocks.PISTON_EXTENSION) {
					TileEntity tileentity = p_189539_2_.getTileEntity(blockpos);

					if (tileentity instanceof TileEntityPiston) {
						TileEntityPiston tileentitypiston = (TileEntityPiston) tileentity;

						if ((tileentitypiston.getFacing() == enumfacing) && tileentitypiston.isExtending()) {
							tileentitypiston.clearPistonTileEntity();
							flag1 = true;
						}
					}
				}

				if (!flag1 && (iblockstate.getMaterial() != Material.AIR) && canPush(iblockstate, p_189539_2_, blockpos, enumfacing.getOpposite(), false)
						&& ((iblockstate.getMobilityFlag() == EnumPushReaction.NORMAL) || (block == Blocks.PISTON) || (block == Blocks.STICKY_PISTON))) {
					this.doMove(p_189539_2_, p_189539_3_, enumfacing, false);
				}
			} else {
				p_189539_2_.setBlockToAir(p_189539_3_.offset(enumfacing));
			}

			p_189539_2_.playSound((EntityPlayer) null, p_189539_3_, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, (p_189539_2_.rand.nextFloat() * 0.15F) + 0.6F);
		}

		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Nullable
	public static EnumFacing getFacing(int meta) {
		int i = meta & 7;
		return i > 5 ? null : EnumFacing.getFront(i);
	}

	public static EnumFacing getFacingFromEntity(BlockPos pos, EntityLivingBase p_185647_1_) {
		if ((MathHelper.abs((float) p_185647_1_.posX - pos.getX()) < 2.0F) && (MathHelper.abs((float) p_185647_1_.posZ - pos.getZ()) < 2.0F)) {
			double d0 = p_185647_1_.posY + p_185647_1_.getEyeHeight();

			if ((d0 - pos.getY()) > 2.0D) { return EnumFacing.UP; }

			if ((pos.getY() - d0) > 0.0D) { return EnumFacing.DOWN; }
		}

		return p_185647_1_.getHorizontalFacing().getOpposite();
	}

	/**
	 * Checks if the piston can push the given BlockState.
	 */
	public static boolean canPush(IBlockState blockStateIn, World worldIn, BlockPos pos, EnumFacing facing, boolean destroyBlocks) {
		Block block = blockStateIn.getBlock();

		if (block == Blocks.OBSIDIAN) {
			return false;
		} else if (!worldIn.getWorldBorder().contains(pos)) {
			return false;
		} else if ((pos.getY() >= 0) && ((facing != EnumFacing.DOWN) || (pos.getY() != 0))) {
			if ((pos.getY() <= (worldIn.getHeight() - 1)) && ((facing != EnumFacing.UP) || (pos.getY() != (worldIn.getHeight() - 1)))) {
				if ((block != Blocks.PISTON) && (block != Blocks.STICKY_PISTON)) {
					if (blockStateIn.getBlockHardness(worldIn, pos) == -1.0F) { return false; }

					if (blockStateIn.getMobilityFlag() == EnumPushReaction.BLOCK) { return false; }

					if (blockStateIn.getMobilityFlag() == EnumPushReaction.DESTROY) { return destroyBlocks; }
				} else if (blockStateIn.getValue(EXTENDED).booleanValue()) { return false; }

				return !block.hasTileEntity();
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean doMove(World worldIn, BlockPos pos, EnumFacing direction, boolean extending) {
		if (!extending) {
			worldIn.setBlockToAir(pos.offset(direction));
		}

		BlockPistonStructureHelper blockpistonstructurehelper = new BlockPistonStructureHelper(worldIn, pos, direction, extending);

		if (!blockpistonstructurehelper.canMove()) {
			return false;
		} else {
			List<BlockPos> list = blockpistonstructurehelper.getBlocksToMove();
			List<IBlockState> list1 = Lists.<IBlockState> newArrayList();

			for (int i = 0; i < list.size(); ++i) {
				BlockPos blockpos = list.get(i);
				list1.add(worldIn.getBlockState(blockpos).getActualState(worldIn, blockpos));
			}

			List<BlockPos> list2 = blockpistonstructurehelper.getBlocksToDestroy();
			int k = list.size() + list2.size();
			IBlockState[] aiblockstate = new IBlockState[k];
			EnumFacing enumfacing = extending ? direction : direction.getOpposite();

			for (int j = list2.size() - 1; j >= 0; --j) {
				BlockPos blockpos1 = list2.get(j);
				IBlockState iblockstate = worldIn.getBlockState(blockpos1);
				iblockstate.getBlock().dropBlockAsItem(worldIn, blockpos1, iblockstate, 0);
				worldIn.setBlockToAir(blockpos1);
				--k;
				aiblockstate[k] = iblockstate;
			}

			for (int l = list.size() - 1; l >= 0; --l) {
				BlockPos blockpos3 = list.get(l);
				IBlockState iblockstate2 = worldIn.getBlockState(blockpos3);
				worldIn.setBlockState(blockpos3, Blocks.AIR.getDefaultState(), 2);
				blockpos3 = blockpos3.offset(enumfacing);
				worldIn.setBlockState(blockpos3, Blocks.PISTON_EXTENSION.getDefaultState().withProperty(FACING, direction), 4);
				worldIn.setTileEntity(blockpos3, BlockPistonMoving.createTilePiston(list1.get(l), direction, extending, false));
				--k;
				aiblockstate[k] = iblockstate2;
			}

			BlockPos blockpos2 = pos.offset(direction);

			if (extending) {
				BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
				IBlockState iblockstate3 = Blocks.PISTON_HEAD.getDefaultState().withProperty(BlockDirectional.FACING, direction).withProperty(BlockPistonExtension.TYPE,
						blockpistonextension$enumpistontype);
				IBlockState iblockstate1 = Blocks.PISTON_EXTENSION.getDefaultState().withProperty(BlockPistonMoving.FACING, direction).withProperty(BlockPistonMoving.TYPE,
						this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
				worldIn.setBlockState(blockpos2, iblockstate1, 4);
				worldIn.setTileEntity(blockpos2, BlockPistonMoving.createTilePiston(iblockstate3, direction, true, false));
			}

			for (int i1 = list2.size() - 1; i1 >= 0; --i1) {
				worldIn.notifyNeighborsOfStateChange(list2.get(i1), aiblockstate[k++].getBlock());
			}

			for (int j1 = list.size() - 1; j1 >= 0; --j1) {
				worldIn.notifyNeighborsOfStateChange(list.get(j1), aiblockstate[k++].getBlock());
			}

			if (extending) {
				worldIn.notifyNeighborsOfStateChange(blockpos2, Blocks.PISTON_HEAD);
				worldIn.notifyNeighborsOfStateChange(pos, this);
			}

			return true;
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(EXTENDED, Boolean.valueOf((meta & 8) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getIndex();

		if (state.getValue(EXTENDED).booleanValue()) {
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
		return new BlockStateContainer(this, new IProperty[] { FACING, EXTENDED });
	}
}
