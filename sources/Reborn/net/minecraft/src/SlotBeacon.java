package net.minecraft.src;

class SlotBeacon extends Slot
{
    final ContainerBeacon beacon;
    
    public SlotBeacon(final ContainerBeacon par1ContainerBeacon, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.beacon = par1ContainerBeacon;
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return par1ItemStack != null && (par1ItemStack.itemID == Item.emerald.itemID || par1ItemStack.itemID == Item.diamond.itemID || par1ItemStack.itemID == Item.ingotGold.itemID || par1ItemStack.itemID == Item.ingotIron.itemID);
    }
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
