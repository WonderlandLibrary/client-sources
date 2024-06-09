package me.swezedcode.client.utils.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventBBSet implements Event {
	public Block block;
	public BlockPos pos;
	public AxisAlignedBB boundingBox;

	public EventBBSet(Block block, BlockPos pos, AxisAlignedBB boundingBox) {
		this.block = block;
		this.pos = pos;
		this.boundingBox = boundingBox;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}
	
	public void setBlock(Block block) {
		this.block = block;
	}
	
	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}
	
}