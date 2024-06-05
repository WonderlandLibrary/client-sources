package net;

import io.netty.channel.epoll.Epoll;

public class O5 {
   private static String[] b;

   public static boolean c() {
      return Epoll.isAvailable();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[3]);
      }

   }
}
