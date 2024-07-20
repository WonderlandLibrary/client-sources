/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.item.ItemStack;
import ru.govno.client.event.Event;

public class EventRenderToolTip
extends Event {
    private final ItemStack stack;
    private final int x;
    private final int y;

    public EventRenderToolTip(ItemStack stack, int x, int y) {
        this.stack = stack;
        this.x = x;
        this.y = y;
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}

