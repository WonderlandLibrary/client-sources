package win.sightclient.event.events.player;

import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import win.sightclient.event.Event;

public class EventWater extends Event {

	public AxisAlignedBB collisionBox = null;
	
	private BlockLiquid block;
	private BlockPos pos;
	
	public EventWater(BlockLiquid block, BlockPos pos) {
		this.block = block;
		this.pos = pos;
	}
	
	public BlockLiquid getBlock() {
		return block;
	}
	
	public BlockPos getPos() {
		return pos;
	}
}
