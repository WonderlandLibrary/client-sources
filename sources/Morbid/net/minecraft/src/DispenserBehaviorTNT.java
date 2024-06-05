package net.minecraft.src;

final class DispenserBehaviorTNT extends BehaviorDefaultDispenseItem
{
    @Override
    protected ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final World var4 = par1IBlockSource.getWorld();
        final int var5 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
        final int var6 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
        final int var7 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
        final EntityTNTPrimed var8 = new EntityTNTPrimed(var4, var5 + 0.5f, var6 + 0.5f, var7 + 0.5f, null);
        var4.spawnEntityInWorld(var8);
        --par2ItemStack.stackSize;
        return par2ItemStack;
    }
}
