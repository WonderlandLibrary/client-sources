package net.minecraft.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBed extends BlockHorizontal {
	public static final PropertyEnum<BlockBed.EnumPartType> PART = PropertyEnum.<BlockBed.EnumPartType> create("part", BlockBed.EnumPartType.class);
	public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");
	protected static final AxisAlignedBB BED_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5625D, 1.0D);

	public BlockBed() {
		super(Material.CLOTH);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(OCCUPIED, Boolean.valueOf(false)));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY,
			float hitZ) {
		if (worldIn.isRemote) {
			return true;
		} else {
			if (state.getValue(PART) != BlockBed.EnumPartType.HEAD) {
				pos = pos.offset(state.getValue(FACING));
				state = worldIn.getBlockState(pos);

				if (state.getBlock() != this) { return true; }
			}

			if (worldIn.provider.canRespawnHere() && (worldIn.getBiomeGenForCoords(pos) != Biomes.HELL)) {
				if (state.getValue(OCCUPIED).booleanValue()) {
					EntityPlayer entityplayer = this.getPlayerInBed(worldIn, pos);

					if (entityplayer != null) {
						playerIn.addChatComponentMessage(new TextComponentTranslation("tile.bed.occupied", new Object[0]));
						return true;
					}

					state = state.withProperty(OCCUPIED, Boolean.valueOf(false));
					worldIn.setBlockState(pos, state, 4);
				}

				EntityPlayer.SleepResult entityplayer$sleepresult = playerIn.trySleep(pos);

				if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK) {
					state = state.withProperty(OCCUPIED, Boolean.valueOf(true));
					worldIn.setBlockState(pos, state, 4);
					return true;
				} else {
					if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW) {
						playerIn.addChatComponentMessage(new TextComponentTranslation("tile.bed.noSleep", new Object[0]));
					} else if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_SAFE) {
						playerIn.addChatComponentMessage(new TextComponentTranslation("tile.bed.notSafe", new Object[0]));
					}

					return true;
				}
			} else {
				worldIn.setBlockToAir(pos);
				BlockPos blockpos = pos.offset(state.getValue(FACING).getOpposite());

				if (worldIn.getBlockState(blockpos).getBlock() == this) {
					worldIn.setBlockToAir(blockpos);
				}

				worldIn.newExplosion((Entity) null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0F, true, true);
				return true;
			}
		}
	}

	@Nullable
	private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos) {
		for (EntityPlayer entityplayer : worldIn.playerEntities) {
			if (entityplayer.isPlayerSleeping() && entityplayer.playerLocation.equals(pos)) { return entityplayer; }
		}

		return null;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void func_189540_a(IBlockState p_189540_1_, World p_189540_2_, BlockPos p_189540_3_, Block p_189540_4_) {
		EnumFacing enumfacing = p_189540_1_.getValue(FACING);

		if (p_189540_1_.getValue(PART) == BlockBed.EnumPartType.HEAD) {
			if (p_189540_2_.getBlockState(p_189540_3_.offset(enumfacing.getOpposite())).getBlock() != this) {
				p_189540_2_.setBlockToAir(p_189540_3_);
			}
		} else if (p_189540_2_.getBlockState(p_189540_3_.offset(enumfacing)).getBlock() != this) {
			p_189540_2_.setBlockToAir(p_189540_3_);

			if (!p_189540_2_.isRemote) {
				this.dropBlockAsItem(p_189540_2_, p_189540_3_, p_189540_1_, 0);
			}
		}
	}

	@Override
	@Nullable

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(PART) == BlockBed.EnumPartType.HEAD ? null : Items.BED;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BED_AABB;
	}

	@Nullable

	/**
	 * Returns a safe BlockPos to disembark the bed
	 */
	public static BlockPos getSafeExitLocation(World worldIn, BlockPos pos, int tries) {
		EnumFacing enumfacing = worldIn.getBlockState(pos).getValue(FACING);
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		for (int l = 0; l <= 1; ++l) {
			int i1 = i - (enumfacing.getFrontOffsetX() * l) - 1;
			int j1 = k - (enumfacing.getFrontOffsetZ() * l) - 1;
			int k1 = i1 + 2;
			int l1 = j1 + 2;

			for (int i2 = i1; i2 <= k1; ++i2) {
				for (int j2 = j1; j2 <= l1; ++j2) {
					BlockPos blockpos = new BlockPos(i2, j, j2);

					if (hasRoomForPlayer(worldIn, blockpos)) {
						if (tries <= 0) { return blockpos; }

						--tries;
					}
				}
			}
		}

		return null;
	}

	protected static boolean hasRoomForPlayer(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isFullyOpaque() && !worldIn.getBlockState(pos).getMaterial().isSolid() && !worldIn.getBlockState(pos.up()).getMaterial().isSolid();
	}

	/**
	 * Spawns this Block's drops into the World as EntityItems.
	 */
	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT) {
			super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
		}
	}

	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Items.BED);
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (player.capabilities.isCreativeMode && (state.getValue(PART) == BlockBed.EnumPartType.HEAD)) {
			BlockPos blockpos = pos.offset(state.getValue(FACING).getOpposite());

			if (worldIn.getBlockState(blockpos).getBlock() == this) {
				worldIn.setBlockToAir(blockpos);
			}
		}
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.HEAD).withProperty(FACING, enumfacing).withProperty(OCCUPIED, Boolean.valueOf((meta & 4) > 0))
				: this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(FACING, enumfacing);
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		if (state.getValue(PART) == BlockBed.EnumPartType.FOOT) {
			IBlockState iblockstate = worldIn.getBlockState(pos.offset(state.getValue(FACING)));

			if (iblockstate.getBlock() == this) {
				state = state.withProperty(OCCUPIED, iblockstate.getValue(OCCUPIED));
			}
		}

		return state;
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

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | state.getValue(FACING).getHorizontalIndex();

		if (state.getValue(PART) == BlockBed.EnumPartType.HEAD) {
			i |= 8;

			if (state.getValue(OCCUPIED).booleanValue()) {
				i |= 4;
			}
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, PART, OCCUPIED });
	}

	public static enum EnumPartType implements IStringSerializable {
		HEAD("head"), FOOT("foot");

		private final String name;

		private EnumPartType(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}
}
