package com.viaversion.viaversion.velocity.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@Sharable
public class VelocityDecodeHandler extends MessageToMessageDecoder<ByteBuf> {
   private final UserConnection info;

   public VelocityDecodeHandler(UserConnection info) {
      this.info = info;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
      if (!this.info.checkIncomingPacket()) {
         throw CancelDecoderException.generate(null);
      } else if (!this.info.shouldTransformPacket()) {
         out.add(bytebuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);

         try {
            this.info.transformIncoming(transformedBuf, CancelDecoderException::generate);
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

   public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
      if (event != VelocityChannelInitializer.COMPRESSION_ENABLED_EVENT) {
         super.userEventTriggered(ctx, event);
      } else {
         ChannelPipeline pipeline = ctx.pipeline();
         ChannelHandler encoder = pipeline.remove("via-encoder");
         pipeline.addBefore("minecraft-encoder", "via-encoder", encoder);
         ChannelHandler decoder = pipeline.remove("via-decoder");
         pipeline.addBefore("minecraft-decoder", "via-decoder", decoder);
         super.userEventTriggered(ctx, event);
      }
   }
}
