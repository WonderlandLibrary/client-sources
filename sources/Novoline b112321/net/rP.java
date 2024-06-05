package net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.Charset;

public class rP {
   public static ByteBuf a(ByteBuf var0) {
      return Unpooled.wrappedBuffer(var0);
   }

   public static ByteBuf a(byte[] var0) {
      return Unpooled.wrappedBuffer(var0);
   }

   public static ByteBuf a() {
      return Unpooled.buffer();
   }

   public static ByteBuf a(int var0) {
      return Unpooled.buffer(var0);
   }

   public static ByteBuf a(CharSequence var0, Charset var1) {
      return Unpooled.copiedBuffer(var0, var1);
   }
}
