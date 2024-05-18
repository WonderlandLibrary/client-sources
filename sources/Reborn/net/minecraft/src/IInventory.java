package net.minecraft.src;

public interface IInventory
{
    int getSizeInventory();
    
    ItemStack getStackInSlot(final int p0);
    
    ItemStack decrStackSize(final int p0, final int p1);
    
    ItemStack getStackInSlotOnClosing(final int p0);
    
    void setInventorySlotContents(final int p0, final ItemStack p1);
    
    String getInvName();
    
    boolean isInvNameLocalized();
    
    int getInventoryStackLimit();
    
    void onInventoryChanged();
    
    boolean isUseableByPlayer(final EntityPlayer p0);
    
    void openChest();
    
    void closeChest();
    
    boolean isStackValidForSlot(final int p0, final ItemStack p1);
}
