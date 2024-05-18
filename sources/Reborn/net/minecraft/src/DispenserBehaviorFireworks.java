package net.minecraft.src;

final class DispenserBehaviorFireworks extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final double var4 = par1IBlockSource.getX() + var3.getFrontOffsetX();
        final double var5 = par1IBlockSource.getYInt() + 0.2f;
        final double var6 = par1IBlockSource.getZ() + var3.getFrontOffsetZ();
        final EntityFireworkRocket var7 = new EntityFireworkRocket(par1IBlockSource.getWorld(), var4, var5, var6, par2ItemStack);
        par1IBlockSource.getWorld().spawnEntityInWorld(var7);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource par1IBlockSource) {
        par1IBlockSource.getWorld().playAuxSFX(1002, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
    }
}
