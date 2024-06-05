package net.minecraft.entity.player;

import java.util.concurrent.Callable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

class InventoryPlayer$1 implements Callable {
   final ItemStack val$itemStackIn;
   final InventoryPlayer this$0;

   InventoryPlayer$1(InventoryPlayer var1, ItemStack var2) {
      this.this$0 = var1;
      this.val$itemStackIn = var2;
   }

   public String call() throws Exception {
      return this.val$itemStackIn.getDisplayName();
   }
}
