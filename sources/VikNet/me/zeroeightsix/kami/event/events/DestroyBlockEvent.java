package me.zeroeightsix.kami.event.events;

import me.zeroeightsix.kami.event.KamiEvent;
import net.minecraft.util.math.BlockPos;

public class DestroyBlockEvent extends KamiEvent {
    BlockPos pos;
    public DestroyBlockEvent(BlockPos blockPos){
        super();
        pos = blockPos;
    }

    public BlockPos getBlockPos(){
        return pos;
    }
}
