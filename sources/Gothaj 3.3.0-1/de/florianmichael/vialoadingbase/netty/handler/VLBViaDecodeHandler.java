package de.florianmichael.vialoadingbase.netty.handler;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelDecoderException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;

@Sharable
public class VLBViaDecodeHandler extends MessageToMessageDecoder<ByteBuf> {
   private final UserConnection user;

   public VLBViaDecodeHandler(UserConnection user) {
      this.user = user;
   }

   protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> out) throws Exception {
      if (!this.user.checkIncomingPacket()) {
         throw CancelDecoderException.generate(null);
      } else if (!this.user.shouldTransformPacket()) {
         out.add(bytebuf.retain());
      } else {
         ByteBuf transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);

         try {
            this.user.transformIncoming(transformedBuf, CancelDecoderException::generate);
            out.add(transformedBuf.retain());
         } finally {
            transformedBuf.release();
         }
      }
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (!PipelineUtil.containsCause(cause, CancelCodecException.class)) {
         if (PipelineUtil.containsCause(cause, InformativeException.class) && this.user.getProtocolInfo().getState() != State.HANDSHAKE
            || Via.getManager().debugHandler().enabled()) {
            cause.printStackTrace();
         }
      }
   }
}
