package net;

public final class k8 extends IllegalArgumentException {
   private static int[] b;

   public k8() {
   }

   public k8(String var1) {
      super(var1);
   }

   public k8(String var1, Throwable var2) {
      super(var1, var2);
   }

   public k8(Throwable var1) {
      super(var1);
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
