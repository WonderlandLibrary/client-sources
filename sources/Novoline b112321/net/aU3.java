package net;

import net.minecraft.block.BlockJukebox$TileEntityJukebox;
import net.minecraft.item.ItemStack;

public class aU3 {
   public static void a(BlockJukebox$TileEntityJukebox var0, ItemStack var1) {
      var0.setRecord(var1);
   }

   public static ItemStack a(BlockJukebox$TileEntityJukebox var0) {
      return var0.getRecord();
   }
}
