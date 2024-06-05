package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.init.*;

class ContainerHorseInventory$1 extends Slot {
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return super.isItemValid(stack) && stack.getItem() == Items.saddle && !this.getHasStack();
    }
}