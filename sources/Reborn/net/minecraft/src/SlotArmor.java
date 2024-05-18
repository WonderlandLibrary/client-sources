package net.minecraft.src;

class SlotArmor extends Slot
{
    final int armorType;
    final ContainerPlayer parent;
    
    SlotArmor(final ContainerPlayer par1ContainerPlayer, final IInventory par2IInventory, final int par3, final int par4, final int par5, final int par6) {
        super(par2IInventory, par3, par4, par5);
        this.parent = par1ContainerPlayer;
        this.armorType = par6;
    }
    
    @Override
    public int getSlotStackLimit() {
        return 1;
    }
    
    @Override
    public boolean isItemValid(final ItemStack par1ItemStack) {
        return par1ItemStack != null && ((par1ItemStack.getItem() instanceof ItemArmor) ? (((ItemArmor)par1ItemStack.getItem()).armorType == this.armorType) : ((par1ItemStack.getItem().itemID == Block.pumpkin.blockID || par1ItemStack.getItem().itemID == Item.skull.itemID) && this.armorType == 0));
    }
    
    @Override
    public Icon getBackgroundIconIndex() {
        return ItemArmor.func_94602_b(this.armorType);
    }
}
