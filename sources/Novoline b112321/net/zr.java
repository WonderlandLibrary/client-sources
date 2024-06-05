package net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import net.minecraft.client.network.NetHandlerHandshakeMemory;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkSystem;

class zr extends ChannelInitializer {
   final NetworkSystem a;

   zr(NetworkSystem var1) {
      this.a = var1;
   }

   protected void initChannel(Channel var1) throws Exception {
      NetworkManager var2 = new NetworkManager(EnumPacketDirection.SERVERBOUND);
      var2.setNetHandler(new NetHandlerHandshakeMemory(NetworkSystem.access$100(this.a), var2));
      NetworkSystem.access$000(this.a).add(var2);
      var1.pipeline().addLast("packet_handler", var2);
   }
}
