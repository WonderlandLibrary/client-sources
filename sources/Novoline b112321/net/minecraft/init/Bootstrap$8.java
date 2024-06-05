package net.minecraft.init;

import java.util.Random;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

final class Bootstrap$8 extends BehaviorDefaultDispenseItem {
   public ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      EnumFacing var3 = BlockDispenser.getFacing(var1.getBlockMetadata());
      IPosition var4 = BlockDispenser.getDispensePosition(var1);
      double var5 = var4.getX() + (double)((float)var3.getFrontOffsetX() * 0.3F);
      double var7 = var4.getY() + (double)((float)var3.getFrontOffsetY() * 0.3F);
      double var9 = var4.getZ() + (double)((float)var3.getFrontOffsetZ() * 0.3F);
      World var11 = var1.getWorld();
      Random var12 = var11.rand;
      double var13 = var12.nextGaussian() * 0.05D + (double)var3.getFrontOffsetX();
      double var15 = var12.nextGaussian() * 0.05D + (double)var3.getFrontOffsetY();
      double var17 = var12.nextGaussian() * 0.05D + (double)var3.getFrontOffsetZ();
      var11.spawnEntityInWorld(new EntitySmallFireball(var11, var5, var7, var9, var13, var15, var17));
      var2.splitStack(1);
      return var2;
   }

   protected void playDispenseSound(IBlockSource var1) {
      var1.getWorld().playAuxSFX(1009, var1.getBlockPos(), 0);
   }
}
