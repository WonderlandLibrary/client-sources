package wtf.diablo.client.event.impl.player;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import wtf.diablo.client.event.api.AbstractEvent;

public final class EventBlockBB extends AbstractEvent {

    private BlockPos pos;
    private Block block;
    private AxisAlignedBB aabb;

    public EventBlockBB(BlockPos pos, Block block, AxisAlignedBB aabb) {
        this.pos = pos;
        this.block = block;
        this.aabb = aabb;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public AxisAlignedBB getAabb() {
        return aabb;
    }

    public void setAabb(AxisAlignedBB aabb) {
        this.aabb = aabb;
    }

    public void addBlock(BlockPos pos) {
        if(block instanceof BlockAir && pos.getY() <= pos.getY())
            setAabb(AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1.0D, pos.getY() + 1.0D, pos.getZ() + 1.0D));
    }

}
