package lunadevs.luna.events;

import com.darkmagician6.eventapi.events.Event;
import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class PlaceBlockEvent extends EventCancellable implements Event {

	private BlockPos blockPos = null;
	private EnumFacing enumFacing = null;

	public PlaceBlockEvent(BlockPos blockPos, EnumFacing enumFacing) {
		setBlockPos(blockPos);
		setEnumFacing(enumFacing);

	}
	
	public BlockPos getBlockPos()
	  {
	    return this.blockPos;
	  }
	  
	  public void setBlockPos(BlockPos blockPos)
	  {
	    this.blockPos = blockPos;
	  }
	  
	  public EnumFacing getEnumFacing()
	  {
	    return this.enumFacing;
	  }
	  
	  public void setEnumFacing(EnumFacing enumFacing)
	  {
	    this.enumFacing = enumFacing;
	  }
	
}
