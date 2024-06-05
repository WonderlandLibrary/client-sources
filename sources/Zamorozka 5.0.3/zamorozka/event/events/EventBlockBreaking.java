package zamorozka.event.events;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.Event;

public class EventBlockBreaking extends Event {
    private EnumBlock state;
    private BlockPos pos;
    private EnumFacing side;

    public EventBlockBreaking(final EnumBlock state, final BlockPos pos, final EnumFacing side) {
        this.side = side;
        this.state = state;
        this.pos = pos;
    }

    public EventBlockBreaking(final EnumBlock state, final BlockPos pos) {
        this.state = state;
        this.pos = pos;
    }

    public void setState(final EnumBlock state) {
        this.state = state;
    }

    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }

    public void setSide(final EnumFacing side) {
        this.side = side;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public EnumBlock getState() {
        return this.state;
    }

    public EnumFacing getSide() {
        return this.side;
    }

    public enum EnumBlock {
        CLICK("CLICK", 0, "CLICK", 0),
        DAMAGE("DAMAGE", 1, "DAMAGE", 1),
        DESTROY("DESTROY", 2, "DESTROY", 2);

        private static final EnumBlock[] ENUM$VALUES;

        static {
            ENUM$VALUES = new EnumBlock[]{EnumBlock.CLICK, EnumBlock.DAMAGE, EnumBlock.DESTROY};
        }

        private EnumBlock(final String s, final int n, final String var1, final int var2) {
        }
    }
}