package pw.latematt.xiv.event.events;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import pw.latematt.xiv.event.Cancellable;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class BreakingBlockEvent extends Event implements Cancellable {
    private final Block block;
    private final BlockPos pos;
    private boolean cancelled;
    private int hitDelay;
    private double multiplier;

    public BreakingBlockEvent(Block block, BlockPos pos, int hitDelay, double multiplier) {
        this.block = block;
        this.pos = pos;
        this.hitDelay = hitDelay;
        this.multiplier = multiplier;
    }

    public Block getBlock() {
        return block;
    }

    public BlockPos getPos() {
        return pos;
    }

    public int getHitDelay() {
        return hitDelay;
    }

    public void setHitDelay(int hitDelay) {
        this.hitDelay = hitDelay;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
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
