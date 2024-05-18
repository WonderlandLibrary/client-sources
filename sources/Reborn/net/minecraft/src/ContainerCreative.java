package net.minecraft.src;

import java.util.*;

class ContainerCreative extends Container
{
    public List itemList;
    
    public ContainerCreative(final EntityPlayer par1EntityPlayer) {
        this.itemList = new ArrayList();
        final InventoryPlayer var2 = par1EntityPlayer.inventory;
        for (int var3 = 0; var3 < 5; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(GuiContainerCreative.getInventory(), var3 * 9 + var4, 9 + var4 * 18, 18 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(var2, var3, 9 + var3 * 18, 112));
        }
        this.scrollTo(0.0f);
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return true;
    }
    
    public void scrollTo(final float par1) {
        final int var2 = this.itemList.size() / 9 - 5 + 1;
        int var3 = (int)(par1 * var2 + 0.5);
        if (var3 < 0) {
            var3 = 0;
        }
        for (int var4 = 0; var4 < 5; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                final int var6 = var5 + (var4 + var3) * 9;
                if (var6 >= 0 && var6 < this.itemList.size()) {
                    GuiContainerCreative.getInventory().setInventorySlotContents(var5 + var4 * 9, this.itemList.get(var6));
                }
                else {
                    GuiContainerCreative.getInventory().setInventorySlotContents(var5 + var4 * 9, null);
                }
            }
        }
    }
    
    public boolean hasMoreThan1PageOfItemsInList() {
        return this.itemList.size() > 45;
    }
    
    @Override
    protected void retrySlotClick(final int par1, final int par2, final boolean par3, final EntityPlayer par4EntityPlayer) {
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
        if (par2 >= this.inventorySlots.size() - 9 && par2 < this.inventorySlots.size()) {
            final Slot var3 = this.inventorySlots.get(par2);
            if (var3 != null && var3.getHasStack()) {
                var3.putStack(null);
            }
        }
        return null;
    }
    
    @Override
    public boolean func_94530_a(final ItemStack par1ItemStack, final Slot par2Slot) {
        return par2Slot.yDisplayPosition > 90;
    }
    
    @Override
    public boolean func_94531_b(final Slot par1Slot) {
        return par1Slot.inventory instanceof InventoryPlayer || (par1Slot.yDisplayPosition > 90 && par1Slot.xDisplayPosition <= 162);
    }
}
