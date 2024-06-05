package net.minecraft.network.play.server;

public enum S45PacketTitle$Type {
   TITLE,
   SUBTITLE,
   TIMES,
   CLEAR,
   RESET;

   private static final S45PacketTitle$Type[] $VALUES = new S45PacketTitle$Type[]{TITLE, SUBTITLE, TIMES, CLEAR, RESET};

   public static S45PacketTitle$Type byName(String var0) {
      for(S45PacketTitle$Type var4 : values()) {
         if(var4.name().equalsIgnoreCase(var0)) {
            return var4;
         }
      }

      return TITLE;
   }

   public static String[] getNames() {
      String[] var0 = new String[values().length];
      int var1 = 0;

      for(S45PacketTitle$Type var5 : values()) {
         var0[var1++] = var5.name().toLowerCase();
      }

      return var0;
   }
}
