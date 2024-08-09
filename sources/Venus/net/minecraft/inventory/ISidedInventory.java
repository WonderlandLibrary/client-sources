/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;

public interface ISidedInventory
extends IInventory {
    public int[] getSlotsForFace(Direction var1);

    public boolean canInsertItem(int var1, ItemStack var2, @Nullable Direction var3);

    public boolean canExtractItem(int var1, ItemStack var2, Direction var3);
}

