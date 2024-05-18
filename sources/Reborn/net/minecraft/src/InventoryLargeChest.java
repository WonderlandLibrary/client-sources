package net.minecraft.src;

public class InventoryLargeChest implements IInventory
{
    private String name;
    private IInventory upperChest;
    private IInventory lowerChest;
    
    public InventoryLargeChest(final String par1Str, IInventory par2IInventory, IInventory par3IInventory) {
        this.name = par1Str;
        if (par2IInventory == null) {
            par2IInventory = par3IInventory;
        }
        if (par3IInventory == null) {
            par3IInventory = par2IInventory;
        }
        this.upperChest = par2IInventory;
        this.lowerChest = par3IInventory;
    }
    
    @Override
    public int getSizeInventory() {
        return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
    }
    
    public boolean isPartOfLargeChest(final IInventory par1IInventory) {
        return this.upperChest == par1IInventory || this.lowerChest == par1IInventory;
    }
    
    @Override
    public String getInvName() {
        return this.upperChest.isInvNameLocalized() ? this.upperChest.getInvName() : (this.lowerChest.isInvNameLocalized() ? this.lowerChest.getInvName() : this.name);
    }
    
    @Override
    public boolean isInvNameLocalized() {
        return this.upperChest.isInvNameLocalized() || this.lowerChest.isInvNameLocalized();
    }
    
    @Override
    public ItemStack getStackInSlot(final int par1) {
        return (par1 >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlot(par1 - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(par1);
    }
    
    @Override
    public ItemStack decrStackSize(final int par1, final int par2) {
        return (par1 >= this.upperChest.getSizeInventory()) ? this.lowerChest.decrStackSize(par1 - this.upperChest.getSizeInventory(), par2) : this.upperChest.decrStackSize(par1, par2);
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(final int par1) {
        return (par1 >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlotOnClosing(par1 - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlotOnClosing(par1);
    }
    
    @Override
    public void setInventorySlotContents(final int par1, final ItemStack par2ItemStack) {
        if (par1 >= this.upperChest.getSizeInventory()) {
            this.lowerChest.setInventorySlotContents(par1 - this.upperChest.getSizeInventory(), par2ItemStack);
        }
        else {
            this.upperChest.setInventorySlotContents(par1, par2ItemStack);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return this.upperChest.getInventoryStackLimit();
    }
    
    @Override
    public void onInventoryChanged() {
        this.upperChest.onInventoryChanged();
        this.lowerChest.onInventoryChanged();
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return this.upperChest.isUseableByPlayer(par1EntityPlayer) && this.lowerChest.isUseableByPlayer(par1EntityPlayer);
    }
    
    @Override
    public void openChest() {
        this.upperChest.openChest();
        this.lowerChest.openChest();
    }
    
    @Override
    public void closeChest() {
        this.upperChest.closeChest();
        this.lowerChest.closeChest();
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
}
