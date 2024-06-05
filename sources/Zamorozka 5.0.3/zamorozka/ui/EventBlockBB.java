package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class EventBlockBB extends Event {

	public boolean cancel;
    private Block block;
    private BlockPos blockPos;
    public AxisAlignedBB boundingBox;

    public EventBlockBB(Block block, BlockPos pos, AxisAlignedBB boundingBox) {
        this.block = block;
        this.blockPos = pos;
        this.boundingBox = boundingBox;
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockPos getPos() {
        return this.blockPos;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean state) {
        this.cancel = state;
    }

}