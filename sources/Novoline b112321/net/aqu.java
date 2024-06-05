package net;

import net.ayd;

public abstract class aqu {
   protected final ayd c;
   private static int b;

   protected aqu(ayd var1) {
      this.c = var1;
   }

   public void f() {
      this.registerPackets();
      this.registerRewrites();
   }

   protected abstract void registerPackets();

   protected void registerRewrites() {
   }

   public ayd c() {
      return this.c;
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int e() {
      return b;
   }

   public static int d() {
      int var0 = e();
      return 53;
   }

   static {
      if(e() != 0) {
         b(84);
      }

   }
}
