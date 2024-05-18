package pw.latematt.xiv.event.events;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import pw.latematt.xiv.event.Event;

/**
 * @author Matthew
 */
public class BlockAddBBEvent extends Event {
    private final Block block;
    private final Entity entity;
    private final BlockPos pos;
    private AxisAlignedBB axisAlignedBB;

    public BlockAddBBEvent(Block block, BlockPos pos, AxisAlignedBB axisAlignedBB, Entity entity) {
        this.block = block;
        this.pos = pos;
        this.axisAlignedBB = axisAlignedBB;
        this.entity = entity;
    }

    public Block getBlock() {
        return block;
    }

    public BlockPos getPos() {
        return pos;
    }

    public Entity getEntity() {
        return entity;
    }

    public AxisAlignedBB getAxisAlignedBB() {
        return axisAlignedBB;
    }

    public void setAxisAlignedBB(AxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
    }
}
