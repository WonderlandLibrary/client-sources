package net.shoreline.client.impl.event.network;

import net.shoreline.client.api.event.Cancelable;
import net.shoreline.client.api.event.Event;
import net.minecraft.util.math.BlockPos;

@Cancelable
public class BreakBlockEvent extends Event
{
    private final BlockPos pos;

    public BreakBlockEvent(BlockPos pos)
    {
        this.pos = pos;
    }

    public BlockPos getPos()
    {
        return pos;
    }
}
