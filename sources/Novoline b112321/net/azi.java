package net;

public enum azi {
   SUCCESS,
   WARNING,
   ERROR,
   INFO;

   private static int[] b;

   static {
      b(new int[4]);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }
}
