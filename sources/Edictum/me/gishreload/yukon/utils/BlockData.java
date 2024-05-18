package me.gishreload.yukon.utils;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class BlockData {

	public final BlockPos position;
	public final EnumFacing face;
	public BlockData(BlockPos add, EnumFacing up) {
		this.position = add;
		this.face = up;
	}

}
