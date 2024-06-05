package net.minecraft.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import net.minecraft.network.NetworkManager;

final class NetworkManager$6 extends ChannelInitializer {
   final NetworkManager val$networkmanager;

   NetworkManager$6(NetworkManager var1) {
      this.val$networkmanager = var1;
   }

   protected void initChannel(Channel var1) {
      var1.pipeline().addLast("packet_handler", this.val$networkmanager);
   }
}
