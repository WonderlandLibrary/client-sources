package net;

import io.netty.channel.EventLoop;
import io.netty.channel.local.LocalEventLoopGroup;

public class ml {
   private static int[] b;

   public static EventLoop a(LocalEventLoopGroup var0) {
      return var0.next();
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
