package net.minecraft.src;

public class ContainerHopper extends Container
{
    private final IInventory field_94538_a;
    
    public ContainerHopper(final InventoryPlayer par1InventoryPlayer, final IInventory par2IInventory) {
        (this.field_94538_a = par2IInventory).openChest();
        final byte var3 = 51;
        for (int var4 = 0; var4 < par2IInventory.getSizeInventory(); ++var4) {
            this.addSlotToContainer(new Slot(par2IInventory, var4, 44 + var4 * 18, 20));
        }
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var5 + var4 * 9 + 9, 8 + var5 * 18, var4 * 18 + var3));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 58 + var3));
        }
    }
    
    @Override
    public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
        return this.field_94538_a.isUseableByPlayer(par1EntityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
        ItemStack var3 = null;
        final Slot var4 = this.inventorySlots.get(par2);
        if (var4 != null && var4.getHasStack()) {
            final ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (par2 < this.field_94538_a.getSizeInventory()) {
                if (!this.mergeItemStack(var5, this.field_94538_a.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 0, this.field_94538_a.getSizeInventory(), false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            }
            else {
                var4.onSlotChanged();
            }
        }
        return var3;
    }
    
    @Override
    public void onCraftGuiClosed(final EntityPlayer par1EntityPlayer) {
        super.onCraftGuiClosed(par1EntityPlayer);
        this.field_94538_a.closeChest();
    }
}
