package net.minecraft.src;

class DispenserBehaviorPotionProjectile extends BehaviorProjectileDispense
{
    final ItemStack potionItemStack;
    final DispenserBehaviorPotion dispenserPotionBehavior;
    
    DispenserBehaviorPotionProjectile(final DispenserBehaviorPotion par1DispenserBehaviorPotion, final ItemStack par2ItemStack) {
        this.dispenserPotionBehavior = par1DispenserBehaviorPotion;
        this.potionItemStack = par2ItemStack;
    }
    
    @Override
    protected IProjectile getProjectileEntity(final World par1World, final IPosition par2IPosition) {
        return new EntityPotion(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ(), this.potionItemStack.copy());
    }
    
    @Override
    protected float func_82498_a() {
        return super.func_82498_a() * 0.5f;
    }
    
    @Override
    protected float func_82500_b() {
        return super.func_82500_b() * 1.25f;
    }
}
