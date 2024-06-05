package zamorozka.event.events;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.Event;

public class EventLiquidCollisionBB extends Event {
    private AxisAlignedBB boundingBox;
    private BlockPos blockPos;

    public EventLiquidCollisionBB()
    {
        super();
    }

    public EventLiquidCollisionBB(BlockPos blockPos)
    {
        super();
        this.blockPos = blockPos;
    }

    public AxisAlignedBB getBoundingBox()
    {
        return boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox)
    {
        this.boundingBox = boundingBox;
    }

    public BlockPos getBlockPos()
    {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos)
    {
        this.blockPos = blockPos;
    }
}