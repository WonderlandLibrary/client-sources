package net.minecraft.src;

class SlotEnchantmentTable extends InventoryBasic
{
    final ContainerEnchantment container;
    
    SlotEnchantmentTable(final ContainerEnchantment par1ContainerEnchantment, final String par2Str, final boolean par3, final int par4) {
        super(par2Str, par3, par4);
        this.container = par1ContainerEnchantment;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
    
    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        this.container.onCraftMatrixChanged(this);
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
}
