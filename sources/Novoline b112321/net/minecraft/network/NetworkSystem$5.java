package net.minecraft.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import net.minecraft.client.network.NetHandlerHandshakeMemory;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkSystem;

class NetworkSystem$5 extends ChannelInitializer {
   final NetworkSystem this$0;

   NetworkSystem$5(NetworkSystem var1) {
      this.this$0 = var1;
   }

   protected void initChannel(Channel var1) throws Exception {
      NetworkManager var2 = new NetworkManager(EnumPacketDirection.SERVERBOUND);
      var2.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.access$100(this.this$0), var2));
      NetworkSystem.access$000(this.this$0).add(var2);
      var1.pipeline().addLast("packet_handler", var2);
   }
}
