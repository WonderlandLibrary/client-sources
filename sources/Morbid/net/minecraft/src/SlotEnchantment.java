package net.minecraft.src;

class SlotEnchantment extends Slot
{
    final ContainerEnchantment container;
    
    SlotEnchantment(final ContainerEnchantment par1ContainerEnchantment, final IInventory par2IInventory, final int par3, final int par4, final int par5) {
        super(par2IInventory, par3, par4, par5);
        this.container = par1ContainerEnchantment;
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return true;
    }
}
