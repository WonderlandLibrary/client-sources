package net.minecraft.inventory;

import net.minecraft.item.*;

class Ingredient extends Slot
{
    public Ingredient(final IInventory p_i1803_2_, final int p_i1803_3_, final int p_i1803_4_, final int p_i1803_5_) {
        super(p_i1803_2_, p_i1803_3_, p_i1803_4_, p_i1803_5_);
    }
    
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return stack != null && stack.getItem().isPotionIngredient(stack);
    }
    
    @Override
    public int getSlotStackLimit() {
        return 64;
    }
}
