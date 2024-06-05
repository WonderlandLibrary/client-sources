package net;

import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsScreen;

public class axm {
   private static String[] b;

   public static void a(boolean var0) {
      Realms.setConnectedToRealms(var0);
   }

   public static void c() {
      Realms.clearResourcePack();
   }

   public static void a(RealmsScreen var0) {
      Realms.setScreen(var0);
   }

   public static long a() {
      return Realms.currentTimeMillis();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[4]);
      }

   }
}
