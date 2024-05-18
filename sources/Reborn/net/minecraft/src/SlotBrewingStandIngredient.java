package net.minecraft.src;

class SlotBrewingStandIngredient extends Slot
{
    final ContainerBrewingStand brewingStand;
    
    public SlotBrewingStandIngredient(final ContainerBrewingStand par1ContainerBrewingStand, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.brewingStand = par1ContainerBrewingStand;
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return par1ItemStack != null && Item.itemsList[par1ItemStack.itemID].isPotionIngredient();
    }
    
    @Override
    public int getSlotStackLimit() {
        return 64;
    }
}
