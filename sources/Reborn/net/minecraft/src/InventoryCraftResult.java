package net.minecraft.src;

public class InventoryCraftResult implements IInventory
{
    private ItemStack[] stackResult;
    
    public InventoryCraftResult() {
        this.stackResult = new ItemStack[1];
    }
    
    @Override
    public int getSizeInventory() {
        return 1;
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return this.stackResult[0];
    }
    
    @Override
    public String getInvName() {
        return "Result";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return false;
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.stackResult[0] != null) {
            final ItemStack var3 = this.stackResult[0];
            this.stackResult[0] = null;
            return var3;
        }
        return null;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.stackResult[0] != null) {
            final ItemStack var2 = this.stackResult[0];
            this.stackResult[0] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.stackResult[0] = par2ItemStack;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void onInventoryChanged() {
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return true;
    }
    
    @Override
    public void openChest() {
    }
    
    @Override
    public void closeChest() {
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
}
