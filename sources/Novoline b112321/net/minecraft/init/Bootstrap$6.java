package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

final class Bootstrap$6 extends BehaviorDefaultDispenseItem {
   public ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      EnumFacing var3 = BlockDispenser.getFacing(var1.getBlockMetadata());
      double var4 = var1.getX() + (double)var3.getFrontOffsetX();
      double var6 = (double)((float)var1.getBlockPos().getY() + 0.2F);
      double var8 = var1.getZ() + (double)var3.getFrontOffsetZ();
      Entity var10 = ItemMonsterPlacer.spawnCreature(var1.getWorld(), var2.getMetadata(), var4, var6, var8);
      if(var10 instanceof EntityLivingBase && var2.hasDisplayName()) {
         var10.setCustomNameTag(var2.getDisplayName());
      }

      var2.splitStack(1);
      return var2;
   }
}
