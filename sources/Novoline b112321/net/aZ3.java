package net;

import viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers.FlowerPotHandler;

public class aZ3 {
   private static int[] b;

   public static boolean a(int var0) {
      return FlowerPotHandler.isFlowah(var0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[1]);
      }

   }
}
