/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import ru.govno.client.event.Event;

public class EventLightingCheck
extends Event {
    private final EnumSkyBlock enumSkyBlock;
    private final BlockPos pos;

    public EventLightingCheck(EnumSkyBlock enumSkyBlock, BlockPos pos) {
        this.enumSkyBlock = enumSkyBlock;
        this.pos = pos;
    }

    public EnumSkyBlock getEnumSkyBlock() {
        return this.enumSkyBlock;
    }

    public BlockPos getPos() {
        return this.pos;
    }
}

