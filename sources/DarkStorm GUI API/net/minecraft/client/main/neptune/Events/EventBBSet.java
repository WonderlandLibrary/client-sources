package net.minecraft.client.main.neptune.Events;

import net.minecraft.block.Block;
import net.minecraft.client.main.neptune.memes.events.Memevnt;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventBBSet implements Memevnt {

	public Block block;
	public BlockPos pos;
	public AxisAlignedBB boundingBox;

	public EventBBSet(Block block, BlockPos pos, AxisAlignedBB boundingBox) {
		this.block = block;
		this.pos = pos;
		this.boundingBox = boundingBox;
	}

}
