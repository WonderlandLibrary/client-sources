package net;

import net.Nj;
import net.a2R;
import net.aOE;
import net.asJ;
import net.minecraft.entity.Entity;

public class arp {
   private static int[] b;

   public static a2R a(Entity var0) {
      return a2R.a(var0);
   }

   public static Nj a(a2R var0, String var1) {
      return var0.a(var1);
   }

   public static aOE a(asJ var0) {
      return a2R.a(var0);
   }

   public static void a() {
      a2R.c();
   }

   public static void c() {
      a2R.a();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
