package net;

public enum a66 {
   HANDSHAKE,
   STATUS,
   LOGIN,
   PLAY;

   private static int b;

   static {
      b(64);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 40;
   }
}
