package zamorozka.event.events;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.Event;

public class EventPlayerClickBlock extends Event
{
    public BlockPos Location;
    public EnumFacing Facing;

    public EventPlayerClickBlock(BlockPos loc, EnumFacing face)
    {
        Location = loc;
        Facing = face;
    }
}