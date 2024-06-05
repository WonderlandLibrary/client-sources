package net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.a29;
import net.aR8;
import net.aw_;
import net.bgY;
import net.l_;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;
import viaversion.viafabric.handler.clientside.VRDecodeHandler;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.data.UserConnection;
import viaversion.viaversion.api.platform.ViaInjector;
import viaversion.viaversion.api.protocol.ProtocolPipeline;

final class axv extends ChannelInitializer {
   final a29 a;

   axv(a29 var1) {
      this.a = var1;
   }

   protected void initChannel(Channel var1) {
      if(!(var1 instanceof SocketChannel)) {
         throw new IllegalStateException();
      } else {
         bgY var2 = new bgY(var1);
         this.a.a((UserConnection)var2);
         (new ProtocolPipeline(var2)).add(aR8.i);
         ViaInjector var3 = Via.getManager().getInjector();

         try {
            var1.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
         } catch (ChannelException var5) {
            ;
         }

         ChannelPipeline var4 = var1.pipeline();
         var4.addLast("timeout", new ReadTimeoutHandler(30)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new aw_(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", this.a);
         var1.pipeline().addBefore("encoder", var3.getEncoderName(), new l_(var2)).addBefore("decoder", var3.getDecoderName(), new VRDecodeHandler(var2));
      }
   }

   private static ChannelException a(ChannelException var0) {
      return var0;
   }
}
