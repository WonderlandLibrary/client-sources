/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import ru.govno.client.event.Event;

public class EventPostPlace
extends Event {
    public BlockPos position;
    public ItemStack stack;

    public EventPostPlace(BlockPos position, ItemStack stack) {
        this.position = position;
        this.stack = stack;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public ItemStack getStack() {
        return this.stack;
    }
}

