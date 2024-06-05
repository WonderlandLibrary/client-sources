package net;

import net.minecraft.client.multiplayer.ThreadLanServerPing;

public class ab_ {
   public static String a(String var0) {
      return ThreadLanServerPing.b(var0);
   }

   public static String b(String var0) {
      return ThreadLanServerPing.a(var0);
   }

   public static void a(ThreadLanServerPing var0) {
      var0.interrupt();
   }
}
