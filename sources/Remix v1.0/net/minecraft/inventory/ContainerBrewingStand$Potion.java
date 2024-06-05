package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

static class Potion extends Slot
{
    private EntityPlayer player;
    
    public Potion(final EntityPlayer p_i1804_1_, final IInventory p_i1804_2_, final int p_i1804_3_, final int p_i1804_4_, final int p_i1804_5_) {
        super(p_i1804_2_, p_i1804_3_, p_i1804_4_, p_i1804_5_);
        this.player = p_i1804_1_;
    }
    
    public static boolean canHoldPotion(final ItemStack p_75243_0_) {
        return p_75243_0_ != null && (p_75243_0_.getItem() == Items.potionitem || p_75243_0_.getItem() == Items.glass_bottle);
    }
    
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return canHoldPotion(stack);
    }
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer playerIn, final ItemStack stack) {
        if (stack.getItem() == Items.potionitem && stack.getMetadata() > 0) {
            this.player.triggerAchievement(AchievementList.potion);
        }
        super.onPickupFromSlot(playerIn, stack);
    }
}
