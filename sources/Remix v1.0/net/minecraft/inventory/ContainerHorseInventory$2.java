package net.minecraft.inventory;

import net.minecraft.entity.passive.*;
import net.minecraft.item.*;

class ContainerHorseInventory$2 extends Slot {
    final /* synthetic */ EntityHorse val$p_i45791_3_;
    
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return super.isItemValid(stack) && this.val$p_i45791_3_.canWearArmor() && EntityHorse.func_146085_a(stack.getItem());
    }
    
    @Override
    public boolean canBeHovered() {
        return this.val$p_i45791_3_.canWearArmor();
    }
}