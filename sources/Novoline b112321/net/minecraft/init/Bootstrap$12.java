package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockTNT;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

final class Bootstrap$12 extends BehaviorDefaultDispenseItem {
   private boolean field_150839_b = true;

   protected ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      World var3 = var1.getWorld();
      BlockPos var4 = var1.getBlockPos().offset(BlockDispenser.getFacing(var1.getBlockMetadata()));
      if(var3.isAirBlock(var4)) {
         var3.setBlockState(var4, Blocks.fire.getDefaultState());
         if(var2.attemptDamageItem(1, var3.rand)) {
            var2.stackSize = 0;
         }
      } else if(var3.getBlockState(var4).getBlock() == Blocks.tnt) {
         Blocks.tnt.onBlockDestroyedByPlayer(var3, var4, Blocks.tnt.getDefaultState().withProperty(BlockTNT.EXPLODE, Boolean.TRUE));
         var3.setBlockToAir(var4);
      } else {
         this.field_150839_b = false;
      }

      return var2;
   }

   protected void playDispenseSound(IBlockSource var1) {
      if(this.field_150839_b) {
         var1.getWorld().playAuxSFX(1000, var1.getBlockPos(), 0);
      } else {
         var1.getWorld().playAuxSFX(1001, var1.getBlockPos(), 0);
      }

   }
}
