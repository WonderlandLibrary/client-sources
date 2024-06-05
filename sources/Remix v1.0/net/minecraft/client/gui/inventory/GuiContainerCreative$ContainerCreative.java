package net.minecraft.client.gui.inventory;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

static class ContainerCreative extends Container
{
    public List itemList;
    
    public ContainerCreative(final EntityPlayer p_i1086_1_) {
        this.itemList = Lists.newArrayList();
        final InventoryPlayer var2 = p_i1086_1_.inventory;
        for (int var3 = 0; var3 < 5; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(GuiContainerCreative.access$100(), var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(var2, var3, 9 + var3 * 18, 112));
        }
        this.scrollTo(0.0f);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer playerIn) {
        return true;
    }
    
    public void scrollTo(final float p_148329_1_) {
        final int var2 = (this.itemList.size() + 8) / 9 - 5;
        int var3 = (int)(p_148329_1_ * var2 + 0.5);
        if (var3 < 0) {
            var3 = 0;
        }
        for (int var4 = 0; var4 < 5; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                final int var6 = var5 + (var4 + var3) * 9;
                if (var6 >= 0 && var6 < this.itemList.size()) {
                    GuiContainerCreative.access$100().setInventorySlotContents(var5 + var4 * 9, this.itemList.get(var6));
                }
                else {
                    GuiContainerCreative.access$100().setInventorySlotContents(var5 + var4 * 9, null);
                }
            }
        }
    }
    
    public boolean func_148328_e() {
        return this.itemList.size() > 45;
    }
    
    @Override
    protected void retrySlotClick(final int p_75133_1_, final int p_75133_2_, final boolean p_75133_3_, final EntityPlayer p_75133_4_) {
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer playerIn, final int index) {
        if (index >= this.inventorySlots.size() - 9 && index < this.inventorySlots.size()) {
            final Slot var3 = this.inventorySlots.get(index);
            if (var3 != null && var3.getHasStack()) {
                var3.putStack(null);
            }
        }
        return null;
    }
    
    @Override
    public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
        return p_94530_2_.yDisplayPosition > 90;
    }
    
    @Override
    public boolean canDragIntoSlot(final Slot p_94531_1_) {
        return p_94531_1_.inventory instanceof InventoryPlayer || (p_94531_1_.yDisplayPosition > 90 && p_94531_1_.xDisplayPosition <= 162);
    }
}
