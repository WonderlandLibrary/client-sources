/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class FurnaceFuelSlot
extends Slot {
    private final AbstractFurnaceContainer field_216939_a;

    public FurnaceFuelSlot(AbstractFurnaceContainer abstractFurnaceContainer, IInventory iInventory, int n, int n2, int n3) {
        super(iInventory, n, n2, n3);
        this.field_216939_a = abstractFurnaceContainer;
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return this.field_216939_a.isFuel(itemStack) || FurnaceFuelSlot.isBucket(itemStack);
    }

    @Override
    public int getItemStackLimit(ItemStack itemStack) {
        return FurnaceFuelSlot.isBucket(itemStack) ? 1 : super.getItemStackLimit(itemStack);
    }

    public static boolean isBucket(ItemStack itemStack) {
        return itemStack.getItem() == Items.BUCKET;
    }
}

