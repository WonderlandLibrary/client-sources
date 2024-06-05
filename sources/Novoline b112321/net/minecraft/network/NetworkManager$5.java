package net.minecraft.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;

final class NetworkManager$5 extends ChannelInitializer {
   final NetworkManager val$networkmanager;

   NetworkManager$5(NetworkManager var1) {
      this.val$networkmanager = var1;
   }

   protected void initChannel(Channel var1) {
      try {
         var1.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
      } catch (ChannelException var3) {
         ;
      }

      ChannelPipeline var2 = var1.pipeline();
      var2.addLast("timeout", new ReadTimeoutHandler(30)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(EnumPacketDirection.CLIENTBOUND)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(EnumPacketDirection.SERVERBOUND)).addLast("packet_handler", this.val$networkmanager);
   }
}
