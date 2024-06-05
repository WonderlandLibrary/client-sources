package net.minecraft.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkSystem;
import net.minecraft.network.PingResponseHandler;
import net.minecraft.server.network.NetHandlerHandshakeTCP;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;

class NetworkSystem$4 extends ChannelInitializer {
   final NetworkSystem this$0;

   NetworkSystem$4(NetworkSystem var1) {
      this.this$0 = var1;
   }

   protected void initChannel(Channel var1) throws Exception {
      try {
         var1.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
      } catch (ChannelException var3) {
         ;
      }

      var1.pipeline().addLast("timeout", new ReadTimeoutHandler(30)).addLast("legacy_query", new PingResponseHandler(this.this$0)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(EnumPacketDirection.SERVERBOUND)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(EnumPacketDirection.CLIENTBOUND));
      NetworkManager var2 = new NetworkManager(EnumPacketDirection.SERVERBOUND);
      NetworkSystem.access$000(this.this$0).add(var2);
      var1.pipeline().addLast("packet_handler", var2);
      var2.setNetHandler(new NetHandlerHandshakeTCP(NetworkSystem.access$100(this.this$0), var2));
   }
}
