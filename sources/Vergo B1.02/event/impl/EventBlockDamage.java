package xyz.vergoclient.event.impl;

import net.minecraft.util.BlockPos;
import xyz.vergoclient.event.Event;

public class EventBlockDamage extends Event {
    private final BlockPos blockPos;

    public EventBlockDamage(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }
}
