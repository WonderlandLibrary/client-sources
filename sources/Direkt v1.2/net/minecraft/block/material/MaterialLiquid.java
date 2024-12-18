package net.minecraft.block.material;

public class MaterialLiquid extends Material {
	public MaterialLiquid(MapColor color) {
		super(color);
		this.setReplaceable();
		this.setNoPushMobility();
	}

	/**
	 * Returns if blocks of these materials are liquids.
	 */
	@Override
	public boolean isLiquid() {
		return true;
	}

	/**
	 * Returns if this material is considered solid or not
	 */
	@Override
	public boolean blocksMovement() {
		return false;
	}

	/**
	 * Returns true if the block is a considered solid. This is true by default.
	 */
	@Override
	public boolean isSolid() {
		return false;
	}
}
