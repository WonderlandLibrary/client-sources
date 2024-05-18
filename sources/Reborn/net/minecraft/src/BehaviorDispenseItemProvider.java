package net.minecraft.src;

final class BehaviorDispenseItemProvider implements IBehaviorDispenseItem
{
    @Override
    public ItemStack dispense(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        return par2ItemStack;
    }
}
