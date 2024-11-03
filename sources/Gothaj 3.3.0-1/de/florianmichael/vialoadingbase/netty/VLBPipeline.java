package de.florianmichael.vialoadingbase.netty;

import com.viaversion.viaversion.api.connection.UserConnection;
import de.florianmichael.vialoadingbase.netty.event.CompressionReorderEvent;
import de.florianmichael.vialoadingbase.netty.handler.VLBViaDecodeHandler;
import de.florianmichael.vialoadingbase.netty.handler.VLBViaEncodeHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public abstract class VLBPipeline extends ChannelInboundHandlerAdapter {
   public static final String VIA_DECODER_HANDLER_NAME = "via-decoder";
   public static final String VIA_ENCODER_HANDLER_NAME = "via-encoder";
   private final UserConnection user;

   public VLBPipeline(UserConnection user) {
      this.user = user;
   }

   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      super.handlerAdded(ctx);
      ctx.pipeline().addBefore(this.getDecoderHandlerName(), "via-decoder", this.createVLBViaDecodeHandler());
      ctx.pipeline().addBefore(this.getEncoderHandlerName(), "via-encoder", this.createVLBViaEncodeHandler());
   }

   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      super.userEventTriggered(ctx, evt);
      if (evt instanceof CompressionReorderEvent) {
         int decoderIndex = ctx.pipeline().names().indexOf(this.getDecompressionHandlerName());
         if (decoderIndex == -1) {
            return;
         }

         if (decoderIndex > ctx.pipeline().names().indexOf("via-decoder")) {
            ChannelHandler decoder = ctx.pipeline().get("via-decoder");
            ChannelHandler encoder = ctx.pipeline().get("via-encoder");
            ctx.pipeline().remove(decoder);
            ctx.pipeline().remove(encoder);
            ctx.pipeline().addAfter(this.getDecompressionHandlerName(), "via-decoder", decoder);
            ctx.pipeline().addAfter(this.getCompressionHandlerName(), "via-encoder", encoder);
         }
      }
   }

   public VLBViaDecodeHandler createVLBViaDecodeHandler() {
      return new VLBViaDecodeHandler(this.user);
   }

   public VLBViaEncodeHandler createVLBViaEncodeHandler() {
      return new VLBViaEncodeHandler(this.user);
   }

   public abstract String getDecoderHandlerName();

   public abstract String getEncoderHandlerName();

   public abstract String getDecompressionHandlerName();

   public abstract String getCompressionHandlerName();

   public UserConnection getUser() {
      return this.user;
   }
}
