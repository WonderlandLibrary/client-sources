package pw.latematt.xiv.event.events;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class BlockModelRenderEvent extends Event {
    private final Block block;
    private final BlockPos pos;

    public BlockModelRenderEvent(Block block, BlockPos pos) {
        this.block = block;
        this.pos = pos;
    }

    public Block getBlock() {
        return block;
    }

    public BlockPos getPos() {
        return pos;
    }
}
