/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IContainerListener {
    public void sendAllContents(Container var1, NonNullList<ItemStack> var2);

    public void sendSlotContents(Container var1, int var2, ItemStack var3);

    public void sendWindowProperty(Container var1, int var2, int var3);
}

