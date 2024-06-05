package zamorozka.event.events;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.Event;

public class DamageBlockEvent extends Event {
	private BlockPos pos;
    private EnumFacing face;

    public DamageBlockEvent(BlockPos pos, EnumFacing face) {
        this.pos = pos;
        this.face = face;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public EnumFacing getFace() {
        return face;
    }

    public void setFace(EnumFacing face) {
        this.face = face;
    }
}
