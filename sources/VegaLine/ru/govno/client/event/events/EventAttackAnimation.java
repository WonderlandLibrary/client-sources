/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.event.events;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import ru.govno.client.event.Event;

public class EventAttackAnimation
extends Event {
    private final EnumHand hand;
    private final float partialTicks;
    private final float interpolatedPitch;
    private final float swingProgress;
    private final float equipProgress;
    private final ItemStack stack;

    public EventAttackAnimation(EnumHand hand, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
        this.hand = hand;
        this.partialTicks = partialTicks;
        this.interpolatedPitch = interpolatedPitch;
        this.swingProgress = swingProgress;
        this.equipProgress = equipProgress;
        this.stack = stack;
    }

    public EnumHand getHand() {
        return this.hand;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    public float getInterpolatedPitch() {
        return this.interpolatedPitch;
    }

    public float getSwingProgress() {
        return this.swingProgress;
    }

    public float getEquipProgress() {
        return this.equipProgress;
    }

    public ItemStack getItemStack() {
        return this.stack;
    }
}

