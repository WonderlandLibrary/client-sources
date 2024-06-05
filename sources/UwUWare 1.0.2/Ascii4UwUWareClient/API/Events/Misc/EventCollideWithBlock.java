
package Ascii4UwUWareClient.API.Events.Misc;

import Ascii4UwUWareClient.API.Event;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventCollideWithBlock extends Event {
	private Block block;
	private BlockPos blockPos;
	public AxisAlignedBB boundingBox;

	public EventCollideWithBlock(Block block, BlockPos pos, AxisAlignedBB boundingBox) {
		this.block = block;
		this.blockPos = pos;
		this.boundingBox = boundingBox;
	}

	public Block getBlock() {
		return this.block;
	}

	public BlockPos getPos() {
		return this.blockPos;
	}

	public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public void setBlockPos(BlockPos blockPos) {
		this.blockPos = blockPos;
	}

	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}
}
