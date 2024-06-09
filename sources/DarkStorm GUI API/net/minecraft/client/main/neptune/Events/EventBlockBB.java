package net.minecraft.client.main.neptune.Events;

import net.minecraft.block.Block;
import net.minecraft.client.main.neptune.memes.events.Memevnt;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EventBlockBB implements Memevnt {

	public Block block;
	public AxisAlignedBB boundingBox;
	public int x, y, z;

	public Block getBlock() {
		return block;
	}

	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public void setBlock(Block block, AxisAlignedBB boundingBox, int x, int y, int z) {
		this.block = block;
		this.boundingBox = boundingBox;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}


}
