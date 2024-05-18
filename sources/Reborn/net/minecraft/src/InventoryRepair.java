package net.minecraft.src;

class InventoryRepair extends InventoryBasic
{
    final ContainerRepair theContainer;
    
    InventoryRepair(final ContainerRepair par1ContainerRepair, final String par2Str, final boolean par3, final int par4) {
        super(par2Str, par3, par4);
        this.theContainer = par1ContainerRepair;
    }
    
    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        this.theContainer.onCraftMatrixChanged(this);
    }
    
    @Override
    public boolean isStackValidForSlot(final int par1, final ItemStack par2ItemStack) {
        return true;
    }
}
