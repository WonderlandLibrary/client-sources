package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import viaversion.viaversion.handlers.ViaHandler;

public class n1 {
   private static int b;

   public static void a(ViaHandler var0, ByteBuf var1) {
      var0.transform(var1);
   }

   public static void a(ViaHandler var0, ChannelHandlerContext var1, Throwable var2) {
      var0.exceptionCaught(var1, var2);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 89;
   }

   static {
      if(b() == 0) {
         b(65);
      }

   }
}
