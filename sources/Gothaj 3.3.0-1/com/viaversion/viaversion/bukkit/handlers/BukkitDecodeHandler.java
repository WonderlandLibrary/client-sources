package com.viaversion.viaversion.bukkit.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@Sharable
public final class BukkitDecodeHandler extends MessageToMessageDecoder<ByteBuf> {
   private final UserConnection connection;

   public BukkitDecodeHandler(UserConnection connection) {
      this.connection = connection;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
      if (!this.connection.checkServerboundPacket()) {
         throw CancelDecoderException.generate(null);
      } else if (!this.connection.shouldTransformPacket()) {
         out.add(bytebuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);

         try {
            this.connection.transformIncoming(transformedBuf, CancelDecoderException::generate);
            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }
      }
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (!PipelineUtil.containsCause(cause, CancelCodecException.class)) {
         super.exceptionCaught(ctx, cause);
         if (!NMSUtil.isDebugPropertySet()) {
            InformativeException exception = PipelineUtil.getCause(cause, InformativeException.class);
            if (exception != null && exception.shouldBePrinted()) {
               cause.printStackTrace();
               exception.setShouldBePrinted(false);
            }
         }
      }
   }

   public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
      if (BukkitChannelInitializer.COMPRESSION_ENABLED_EVENT != null && event == BukkitChannelInitializer.COMPRESSION_ENABLED_EVENT) {
         ChannelPipeline pipeline = ctx.pipeline();
         pipeline.addAfter("compress", "via-encoder", pipeline.remove("via-encoder"));
         pipeline.addAfter("decompress", "via-decoder", pipeline.remove("via-decoder"));
         super.userEventTriggered(ctx, event);
      } else {
         super.userEventTriggered(ctx, event);
      }
   }
}
