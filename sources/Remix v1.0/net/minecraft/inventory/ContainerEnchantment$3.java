package net.minecraft.inventory;

import net.minecraft.init.*;
import net.minecraft.item.*;

class ContainerEnchantment$3 extends Slot {
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return stack.getItem() == Items.dye && EnumDyeColor.func_176766_a(stack.getMetadata()) == EnumDyeColor.BLUE;
    }
}