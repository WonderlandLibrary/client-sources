package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import java.util.function.Function;
import net.aAu;
import net.acE;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.exception.CancelEncoderException;

public class l_ extends MessageToMessageEncoder {
   private final UserConnection a;
   private static int[] b;

   public l_(UserConnection var1) {
      this.a = var1;
   }

   protected void a(ChannelHandlerContext var1, ByteBuf var2, List var3) throws Exception {
      int[] var4 = b();
      if(!this.a.checkIncomingPacket()) {
         throw CancelEncoderException.generate((Throwable)null);
      } else if(!this.a.shouldTransformPacket()) {
         var3.add(var2.retain());
      } else {
         ByteBuf var5 = var1.alloc().buffer().writeBytes(var2);
         l_ var10000 = this;

         try {
            var10000.a.transformIncoming(var5, CancelEncoderException::generate);
            var3.add(var5.retain());
         } finally {
            var5.release();
         }

         if(acE.b() == null) {
            b(new int[3]);
         }

      }
   }

   public void exceptionCaught(ChannelHandlerContext var1, Throwable var2) throws Exception {
      int[] var3 = b();
      if(!(var2 instanceof aAu)) {
         super.exceptionCaught(var1, var2);
      }
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      if(b() == null) {
         b(new int[2]);
      }

   }
}
