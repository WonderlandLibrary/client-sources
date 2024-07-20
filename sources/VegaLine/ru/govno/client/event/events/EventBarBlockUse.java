/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.util.math.BlockPos;
import ru.govno.client.event.Event;

public class EventBarBlockUse
extends Event {
    public BlockPos position;

    public EventBarBlockUse(BlockPos pos) {
        this.position = pos;
    }

    public BlockPos getPosition() {
        return this.position;
    }
}

