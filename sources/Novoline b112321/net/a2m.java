package net;

import net.minecraft.block.Block;
import net.minecraft.world.NextTickListEntry;

public class a2m {
   public static Block a(NextTickListEntry var0) {
      return var0.getBlock();
   }

   public static NextTickListEntry a(NextTickListEntry var0, long var1) {
      return var0.setScheduledTime(var1);
   }

   public static void a(NextTickListEntry var0, int var1) {
      var0.setPriority(var1);
   }
}
