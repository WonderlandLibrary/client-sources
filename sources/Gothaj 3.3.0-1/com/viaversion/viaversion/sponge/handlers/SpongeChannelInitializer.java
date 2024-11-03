package com.viaversion.viaversion.sponge.handlers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.connection.UserConnectionImpl;
import com.viaversion.viaversion.platform.WrappedChannelInitializer;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import java.lang.reflect.Method;

public class SpongeChannelInitializer extends ChannelInitializer<Channel> implements WrappedChannelInitializer {
   private static final Method INIT_CHANNEL_METHOD;
   private final ChannelInitializer<Channel> original;

   public SpongeChannelInitializer(ChannelInitializer<Channel> oldInit) {
      this.original = oldInit;
   }

   protected void initChannel(Channel channel) throws Exception {
      if (Via.getAPI().getServerVersion().isKnown() && channel instanceof SocketChannel) {
         UserConnection info = new UserConnectionImpl((SocketChannel)channel);
         new ProtocolPipelineImpl(info);
         INIT_CHANNEL_METHOD.invoke(this.original, channel);
         MessageToByteEncoder encoder = new SpongeEncodeHandler(info, (MessageToByteEncoder<?>)channel.pipeline().get("encoder"));
         ByteToMessageDecoder decoder = new SpongeDecodeHandler(info, (ByteToMessageDecoder)channel.pipeline().get("decoder"));
         channel.pipeline().replace("encoder", "encoder", encoder);
         channel.pipeline().replace("decoder", "decoder", decoder);
      } else {
         INIT_CHANNEL_METHOD.invoke(this.original, channel);
      }
   }

   public ChannelInitializer<Channel> getOriginal() {
      return this.original;
   }

   @Override
   public ChannelInitializer<Channel> original() {
      return this.original;
   }

   static {
      try {
         INIT_CHANNEL_METHOD = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class);
         INIT_CHANNEL_METHOD.setAccessible(true);
      } catch (NoSuchMethodException var1) {
         throw new RuntimeException(var1);
      }
   }
}
