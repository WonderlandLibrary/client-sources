package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.gR;

public class aa9 extends RuntimeException {
   private final gR c;
   private static int b;

   public aa9(gR var1) {
      this.c = var1;
   }

   public aa9(String var1, gR var2) {
      super(var1);
      this.c = var2;
   }

   public aa9(String var1, Throwable var2, gR var3) {
      super(var1, var2);
      b();
      this.c = var3;
   }

   public aa9(Throwable var1, gR var2) {
      super(var1);
      int var10000 = c();
      this.c = var2;
      int var3 = var10000;
      if(PacketRemapper.b() == null) {
         ++var3;
         b(var3);
      }

   }

   public gR a() {
      return this.c;
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 92;
   }

   private static aa9 a(aa9 var0) {
      return var0;
   }

   static {
      if(c() != 0) {
         b(54);
      }

   }
}
