package net;

import net.acE;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent$Action;

public class amC {
   private static acE[] b;

   public static ClickEvent$Action b(ClickEvent var0) {
      return var0.getAction();
   }

   public static String a(ClickEvent var0) {
      return var0.getValue();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[2]);
      }

   }
}
