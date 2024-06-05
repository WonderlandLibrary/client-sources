package net;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.base64.Base64;

public class agy {
   private static String b;

   public static ByteBuf a(ByteBuf var0) {
      return Base64.encode(var0);
   }

   public static ByteBuf b(ByteBuf var0) {
      return Base64.decode(var0);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("GV1k");
      }

   }
}
