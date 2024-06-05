package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

final class Bootstrap$13 extends BehaviorDefaultDispenseItem {
   private boolean field_150838_b = true;

   protected ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      if(EnumDyeColor.WHITE == EnumDyeColor.byDyeDamage(var2.getMetadata())) {
         World var3 = var1.getWorld();
         BlockPos var4 = var1.getBlockPos().offset(BlockDispenser.getFacing(var1.getBlockMetadata()));
         if(ItemDye.applyBonemeal(var2, var3, var4)) {
            if(!var3.isRemote) {
               var3.playAuxSFX(2005, var4, 0);
            }
         } else {
            this.field_150838_b = false;
         }

         return var2;
      } else {
         return super.dispenseStack(var1, var2);
      }
   }

   protected void playDispenseSound(IBlockSource var1) {
      if(this.field_150838_b) {
         var1.getWorld().playAuxSFX(1000, var1.getBlockPos(), 0);
      } else {
         var1.getWorld().playAuxSFX(1001, var1.getBlockPos(), 0);
      }

   }
}
