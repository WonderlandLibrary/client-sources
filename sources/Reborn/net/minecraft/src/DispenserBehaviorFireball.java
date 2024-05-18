package net.minecraft.src;

import java.util.*;

final class DispenserBehaviorFireball extends BehaviorDefaultDispenseItem
{
    public ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final IPosition var4 = BlockDispenser.getIPositionFromBlockSource(par1IBlockSource);
        final double var5 = var4.getX() + var3.getFrontOffsetX() * 0.3f;
        final double var6 = var4.getY() + var3.getFrontOffsetX() * 0.3f;
        final double var7 = var4.getZ() + var3.getFrontOffsetZ() * 0.3f;
        final World var8 = par1IBlockSource.getWorld();
        final Random var9 = var8.rand;
        final double var10 = var9.nextGaussian() * 0.05 + var3.getFrontOffsetX();
        final double var11 = var9.nextGaussian() * 0.05 + var3.getFrontOffsetY();
        final double var12 = var9.nextGaussian() * 0.05 + var3.getFrontOffsetZ();
        var8.spawnEntityInWorld(new EntitySmallFireball(var8, var5, var6, var7, var10, var11, var12));
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource par1IBlockSource) {
        par1IBlockSource.getWorld().playAuxSFX(1009, par1IBlockSource.getXInt(), par1IBlockSource.getYInt(), par1IBlockSource.getZInt(), 0);
    }
}
