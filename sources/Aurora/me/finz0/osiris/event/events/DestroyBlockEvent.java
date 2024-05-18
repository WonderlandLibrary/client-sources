package me.finz0.osiris.event.events;

import me.finz0.osiris.event.OsirisEvent;
import net.minecraft.util.math.BlockPos;

public class DestroyBlockEvent extends OsirisEvent {
    BlockPos pos;
    public DestroyBlockEvent(BlockPos blockPos){
        super();
        pos = blockPos;
    }

    public BlockPos getBlockPos(){
        return pos;
    }
}
