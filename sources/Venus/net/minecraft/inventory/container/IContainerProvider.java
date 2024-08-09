/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

@FunctionalInterface
public interface IContainerProvider {
    @Nullable
    public Container createMenu(int var1, PlayerInventory var2, PlayerEntity var3);
}

