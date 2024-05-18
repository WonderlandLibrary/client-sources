package net.minecraft.src;

public class Slot
{
    private final int slotIndex;
    public final IInventory inventory;
    public int slotNumber;
    public int xDisplayPosition;
    public int yDisplayPosition;
    
    public Slot(final IInventory par1IInventory, final int par2, final int par3, final int par4) {
        this.inventory = par1IInventory;
        this.slotIndex = par2;
        this.xDisplayPosition = par3;
        this.yDisplayPosition = par4;
    }
    
    public void onSlotChange(final ItemStack par1ItemStack, final ItemStack par2ItemStack) {
        if (par1ItemStack != null && par2ItemStack != null && par1ItemStack.itemID == par2ItemStack.itemID) {
            final int var3 = par2ItemStack.stackSize - par1ItemStack.stackSize;
            if (var3 > 0) {
                this.onCrafting(par1ItemStack, var3);
            }
        }
    }
    
    protected void onCrafting(final ItemStack par1ItemStack, final int par2) {
    }
    
    protected void onCrafting(final ItemStack par1ItemStack) {
    }
    
    public void onPickupFromSlot(final EntityPlayer par1EntityPlayer, final ItemStack par2ItemStack) {
        this.onSlotChanged();
    }
    
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return true;
    }
    
    public ItemStack getStack() {
        return this.inventory.getStackInSlot(this.slotIndex);
    }
    
    public boolean getHasStack() {
        return this.getStack() != null;
    }
    
    public void putStack(final ItemStack par1ItemStack) {
        this.inventory.setInventorySlotContents(this.slotIndex, par1ItemStack);
        this.onSlotChanged();
    }
    
    public void onSlotChanged() {
        this.inventory.onInventoryChanged();
    }
    
    public int getSlotStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }
    
    public Icon getBackgroundIconIndex() {
        return null;
    }
    
    public ItemStack decrStackSize(final int par1) {
        return this.inventory.decrStackSize(this.slotIndex, par1);
    }
    
    public boolean isSlotInInventory(final IInventory par1IInventory, final int par2) {
        return par1IInventory == this.inventory && par2 == this.slotIndex;
    }
    
    public boolean canTakeStack(final EntityPlayer par1EntityPlayer) {
        return true;
    }
}
