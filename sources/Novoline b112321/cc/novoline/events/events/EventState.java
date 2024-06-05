package cc.novoline.events.events;

public enum EventState {
   PRE,
   POST;

   private static final EventState[] a = new EventState[]{PRE, POST};
   private static boolean b;

   static {
      b(false);
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
}
