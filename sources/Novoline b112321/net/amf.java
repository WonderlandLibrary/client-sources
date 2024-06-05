package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;

public class amf {
   private int a;
   private int b;
   private static String c;

   public amf(int var1, int var2) {
      b();
      this.a = var1;
      this.b = var2;
      if(PacketRemapper.b() == null) {
         b("Kb6D6b");
      }

   }

   public static amf a(int var0) {
      return new amf(var0 >> 4, var0 & 15);
   }

   public static int a(amf var0) {
      return var0.c() << 4 | var0.a() & 15;
   }

   public int c() {
      return this.a;
   }

   public int a() {
      return this.b;
   }

   public String toString() {
      String var1 = b();
      return "BlockState{id: " + this.a + ", data: " + this.b + "}";
   }

   public static void b(String var0) {
      c = var0;
   }

   public static String b() {
      return c;
   }

   static {
      b((String)null);
   }
}
