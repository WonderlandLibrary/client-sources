package net.minecraft.block;

import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockQuartz extends Block {
	public static final PropertyEnum<BlockQuartz.EnumType> VARIANT = PropertyEnum.<BlockQuartz.EnumType> create("variant", BlockQuartz.EnumType.class);

	public BlockQuartz() {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockQuartz.EnumType.DEFAULT));
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate
	 */
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		if (meta == BlockQuartz.EnumType.LINES_Y.getMetadata()) {
			switch (facing.getAxis()) {
			case Z:
				return this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.LINES_Z);

			case X:
				return this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.LINES_X);

			case Y:
				return this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.LINES_Y);
			}
		}

		return meta == BlockQuartz.EnumType.CHISELED.getMetadata() ? this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.CHISELED)
				: this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.DEFAULT);
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns the metadata of the dropped item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state) {
		BlockQuartz.EnumType blockquartz$enumtype = state.getValue(VARIANT);
		return (blockquartz$enumtype != BlockQuartz.EnumType.LINES_X) && (blockquartz$enumtype != BlockQuartz.EnumType.LINES_Z) ? blockquartz$enumtype.getMetadata()
				: BlockQuartz.EnumType.LINES_Y.getMetadata();
	}

	@Override
	protected ItemStack createStackedBlock(IBlockState state) {
		BlockQuartz.EnumType blockquartz$enumtype = state.getValue(VARIANT);
		return (blockquartz$enumtype != BlockQuartz.EnumType.LINES_X) && (blockquartz$enumtype != BlockQuartz.EnumType.LINES_Z) ? super.createStackedBlock(state)
				: new ItemStack(Item.getItemFromBlock(this), 1, BlockQuartz.EnumType.LINES_Y.getMetadata());
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		list.add(new ItemStack(itemIn, 1, BlockQuartz.EnumType.DEFAULT.getMetadata()));
		list.add(new ItemStack(itemIn, 1, BlockQuartz.EnumType.CHISELED.getMetadata()));
		list.add(new ItemStack(itemIn, 1, BlockQuartz.EnumType.LINES_Y.getMetadata()));
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state) {
		return MapColor.QUARTZ;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.byMetadata(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed blockstate.
	 */
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		switch (rot) {
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:
			switch (state.getValue(VARIANT)) {
			case LINES_X:
				return state.withProperty(VARIANT, BlockQuartz.EnumType.LINES_Z);

			case LINES_Z:
				return state.withProperty(VARIANT, BlockQuartz.EnumType.LINES_X);

			default:
				return state;
			}

		default:
			return state;
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	public static enum EnumType implements IStringSerializable {
		DEFAULT(0, "default", "default"), CHISELED(1, "chiseled", "chiseled"), LINES_Y(2, "lines_y", "lines"), LINES_X(3, "lines_x", "lines"), LINES_Z(4, "lines_z", "lines");

		private static final BlockQuartz.EnumType[] META_LOOKUP = new BlockQuartz.EnumType[values().length];
		private final int meta;
		private final String serializedName;
		private final String unlocalizedName;

		private EnumType(int meta, String name, String unlocalizedName) {
			this.meta = meta;
			this.serializedName = name;
			this.unlocalizedName = unlocalizedName;
		}

		public int getMetadata() {
			return this.meta;
		}

		@Override
		public String toString() {
			return this.unlocalizedName;
		}

		public static BlockQuartz.EnumType byMetadata(int meta) {
			if ((meta < 0) || (meta >= META_LOOKUP.length)) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		@Override
		public String getName() {
			return this.serializedName;
		}

		static {
			for (BlockQuartz.EnumType blockquartz$enumtype : values()) {
				META_LOOKUP[blockquartz$enumtype.getMetadata()] = blockquartz$enumtype;
			}
		}
	}
}
