package net.minecraft.src;

public class InventoryCrafting implements IInventory
{
    private ItemStack[] stackList;
    private int inventoryWidth;
    private Container eventHandler;
    
    public InventoryCrafting(final Container par1Container, final int par2, final int par3) {
        final int var4 = par2 * par3;
        this.stackList = new ItemStack[var4];
        this.eventHandler = par1Container;
        this.inventoryWidth = par2;
    }
    
    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return (par1 >= this.getSizeInventory()) ? null : this.stackList[par1];
    }
    
    public ItemStack getStackInRowAndColumn(final int par1, final int par2) {
        if (par1 >= 0 && par1 < this.inventoryWidth) {
            final int var3 = par1 + par2 * this.inventoryWidth;
            return this.getStackInSlot(var3);
        }
        return null;
    }
    
    @Override
    public String getInvName() {
        return "container.crafting";
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return false;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        if (this.stackList[par1] != null) {
            final ItemStack var2 = this.stackList[par1];
            this.stackList[par1] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        if (this.stackList[par1] == null) {
            return null;
        }
        if (this.stackList[par1].stackSize <= par2) {
            final ItemStack var3 = this.stackList[par1];
            this.stackList[par1] = null;
            this.eventHandler.onCraftMatrixChanged(this);
            return var3;
        }
        final ItemStack var3 = this.stackList[par1].splitStack(par2);
        if (this.stackList[par1].stackSize == 0) {
            this.stackList[par1] = null;
        }
        this.eventHandler.onCraftMatrixChanged(this);
        return var3;
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        this.stackList[par1] = par2ItemStack;
        this.eventHandler.onCraftMatrixChanged(this);
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
