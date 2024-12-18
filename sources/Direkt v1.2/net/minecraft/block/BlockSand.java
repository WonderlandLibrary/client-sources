package net.minecraft.block;

import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockSand extends BlockFalling {
	public static final PropertyEnum<BlockSand.EnumType> VARIANT = PropertyEnum.<BlockSand.EnumType> create("variant", BlockSand.EnumType.class);

	public BlockSand() {
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockSand.EnumType.SAND));
	}

	/**
	 * Gets the metadata of the item this Block can drop. This method is called when the block gets destroyed. It returns the metadata of the dropped item based on the old metadata of the block.
	 */
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
		for (BlockSand.EnumType blocksand$enumtype : BlockSand.EnumType.values()) {
			list.add(new ItemStack(itemIn, 1, blocksand$enumtype.getMetadata()));
		}
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	@Override
	public MapColor getMapColor(IBlockState state) {
		return state.getValue(VARIANT).getMapColor();
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, BlockSand.EnumType.byMetadata(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
	public int func_189876_x(IBlockState p_189876_1_) {
		BlockSand.EnumType blocksand$enumtype = p_189876_1_.getValue(VARIANT);
		return blocksand$enumtype.func_189865_a();
	}

	public static enum EnumType implements IStringSerializable {
		SAND(0, "sand", "default", MapColor.SAND, -2370656), RED_SAND(1, "red_sand", "red", MapColor.ADOBE, -5679071);

		private static final BlockSand.EnumType[] META_LOOKUP = new BlockSand.EnumType[values().length];
		private final int meta;
		private final String name;
		private final MapColor mapColor;
		private final String unlocalizedName;
		private final int field_189866_h;

		private EnumType(int p_i47157_3_, String p_i47157_4_, String p_i47157_5_, MapColor p_i47157_6_, int p_i47157_7_) {
			this.meta = p_i47157_3_;
			this.name = p_i47157_4_;
			this.mapColor = p_i47157_6_;
			this.unlocalizedName = p_i47157_5_;
			this.field_189866_h = p_i47157_7_;
		}

		public int func_189865_a() {
			return this.field_189866_h;
		}

		public int getMetadata() {
			return this.meta;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public MapColor getMapColor() {
			return this.mapColor;
		}

		public static BlockSand.EnumType byMetadata(int meta) {
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
			for (BlockSand.EnumType blocksand$enumtype : values()) {
				META_LOOKUP[blocksand$enumtype.getMetadata()] = blocksand$enumtype;
			}
		}
	}
}
