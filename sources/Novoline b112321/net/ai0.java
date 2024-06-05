package net;

import cc.novoline.modules.exploits.Blink;
import net.minecraft.client.entity.EntityOtherPlayerMP;

public class ai0 {
   private static String[] b;

   public static EntityOtherPlayerMP a(Blink var0) {
      return var0.getBlinkEntity();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[3]);
      }

   }
}
