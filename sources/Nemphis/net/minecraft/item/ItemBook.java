/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBook
extends Item {
    private static final String __OBFID = "CL_00001775";

    @Override
    public boolean isItemTool(ItemStack stack) {
        if (stack.stackSize == 1) {
            return true;
        }
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }
}

