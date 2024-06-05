package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.init.*;

class BeaconSlot extends Slot
{
    public BeaconSlot(final IInventory p_i1801_2_, final int p_i1801_3_, final int p_i1801_4_, final int p_i1801_5_) {
        super(p_i1801_2_, p_i1801_3_, p_i1801_4_, p_i1801_5_);
    }
    
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return stack != null && (stack.getItem() == Items.emerald || stack.getItem() == Items.diamond || stack.getItem() == Items.gold_ingot || stack.getItem() == Items.iron_ingot);
    }
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
