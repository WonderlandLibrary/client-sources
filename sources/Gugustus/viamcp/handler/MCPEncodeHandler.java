package viamcp.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@ChannelHandler.Sharable
public class MCPEncodeHandler extends MessageToMessageEncoder<ByteBuf> {
   private final UserConnection info;
   private boolean handledCompression;

   public MCPEncodeHandler(UserConnection info) {
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
            boolean needsCompress = this.handleCompressionOrder(ctx, transformedBuf);
            this.info.transformOutgoing(transformedBuf, CancelEncoderException::generate);
            if (needsCompress) {
               CommonTransformer.compress(ctx, transformedBuf);
            }

            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }
      }
   }

   private boolean handleCompressionOrder(ChannelHandlerContext ctx, ByteBuf buf) throws InvocationTargetException {
      if (this.handledCompression) {
         return false;
      } else {
         int encoderIndex = ctx.pipeline().names().indexOf("compress");
         if (encoderIndex == -1) {
            return false;
         } else {
            this.handledCompression = true;
            if (encoderIndex > ctx.pipeline().names().indexOf("via-encoder")) {
               CommonTransformer.decompress(ctx, buf);
               ChannelHandler encoder = ctx.pipeline().get("via-encoder");
               ChannelHandler decoder = ctx.pipeline().get("via-decoder");
               ctx.pipeline().remove(encoder);
               ctx.pipeline().remove(decoder);
               ctx.pipeline().addAfter("compress", "via-encoder", encoder);
               ctx.pipeline().addAfter("decompress", "via-decoder", decoder);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (!PipelineUtil.containsCause(cause, CancelCodecException.class)) {
         super.exceptionCaught(ctx, cause);
      }
   }
}
