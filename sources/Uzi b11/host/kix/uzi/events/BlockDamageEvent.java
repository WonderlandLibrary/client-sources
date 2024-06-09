package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * Created by myche on 3/6/2017.
 */
public class BlockDamageEvent extends EventCancellable {

    private BlockPos blockPos;
    private EnumFacing enumFacing;
    private boolean cancelled;

    public BlockDamageEvent(final BlockPos blockPos, final EnumFacing enumFacing) {
        this.blockPos = null;
        this.enumFacing = null;
        this.setBlockPos(blockPos);
        this.setEnumFacing(enumFacing);
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public void setBlockPos(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public EnumFacing getEnumFacing() {
        return this.enumFacing;
    }

    public void setEnumFacing(final EnumFacing enumFacing) {
        this.enumFacing = enumFacing;
    }


}
