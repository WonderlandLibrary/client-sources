package com.viaversion.viaversion.bungee.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@Sharable
public class BungeeDecodeHandler extends MessageToMessageDecoder<ByteBuf> {
   private final UserConnection info;

   public BungeeDecodeHandler(UserConnection info) {
      this.info = info;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
      if (!ctx.channel().isActive()) {
         throw CancelDecoderException.generate(null);
      } else if (!this.info.checkServerboundPacket()) {
         throw CancelDecoderException.generate(null);
      } else if (!this.info.shouldTransformPacket()) {
         out.add(bytebuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);

         try {
            this.info.transformServerbound(transformedBuf, CancelDecoderException::generate);
            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }
      }
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (!(cause instanceof CancelCodecException)) {
         super.exceptionCaught(ctx, cause);
      }
   }
}
