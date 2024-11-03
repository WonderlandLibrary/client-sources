package com.viaversion.viaversion.sponge.handlers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.exception.CancelCodecException;
import com.viaversion.viaversion.exception.CancelEncoderException;
import com.viaversion.viaversion.handlers.ChannelHandlerContextWrapper;
import com.viaversion.viaversion.handlers.ViaCodecHandler;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.lang.reflect.InvocationTargetException;

public class SpongeEncodeHandler extends MessageToByteEncoder<Object> implements ViaCodecHandler {
   private final UserConnection info;
   private final MessageToByteEncoder<?> minecraftEncoder;

   public SpongeEncodeHandler(UserConnection info, MessageToByteEncoder<?> minecraftEncoder) {
      this.info = info;
      this.minecraftEncoder = minecraftEncoder;
   }

   protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf bytebuf) throws Exception {
      if (!(o instanceof ByteBuf)) {
         try {
            PipelineUtil.callEncode(this.minecraftEncoder, new ChannelHandlerContextWrapper(ctx, this), o, bytebuf);
         } catch (InvocationTargetException var5) {
            if (var5.getCause() instanceof Exception) {
               throw (Exception)var5.getCause();
            }

            if (var5.getCause() instanceof Error) {
               throw (Error)var5.getCause();
            }
         }
      } else {
         bytebuf.writeBytes((ByteBuf)o);
      }

      this.transform(bytebuf);
   }

   @Override
   public void transform(ByteBuf bytebuf) throws Exception {
      if (!this.info.checkClientboundPacket()) {
         throw CancelEncoderException.generate(null);
      } else if (this.info.shouldTransformPacket()) {
         this.info.transformClientbound(bytebuf, CancelEncoderException::generate);
      }
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (!(cause instanceof CancelCodecException)) {
         super.exceptionCaught(ctx, cause);
      }
   }
}
