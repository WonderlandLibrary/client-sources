package zamorozka.event.events;

import zamorozka.event.Event;
import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class EventEntityCollision extends Event {
    private Block block;
    private BlockPos blockPos;
    private AxisAlignedBB boundingBox;
    private boolean pre;

    public EventEntityCollision(final Block block, final BlockPos pos, final AxisAlignedBB boundingBox, final boolean pre) {
        this.block = block;
        this.blockPos = pos;
        this.boundingBox = boundingBox;
        this.pre = pre;
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public void setBlock(final Block block) {
        this.block = block;
    }

    public void setBlockPos(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
}