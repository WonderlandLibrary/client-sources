package net.minecraft.src;

import java.util.*;

final class BehaviorDispenseArmor extends BehaviorDefaultDispenseItem
{
    @Override
    protected ItemStack dispenseStack(final IBlockSource par1IBlockSource, final ItemStack par2ItemStack) {
        final EnumFacing var3 = BlockDispenser.getFacing(par1IBlockSource.getBlockMetadata());
        final int var4 = par1IBlockSource.getXInt() + var3.getFrontOffsetX();
        final int var5 = par1IBlockSource.getYInt() + var3.getFrontOffsetY();
        final int var6 = par1IBlockSource.getZInt() + var3.getFrontOffsetZ();
        final AxisAlignedBB var7 = AxisAlignedBB.getAABBPool().getAABB(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1);
        final List var8 = par1IBlockSource.getWorld().selectEntitiesWithinAABB(EntityLiving.class, var7, new EntitySelectorArmoredMob(par2ItemStack));
        if (var8.size() > 0) {
            final EntityLiving var9 = var8.get(0);
            final int var10 = (var9 instanceof EntityPlayer) ? 1 : 0;
            final int var11 = EntityLiving.getArmorPosition(par2ItemStack);
            final ItemStack var12 = par2ItemStack.copy();
            var12.stackSize = 1;
            var9.setCurrentItemOrArmor(var11 - var10, var12);
            var9.func_96120_a(var11, 2.0f);
            --par2ItemStack.stackSize;
            return par2ItemStack;
        }
        return super.dispenseStack(par1IBlockSource, par2ItemStack);
    }
}
