package in.momin5.cookieclient.api.event.events;

import in.momin5.cookieclient.api.event.Event;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ClickBlockEvent extends Event {

	private BlockPos blockPos;
	private EnumFacing enumFacing;

	public ClickBlockEvent(BlockPos blockPos, EnumFacing enumFacing) {
		this.blockPos = blockPos;
		this.enumFacing = enumFacing;
	}

	public BlockPos getBlockPos() { return this.blockPos; }
	public EnumFacing getEnumFacing() { return this.enumFacing; }
	public void setBlockPos(BlockPos blockPos) { this.blockPos = blockPos; }
	public void setEnumFacing(EnumFacing enumFacing) { this.enumFacing = enumFacing; }

}
