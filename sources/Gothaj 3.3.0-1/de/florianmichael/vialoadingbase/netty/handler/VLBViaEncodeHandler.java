package de.florianmichael.vialoadingbase.netty.handler;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;

@Sharable
public class VLBViaEncodeHandler extends MessageToMessageEncoder<ByteBuf> {
   private final UserConnection user;

   public VLBViaEncodeHandler(UserConnection user) {
      this.user = user;
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
      if (!this.user.checkOutgoingPacket()) {
         throw CancelEncoderException.generate(null);
      } else if (!this.user.shouldTransformPacket()) {
         out.add(bytebuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);

         try {
            this.user.transformOutgoing(transformedBuf, CancelEncoderException::generate);
            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }
      }
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (!PipelineUtil.containsCause(cause, CancelCodecException.class)) {
         super.exceptionCaught(ctx, cause);
      }
   }
}
