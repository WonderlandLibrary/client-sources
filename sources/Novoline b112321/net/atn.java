package net;

import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.IChatComponent;

public class atn {
   private static boolean b;

   public static boolean a(S02PacketChat var0) {
      return var0.isChat();
   }

   public static IChatComponent c(S02PacketChat var0) {
      return var0.getChatComponent();
   }

   public static byte b(S02PacketChat var0) {
      return var0.getType();
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(b()) {
         b(true);
      }

   }
}
