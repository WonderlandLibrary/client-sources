package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

final class Bootstrap$16 extends BehaviorDefaultDispenseItem {
   private boolean field_179241_b = true;

   protected ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      World var3 = var1.getWorld();
      BlockPos var4 = var1.getBlockPos().offset(BlockDispenser.getFacing(var1.getBlockMetadata()));
      BlockPumpkin var5 = (BlockPumpkin)Blocks.pumpkin;
      if(var3.isAirBlock(var4) && var5.canDispenserPlace(var3, var4)) {
         if(!var3.isRemote) {
            var3.setBlockState(var4, var5.getDefaultState(), 3);
         }

         --var2.stackSize;
      } else {
         this.field_179241_b = false;
      }

      return var2;
   }

   protected void playDispenseSound(IBlockSource var1) {
      if(this.field_179241_b) {
         var1.getWorld().playAuxSFX(1000, var1.getBlockPos(), 0);
      } else {
         var1.getWorld().playAuxSFX(1001, var1.getBlockPos(), 0);
      }

   }
}
