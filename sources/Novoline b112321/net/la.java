package net;

import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S30PacketWindowItems;

public class la {
   public static int a(S30PacketWindowItems var0) {
      return var0.getWindowID();
   }

   public static ItemStack[] b(S30PacketWindowItems var0) {
      return var0.getItemStacks();
   }
}
