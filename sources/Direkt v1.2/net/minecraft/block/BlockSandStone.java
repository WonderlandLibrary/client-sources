package net.minecraft.block;

import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockSandStone extends Block {
	public static final PropertyEnum<BlockSandStone.EnumType> TYPE = PropertyEnum.<BlockSandStone.EnumType> create("type", BlockSandStone.EnumType.class);

	public BlockSandStone() {
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockSandStone.EnumType.DEFAULT));
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns the metadata of the dropped item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).getMetadata();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (BlockSandStone.EnumType blocksandstone$enumtype : BlockSandStone.EnumType.values()) {
			list.add(new ItemStack(itemIn, 1, blocksandstone$enumtype.getMetadata()));
		}
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state) {
		return MapColor.SAND;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, BlockSandStone.EnumType.byMetadata(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	public static enum EnumType implements IStringSerializable {
		DEFAULT(0, "sandstone", "default"), CHISELED(1, "chiseled_sandstone", "chiseled"), SMOOTH(2, "smooth_sandstone", "smooth");

		private static final BlockSandStone.EnumType[] META_LOOKUP = new BlockSandStone.EnumType[values().length];
		private final int metadata;
		private final String name;
		private final String unlocalizedName;

		private EnumType(int meta, String name, String unlocalizedName) {
			this.metadata = meta;
			this.name = name;
			this.unlocalizedName = unlocalizedName;
		}

		public int getMetadata() {
			return this.metadata;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public static BlockSandStone.EnumType byMetadata(int meta) {
			if ((meta < 0) || (meta >= META_LOOKUP.length)) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		@Override
		public String getName() {
			return this.name;
		}

		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}

		static {
			for (BlockSandStone.EnumType blocksandstone$enumtype : values()) {
				META_LOOKUP[blocksandstone$enumtype.getMetadata()] = blocksandstone$enumtype;
			}
		}
	}
}
