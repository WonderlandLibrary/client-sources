package client.metaware.impl.event.impl.player;

import client.metaware.impl.event.Event;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class DamageBlockEvent extends Event {

    private BlockPos blockPos;
    private EnumFacing facing;

    public DamageBlockEvent(BlockPos blockPos, EnumFacing facing) {
        this.blockPos = blockPos;
        this.facing = facing;
    }

    public BlockPos blockPos() {
        return blockPos;
    }

    public EnumFacing facing() {
        return facing;
    }
}
