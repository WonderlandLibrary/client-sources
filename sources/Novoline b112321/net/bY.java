package net;

public enum bY {
   TITLE,
   ACTION_BAR,
   BOSS_BAR,
   DISABLED;

   private static final bY[] a = new bY[]{TITLE, ACTION_BAR, BOSS_BAR, DISABLED};
   private static int b;

   static {
      b(0);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 4;
   }
}
