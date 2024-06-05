package net;

import cc.novoline.gui.screen.alt.login.AltLoginThread;
import net.minecraft.util.Session;

public class vi {
   private static String[] b;

   public static Session a(AltLoginThread var0) {
      return var0.run();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[2]);
      }

   }
}
