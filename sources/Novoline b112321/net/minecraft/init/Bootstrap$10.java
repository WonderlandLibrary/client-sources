package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

final class Bootstrap$10 extends BehaviorDefaultDispenseItem {
   private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();

   public ItemStack dispenseStack(IBlockSource var1, ItemStack var2) {
      ItemBucket var3 = (ItemBucket)var2.getItem();
      BlockPos var4 = var1.getBlockPos().offset(BlockDispenser.getFacing(var1.getBlockMetadata()));
      if(var3.tryPlaceContainedLiquid(var1.getWorld(), var4)) {
         var2.setItem(Items.bucket);
         var2.stackSize = 1;
         return var2;
      } else {
         return this.field_150841_b.dispense(var1, var2);
      }
   }
}
