package zamorozka.event.events;

import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventTarget;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class EventBBSet implements Event {

	public Block block;
	public BlockPos pos;
	public AxisAlignedBB boundingBox;

	public EventBBSet(Block block, BlockPos pos, AxisAlignedBB boundingBox) {
		this.block = block;
		this.pos = pos;
		this.boundingBox = boundingBox;
	}

	@Override
	public boolean getBubbles() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getCancelable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EventTarget getCurrentTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getEventPhase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EventTarget getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTimeStamp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initEvent(String eventTypeArg, boolean canBubbleArg, boolean cancelableArg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preventDefault() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopPropagation() {
		// TODO Auto-generated method stub
		
	}

}
