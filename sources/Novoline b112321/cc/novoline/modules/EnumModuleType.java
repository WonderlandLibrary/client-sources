package cc.novoline.modules;

public enum EnumModuleType {
   COMBAT,
   MOVEMENT,
   PLAYER,
   VISUALS,
   EXPLOITS,
   MISC;

   private static int[] b;

   static {
      b((int[])null);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }
}
