package com.viaversion.viaversion.velocity.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@Sharable
public class VelocityEncodeHandler extends MessageToMessageEncoder<ByteBuf> {
   private final UserConnection info;

   public VelocityEncodeHandler(UserConnection info) {
      this.info = info;
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
      if (!this.info.checkOutgoingPacket()) {
         throw CancelEncoderException.generate(null);
      } else if (!this.info.shouldTransformPacket()) {
         out.add(bytebuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);

         try {
            this.info.transformOutgoing(transformedBuf, CancelEncoderException::generate);
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
