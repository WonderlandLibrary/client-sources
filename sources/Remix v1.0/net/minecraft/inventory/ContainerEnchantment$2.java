package net.minecraft.inventory;

import net.minecraft.item.*;

class ContainerEnchantment$2 extends Slot {
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return true;
    }
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}