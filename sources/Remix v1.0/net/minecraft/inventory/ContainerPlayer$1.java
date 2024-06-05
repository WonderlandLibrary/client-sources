package net.minecraft.inventory;

import net.minecraft.item.*;
import net.minecraft.init.*;

class ContainerPlayer$1 extends Slot {
    final /* synthetic */ int val$var44;
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
    
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return stack != null && ((stack.getItem() instanceof ItemArmor) ? (((ItemArmor)stack.getItem()).armorType == this.val$var44) : ((stack.getItem() == Item.getItemFromBlock(Blocks.pumpkin) || stack.getItem() == Items.skull) && this.val$var44 == 0));
    }
    
    @Override
    public String func_178171_c() {
        return ItemArmor.EMPTY_SLOT_NAMES[this.val$var44];
    }
}