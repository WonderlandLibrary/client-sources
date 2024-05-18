package pw.latematt.xiv.event.events;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class ClickBlockEvent extends Event implements Cancellable {
    private final Block block;
    private final BlockPos pos;
    private boolean cancelled;

    public ClickBlockEvent(Block block, BlockPos pos) {
        this.block = block;
        this.pos = pos;
    }

    public Block getBlock() {
        return block;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
