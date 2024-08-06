package com.shroomclient.shroomclientnextgen.events.impl;

import com.shroomclient.shroomclientnextgen.events.Event;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BlockChangeEvent extends Event {

    public BlockPos pos;

    public BlockState old;
    public BlockState update;

    // last variable is "update" because "new" doesnt work
    public BlockChangeEvent(BlockPos pos, BlockState old, BlockState update) {
        this.pos = pos;

        this.old = old;
        this.update = update;
    }
}
