package cc.novoline.events.events;

public enum PacketDirection {
   INCOMING,
   OUTGOING;

   private static final PacketDirection[] c = new PacketDirection[]{INCOMING, OUTGOING};
   private static String[] b;

   static {
      b(new String[4]);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }
}
