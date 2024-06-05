package net.minecraft.server.network;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.server.network.NetHandlerLoginServer;

class NetHandlerLoginServer$1 implements ChannelFutureListener {
   final NetHandlerLoginServer this$0;

   NetHandlerLoginServer$1(NetHandlerLoginServer var1) {
      this.this$0 = var1;
   }

   public void operationComplete(ChannelFuture var1) throws Exception {
      this.this$0.networkManager.setCompressionTreshold(NetHandlerLoginServer.access$000(this.this$0).getNetworkCompressionTreshold());
   }
}
