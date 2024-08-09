package ru.FecuritySQ.event.imp;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import ru.FecuritySQ.event.Event;

public class EventInteractBlockDamage extends Event {
    public BlockPos posBlock;
    public Direction directionFacing;

    public EventInteractBlockDamage(BlockPos pos, Direction direction){
        this.posBlock = pos;
        this.directionFacing = direction;
    }

}
