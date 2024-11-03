package com.viaversion.viaversion.sponge.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SpongeDecodeHandler extends ByteToMessageDecoder {
   private final ByteToMessageDecoder minecraftDecoder;
   private final UserConnection info;

   public SpongeDecodeHandler(UserConnection info, ByteToMessageDecoder minecraftDecoder) {
      this.info = info;
      this.minecraftDecoder = minecraftDecoder;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> list) throws Exception {
      if (!this.info.checkServerboundPacket()) {
         bytebuf.clear();
         throw CancelDecoderException.generate(null);
      } else {
         ByteBuf transformedBuf = null;

         try {
            if (this.info.shouldTransformPacket()) {
               transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
               this.info.transformServerbound(transformedBuf, CancelDecoderException::generate);
            }

            try {
               list.addAll(PipelineUtil.callDecode(this.minecraftDecoder, ctx, transformedBuf == null ? bytebuf : transformedBuf));
            } catch (InvocationTargetException var9) {
               if (var9.getCause() instanceof Exception) {
                  throw (Exception)var9.getCause();
               }

               if (var9.getCause() instanceof Error) {
                  throw (Error)var9.getCause();
               }
            }
         } finally {
            if (transformedBuf != null) {
               transformedBuf.release();
            }
         }
      }
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (!(cause instanceof CancelCodecException)) {
         super.exceptionCaught(ctx, cause);
      }
   }
}
