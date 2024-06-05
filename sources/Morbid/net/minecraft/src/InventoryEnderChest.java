package net.minecraft.src;

public class InventoryEnderChest extends InventoryBasic
{
    private TileEntityEnderChest associatedChest;
    
    public InventoryEnderChest() {
        super("container.enderchest", false, 27);
    }
    
    public void setAssociatedChest(final TileEntityEnderChest par1TileEntityEnderChest) {
        this.associatedChest = par1TileEntityEnderChest;
    }
    
    public void loadInventoryFromNBT(final NBTTagList par1NBTTagList) {
        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            this.setInventorySlotContents(var2, null);
        }
        for (int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2) {
            final NBTTagCompound var3 = (NBTTagCompound)par1NBTTagList.tagAt(var2);
            final int var4 = var3.getByte("Slot") & 0xFF;
            if (var4 >= 0 && var4 < this.getSizeInventory()) {
                this.setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
            }
        }
    }
    
    public NBTTagList saveInventoryToNBT() {
        final NBTTagList var1 = new NBTTagList("EnderItems");
        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            final ItemStack var3 = this.getStackInSlot(var2);
            if (var3 != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var2);
                var3.writeToNBT(var4);
                var1.appendTag(var4);
            }
        }
        return var1;
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer par1EntityPlayer) {
        return (this.associatedChest == null || this.associatedChest.isUseableByPlayer(par1EntityPlayer)) && super.isUseableByPlayer(par1EntityPlayer);
    }
    
    @Override
    public void openChest() {
        if (this.associatedChest != null) {
            this.associatedChest.openChest();
        }
        super.openChest();
    }
    
    @Override
    public void closeChest() {
        if (this.associatedChest != null) {
            this.associatedChest.closeChest();
        }
        super.closeChest();
        this.associatedChest = null;
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
}
