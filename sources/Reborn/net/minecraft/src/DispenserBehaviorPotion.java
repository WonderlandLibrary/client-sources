package net.minecraft.src;

final class DispenserBehaviorPotion implements IBehaviorDispenseItem
{
    private final BehaviorDefaultDispenseItem defaultDispenserItemBehavior;
    
    DispenserBehaviorPotion() {
        this.defaultDispenserItemBehavior = new BehaviorDefaultDispenseItem();
    }
    
    @Override
    public ItemStack dispense(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        return ItemPotion.isSplash(par2ItemStack.getItemDamage()) ? new DispenserBehaviorPotionProjectile(this, par2ItemStack).dispense(par1IBlockSource, par2ItemStack) : this.defaultDispenserItemBehavior.dispense(par1IBlockSource, par2ItemStack);
    }
}
