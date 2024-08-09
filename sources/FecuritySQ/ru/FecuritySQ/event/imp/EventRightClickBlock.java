package ru.FecuritySQ.event.imp;

import net.minecraft.util.math.BlockPos;
import ru.FecuritySQ.event.Event;

public class EventRightClickBlock extends Event {

    public BlockPos pos;
    public EventRightClickBlock(BlockPos pos){
        this.pos = pos;
    }

}
