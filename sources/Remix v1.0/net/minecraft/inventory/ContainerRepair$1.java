package net.minecraft.inventory;

class ContainerRepair$1 extends InventoryBasic {
    @Override
    public void markDirty() {
        super.markDirty();
        ContainerRepair.this.onCraftMatrixChanged(this);
    }
}