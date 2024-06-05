package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;

class CreativeSlot extends Slot
{
    private final Slot field_148332_b;
    
    public CreativeSlot(final Slot p_i46313_2_, final int p_i46313_3_) {
        super(p_i46313_2_.inventory, p_i46313_3_, 0, 0);
        this.field_148332_b = p_i46313_2_;
    }
    
    @Override
    public void onPickupFromSlot(final EntityPlayer playerIn, final ItemStack stack) {
        this.field_148332_b.onPickupFromSlot(playerIn, stack);
    }
    
    @Override
    public boolean isItemValid(final ItemStack stack) {
        return this.field_148332_b.isItemValid(stack);
    }
    
    @Override
    public ItemStack getStack() {
        return this.field_148332_b.getStack();
    }
    
    @Override
    public boolean getHasStack() {
        return this.field_148332_b.getHasStack();
    }
    
    @Override
    public void putStack(final ItemStack p_75215_1_) {
        this.field_148332_b.putStack(p_75215_1_);
    }
    
    @Override
    public void onSlotChanged() {
        this.field_148332_b.onSlotChanged();
    }
    
    @Override
    public int getSlotStackLimit() {
        return this.field_148332_b.getSlotStackLimit();
    }
    
    @Override
    public int func_178170_b(final ItemStack p_178170_1_) {
        return this.field_148332_b.func_178170_b(p_178170_1_);
    }
    
    @Override
    public String func_178171_c() {
        return this.field_148332_b.func_178171_c();
    }
    
    @Override
    public ItemStack decrStackSize(final int p_75209_1_) {
        return this.field_148332_b.decrStackSize(p_75209_1_);
    }
    
    @Override
    public boolean isHere(final IInventory p_75217_1_, final int p_75217_2_) {
        return this.field_148332_b.isHere(p_75217_1_, p_75217_2_);
    }
}
