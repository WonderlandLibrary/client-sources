package zamorozka.event.events;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.Event;

public class BlockBoundingBoxSubject extends Event {

    private Block block;
    private AxisAlignedBB boundingBox;
    private BlockPos blockPos;

    public BlockBoundingBoxSubject(Block block, AxisAlignedBB boundingBox, BlockPos blockPos) {
        this.block = block;
        this.boundingBox = boundingBox;
        this.blockPos = blockPos;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
}