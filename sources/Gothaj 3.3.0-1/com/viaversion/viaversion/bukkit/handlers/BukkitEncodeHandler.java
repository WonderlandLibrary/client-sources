package com.viaversion.viaversion.bukkit.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.bukkit.util.NMSUtil;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@Sharable
public final class BukkitEncodeHandler extends MessageToMessageEncoder<ByteBuf> {
   private final UserConnection connection;
   private boolean handledCompression = BukkitChannelInitializer.COMPRESSION_ENABLED_EVENT != null;

   public BukkitEncodeHandler(UserConnection connection) {
      this.connection = connection;
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
      if (!this.connection.checkClientboundPacket()) {
         throw CancelEncoderException.generate(null);
      } else if (!this.connection.shouldTransformPacket()) {
         out.add(bytebuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);

         try {
            boolean needsCompression = !this.handledCompression && this.handleCompressionOrder(ctx, transformedBuf);
            this.connection.transformClientbound(transformedBuf, CancelEncoderException::generate);
            if (needsCompression) {
               this.recompress(ctx, transformedBuf);
            }

            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }
      }
   }

   private boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
      ChannelPipeline pipeline = ctx.pipeline();
      List<String> names = pipeline.names();
      int compressorIndex = names.indexOf("compress");
      if (compressorIndex == -1) {
         return false;
      } else {
         this.handledCompression = true;
         if (compressorIndex > names.indexOf("via-encoder")) {
            ByteBuf decompressed = (ByteBuf)PipelineUtil.callDecode((ByteToMessageDecoder)pipeline.get("decompress"), ctx, buf).get(0);

            try {
               buf.clear().writeBytes(decompressed);
            } finally {
               decompressed.release();
            }

            pipeline.addAfter("compress", "via-encoder", pipeline.remove("via-encoder"));
            pipeline.addAfter("decompress", "via-decoder", pipeline.remove("via-decoder"));
            return true;
         } else {
            return false;
         }
      }
   }

   private void recompress(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
      ByteBuf compressed = ctx.alloc().buffer();

      try {
         PipelineUtil.callEncode((MessageToByteEncoder)ctx.pipeline().get("compress"), ctx, buf, compressed);
         buf.clear().writeBytes(compressed);
      } finally {
         compressed.release();
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

   public UserConnection connection() {
      return this.connection;
   }
}
