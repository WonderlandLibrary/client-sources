package com.viaversion.viaversion.bungee.handlers;

import com.viaversion.viaversion.exception.CancelDecoderException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import java.util.function.Function;
import net.aAu;
import net.bgR;
import net.l_;

public class BungeeDecodeHandler extends MessageToMessageDecoder {
   private final bgR a;

   public BungeeDecodeHandler(bgR var1) {
      this.a = var1;
   }

   protected void decode(ChannelHandlerContext var1, ByteBuf var2, List var3) throws Exception {
      l_.b();
      this.a.f();
      if(!this.a.y()) {
         var3.add(var2.retain());
      } else {
         ByteBuf var5 = var1.alloc().buffer().writeBytes(var2);
         BungeeDecodeHandler var10000 = this;

         try {
            var10000.a.b(var5, CancelDecoderException::generate);
            var3.add(var5.retain());
         } finally {
            var5.release();
         }

      }
   }

   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) throws Exception {
      int[] var3 = l_.b();
      if(!(var2 instanceof aAu)) {
         super.exceptionCaught(var1, var2);
      }
   }

   public bgR a() {
      return this.a;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
