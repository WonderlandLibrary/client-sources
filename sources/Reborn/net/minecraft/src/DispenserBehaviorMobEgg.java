package net.minecraft.src;

final class DispenserBehaviorMobEgg extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final double var4 = par1IBlockSource.getX() + var3.getFrontOffsetX();
        final double var5 = par1IBlockSource.getYInt() + 0.2f;
        final double var6 = par1IBlockSource.getZ() + var3.getFrontOffsetZ();
        final Entity var7 = ItemMonsterPlacer.spawnCreature(par1IBlockSource.getWorld(), par2ItemStack.getItemDamage(), var4, var5, var6);
        if (var7 instanceof EntityLiving && par2ItemStack.hasDisplayName()) {
            ((EntityLiving)var7).func_94058_c(par2ItemStack.getDisplayName());
        }
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
}
