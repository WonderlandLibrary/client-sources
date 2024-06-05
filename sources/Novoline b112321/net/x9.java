package net;

import net.minecraft.event.HoverEvent$Action;

public class x9 {
   public static HoverEvent$Action a(String var0) {
      return HoverEvent$Action.getValueByCanonicalName(var0);
   }

   public static boolean b(HoverEvent$Action var0) {
      return var0.shouldAllowInChat();
   }

   public static String a(HoverEvent$Action var0) {
      return var0.getCanonicalName();
   }
}
