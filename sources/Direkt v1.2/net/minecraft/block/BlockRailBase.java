package net.minecraft.block;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRailBase extends Block {
	protected static final AxisAlignedBB FLAT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
	protected final boolean isPowered;

	public static boolean isRailBlock(World worldIn, BlockPos pos) {
		return isRailBlock(worldIn.getBlockState(pos));
	}

	public static boolean isRailBlock(IBlockState state) {
		Block block = state.getBlock();
		return (block == Blocks.RAIL) || (block == Blocks.GOLDEN_RAIL) || (block == Blocks.DETECTOR_RAIL) || (block == Blocks.ACTIVATOR_RAIL);
	}

	protected BlockRailBase(boolean isPowered) {
		super(Material.CIRCUITS);
		this.isPowered = isPowered;
		this.setCreativeTab(CreativeTabs.TRANSPORTATION);
	}

	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = state.getBlock() == this ? (BlockRailBase.EnumRailDirection) state.getValue(this.getShapeProperty()) : null;
		return (blockrailbase$enumraildirection != null) && blockrailbase$enumraildirection.isAscending() ? FULL_BLOCK_AABB : FLAT_AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isFullyOpaque();
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			state = this.updateDir(worldIn, pos, state, true);

			if (this.isPowered) {
				state.func_189546_a(worldIn, pos, this);
			}
		}
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		if (!p_189540_2_.isRemote) {
			BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = p_189540_1_.getValue(this.getShapeProperty());
			boolean flag = false;

			if (!p_189540_2_.getBlockState(p_189540_3_.down()).isFullyOpaque()) {
				flag = true;
			}

			if ((blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.ASCENDING_EAST) && !p_189540_2_.getBlockState(p_189540_3_.east()).isFullyOpaque()) {
				flag = true;
			} else if ((blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.ASCENDING_WEST) && !p_189540_2_.getBlockState(p_189540_3_.west()).isFullyOpaque()) {
				flag = true;
			} else if ((blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.ASCENDING_NORTH) && !p_189540_2_.getBlockState(p_189540_3_.north()).isFullyOpaque()) {
				flag = true;
			} else if ((blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.ASCENDING_SOUTH) && !p_189540_2_.getBlockState(p_189540_3_.south()).isFullyOpaque()) {
				flag = true;
			}

			if (flag && !p_189540_2_.isAirBlock(p_189540_3_)) {
				this.dropBlockAsItem(p_189540_2_, p_189540_3_, p_189540_1_, 0);
				p_189540_2_.setBlockToAir(p_189540_3_);
			} else {
				this.func_189541_b(p_189540_1_, p_189540_2_, p_189540_3_, p_189540_4_);
			}
		}
	}

	protected void func_189541_b(IBlockState p_189541_1_, World p_189541_2_, BlockPos p_189541_3_, Block p_189541_4_) {
	}

	protected IBlockState updateDir(World worldIn, BlockPos pos, IBlockState state, boolean p_176564_4_) {
		return worldIn.isRemote ? state : (new BlockRailBase.Rail(worldIn, pos, state)).place(worldIn.isBlockPowered(pos), p_176564_4_).getBlockState();
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.NORMAL;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);

		if (state.getValue(this.getShapeProperty()).isAscending()) {
			worldIn.notifyNeighborsOfStateChange(pos.up(), this);
		}

		if (this.isPowered) {
			worldIn.notifyNeighborsOfStateChange(pos, this);
			worldIn.notifyNeighborsOfStateChange(pos.down(), this);
		}
	}

	public abstract IProperty<BlockRailBase.EnumRailDirection> getShapeProperty();

	public static enum EnumRailDirection implements IStringSerializable {
		NORTH_SOUTH(0, "north_south"), EAST_WEST(1, "east_west"), ASCENDING_EAST(2, "ascending_east"), ASCENDING_WEST(3, "ascending_west"), ASCENDING_NORTH(4, "ascending_north"), ASCENDING_SOUTH(5,
				"ascending_south"), SOUTH_EAST(6, "south_east"), SOUTH_WEST(7, "south_west"), NORTH_WEST(8, "north_west"), NORTH_EAST(9, "north_east");

		private static final BlockRailBase.EnumRailDirection[] META_LOOKUP = new BlockRailBase.EnumRailDirection[values().length];
		private final int meta;
		private final String name;

		private EnumRailDirection(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public int getMetadata() {
			return this.meta;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public boolean isAscending() {
			return (this == ASCENDING_NORTH) || (this == ASCENDING_EAST) || (this == ASCENDING_SOUTH) || (this == ASCENDING_WEST);
		}

		public static BlockRailBase.EnumRailDirection byMetadata(int meta) {
			if ((meta < 0) || (meta >= META_LOOKUP.length)) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		@Override
		public String getName() {
			return this.name;
		}

		static {
			for (BlockRailBase.EnumRailDirection blockrailbase$enumraildirection : values()) {
				META_LOOKUP[blockrailbase$enumraildirection.getMetadata()] = blockrailbase$enumraildirection;
			}
		}
	}

	public class Rail {
		private final World world;
		private final BlockPos pos;
		private final BlockRailBase block;
		private IBlockState state;
		private final boolean isPowered;
		private final List<BlockPos> connectedRails = Lists.<BlockPos> newArrayList();

		public Rail(World worldIn, BlockPos pos, IBlockState state) {
			this.world = worldIn;
			this.pos = pos;
			this.state = state;
			this.block = (BlockRailBase) state.getBlock();
			BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = state.getValue(this.block.getShapeProperty());
			this.isPowered = this.block.isPowered;
			this.updateConnectedRails(blockrailbase$enumraildirection);
		}

		public List<BlockPos> getConnectedRails() {
			return this.connectedRails;
		}

		private void updateConnectedRails(BlockRailBase.EnumRailDirection railDirection) {
			this.connectedRails.clear();

			switch (railDirection) {
			case NORTH_SOUTH:
				this.connectedRails.add(this.pos.north());
				this.connectedRails.add(this.pos.south());
				break;

			case EAST_WEST:
				this.connectedRails.add(this.pos.west());
				this.connectedRails.add(this.pos.east());
				break;

			case ASCENDING_EAST:
				this.connectedRails.add(this.pos.west());
				this.connectedRails.add(this.pos.east().up());
				break;

			case ASCENDING_WEST:
				this.connectedRails.add(this.pos.west().up());
				this.connectedRails.add(this.pos.east());
				break;

			case ASCENDING_NORTH:
				this.connectedRails.add(this.pos.north().up());
				this.connectedRails.add(this.pos.south());
				break;

			case ASCENDING_SOUTH:
				this.connectedRails.add(this.pos.north());
				this.connectedRails.add(this.pos.south().up());
				break;

			case SOUTH_EAST:
				this.connectedRails.add(this.pos.east());
				this.connectedRails.add(this.pos.south());
				break;

			case SOUTH_WEST:
				this.connectedRails.add(this.pos.west());
				this.connectedRails.add(this.pos.south());
				break;

			case NORTH_WEST:
				this.connectedRails.add(this.pos.west());
				this.connectedRails.add(this.pos.north());
				break;

			case NORTH_EAST:
				this.connectedRails.add(this.pos.east());
				this.connectedRails.add(this.pos.north());
			}
		}

		private void removeSoftConnections() {
			for (int i = 0; i < this.connectedRails.size(); ++i) {
				BlockRailBase.Rail blockrailbase$rail = this.findRailAt(this.connectedRails.get(i));

				if ((blockrailbase$rail != null) && blockrailbase$rail.isConnectedToRail(this)) {
					this.connectedRails.set(i, blockrailbase$rail.pos);
				} else {
					this.connectedRails.remove(i--);
				}
			}
		}

		private boolean hasRailAt(BlockPos pos) {
			return BlockRailBase.isRailBlock(this.world, pos) || BlockRailBase.isRailBlock(this.world, pos.up()) || BlockRailBase.isRailBlock(this.world, pos.down());
		}

		@Nullable
		private BlockRailBase.Rail findRailAt(BlockPos pos) {
			IBlockState iblockstate = this.world.getBlockState(pos);

			if (BlockRailBase.isRailBlock(iblockstate)) {
				return BlockRailBase.this.new Rail(this.world, pos, iblockstate);
			} else {
				BlockPos lvt_2_1_ = pos.up();
				iblockstate = this.world.getBlockState(lvt_2_1_);

				if (BlockRailBase.isRailBlock(iblockstate)) {
					return BlockRailBase.this.new Rail(this.world, lvt_2_1_, iblockstate);
				} else {
					lvt_2_1_ = pos.down();
					iblockstate = this.world.getBlockState(lvt_2_1_);
					return BlockRailBase.isRailBlock(iblockstate) ? BlockRailBase.this.new Rail(this.world, lvt_2_1_, iblockstate) : null;
				}
			}
		}

		private boolean isConnectedToRail(BlockRailBase.Rail rail) {
			return this.isConnectedTo(rail.pos);
		}

		private boolean isConnectedTo(BlockPos posIn) {
			for (int i = 0; i < this.connectedRails.size(); ++i) {
				BlockPos blockpos = this.connectedRails.get(i);

				if ((blockpos.getX() == posIn.getX()) && (blockpos.getZ() == posIn.getZ())) { return true; }
			}

			return false;
		}

		protected int countAdjacentRails() {
			int i = 0;

			for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
				if (this.hasRailAt(this.pos.offset(enumfacing))) {
					++i;
				}
			}

			return i;
		}

		private boolean canConnectTo(BlockRailBase.Rail rail) {
			return this.isConnectedToRail(rail) || (this.connectedRails.size() != 2);
		}

		private void connectTo(BlockRailBase.Rail p_150645_1_) {
			this.connectedRails.add(p_150645_1_.pos);
			BlockPos blockpos = this.pos.north();
			BlockPos blockpos1 = this.pos.south();
			BlockPos blockpos2 = this.pos.west();
			BlockPos blockpos3 = this.pos.east();
			boolean flag = this.isConnectedTo(blockpos);
			boolean flag1 = this.isConnectedTo(blockpos1);
			boolean flag2 = this.isConnectedTo(blockpos2);
			boolean flag3 = this.isConnectedTo(blockpos3);
			BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;

			if (flag || flag1) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
			}

			if (flag2 || flag3) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
			}

			if (!this.isPowered) {
				if (flag1 && flag3 && !flag && !flag2) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
				}

				if (flag1 && flag2 && !flag && !flag3) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
				}

				if (flag && flag2 && !flag1 && !flag3) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
				}

				if (flag && flag3 && !flag1 && !flag2) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
				}
			}

			if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
				if (BlockRailBase.isRailBlock(this.world, blockpos.up())) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
				}

				if (BlockRailBase.isRailBlock(this.world, blockpos1.up())) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
				}
			}

			if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
				if (BlockRailBase.isRailBlock(this.world, blockpos3.up())) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
				}

				if (BlockRailBase.isRailBlock(this.world, blockpos2.up())) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
				}
			}

			if (blockrailbase$enumraildirection == null) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
			}

			this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);
			this.world.setBlockState(this.pos, this.state, 3);
		}

		private boolean hasNeighborRail(BlockPos p_180361_1_) {
			BlockRailBase.Rail blockrailbase$rail = this.findRailAt(p_180361_1_);

			if (blockrailbase$rail == null) {
				return false;
			} else {
				blockrailbase$rail.removeSoftConnections();
				return blockrailbase$rail.canConnectTo(this);
			}
		}

		public BlockRailBase.Rail place(boolean p_180364_1_, boolean p_180364_2_) {
			BlockPos blockpos = this.pos.north();
			BlockPos blockpos1 = this.pos.south();
			BlockPos blockpos2 = this.pos.west();
			BlockPos blockpos3 = this.pos.east();
			boolean flag = this.hasNeighborRail(blockpos);
			boolean flag1 = this.hasNeighborRail(blockpos1);
			boolean flag2 = this.hasNeighborRail(blockpos2);
			boolean flag3 = this.hasNeighborRail(blockpos3);
			BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;

			if ((flag || flag1) && !flag2 && !flag3) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
			}

			if ((flag2 || flag3) && !flag && !flag1) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
			}

			if (!this.isPowered) {
				if (flag1 && flag3 && !flag && !flag2) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
				}

				if (flag1 && flag2 && !flag && !flag3) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
				}

				if (flag && flag2 && !flag1 && !flag3) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
				}

				if (flag && flag3 && !flag1 && !flag2) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
				}
			}

			if (blockrailbase$enumraildirection == null) {
				if (flag || flag1) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
				}

				if (flag2 || flag3) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
				}

				if (!this.isPowered) {
					if (p_180364_1_) {
						if (flag1 && flag3) {
							blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
						}

						if (flag2 && flag1) {
							blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
						}

						if (flag3 && flag) {
							blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
						}

						if (flag && flag2) {
							blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
						}
					} else {
						if (flag && flag2) {
							blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
						}

						if (flag3 && flag) {
							blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
						}

						if (flag2 && flag1) {
							blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
						}

						if (flag1 && flag3) {
							blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
						}
					}
				}
			}

			if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
				if (BlockRailBase.isRailBlock(this.world, blockpos.up())) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
				}

				if (BlockRailBase.isRailBlock(this.world, blockpos1.up())) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
				}
			}

			if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
				if (BlockRailBase.isRailBlock(this.world, blockpos3.up())) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
				}

				if (BlockRailBase.isRailBlock(this.world, blockpos2.up())) {
					blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
				}
			}

			if (blockrailbase$enumraildirection == null) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
			}

			this.updateConnectedRails(blockrailbase$enumraildirection);
			this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);

			if (p_180364_2_ || (this.world.getBlockState(this.pos) != this.state)) {
				this.world.setBlockState(this.pos, this.state, 3);

				for (int i = 0; i < this.connectedRails.size(); ++i) {
					BlockRailBase.Rail blockrailbase$rail = this.findRailAt(this.connectedRails.get(i));

					if (blockrailbase$rail != null) {
						blockrailbase$rail.removeSoftConnections();

						if (blockrailbase$rail.canConnectTo(this)) {
							blockrailbase$rail.connectTo(this);
						}
					}
				}
			}

			return this;
		}

		public IBlockState getBlockState() {
			return this.state;
		}
	}
}
