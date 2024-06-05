package net;

import net.acE;

public class dr {
   private final int c;
   private final short d;
   private static boolean b;

   public dr(int var1, int var2) {
      this.c = var1;
      this.d = (short)var2;
   }

   public dr(int var1) {
      a();
      super();
      this.c = var1;
      this.d = 0;
      if(acE.b() == null) {
         b(false);
      }

   }

   public int d() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public dr a(int var1) {
      return new dr(this.c, var1);
   }

   public boolean equals(Object var1) {
      boolean var2 = b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         dr var3 = (dr)var1;
         return this.c != var3.c?false:this.d == var3.d;
      } else {
         return false;
      }
   }

   public int hashCode() {
      b();
      int var2 = this.c;
      var2 = 31 * var2 + this.d;
      return var2;
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(a()) {
         b(true);
      }

   }
}
