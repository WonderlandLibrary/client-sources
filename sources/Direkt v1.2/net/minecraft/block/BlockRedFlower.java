package net.minecraft.block;

public class BlockRedFlower extends BlockFlower {
	/**
	 * Get the Type of this flower (Yellow/Red)
	 */
	@Override
	public BlockFlower.EnumFlowerColor getBlockType() {
		return BlockFlower.EnumFlowerColor.RED;
	}
}
