/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBook
extends Item {
    @Override
    public boolean isItemTool(ItemStack stack) {
        return stack.func_190916_E() == 1;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }
}

