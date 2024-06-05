package net.minecraft.inventory;

class ContainerEnchantment$1 extends InventoryBasic {
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void markDirty() {
        super.markDirty();
        ContainerEnchantment.this.onCraftMatrixChanged(this);
    }
}