package net.minecraft.network;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.ChatComponentText;

class NetHandlerPlayServer$1 implements GenericFutureListener {
   final ChatComponentText val$chatcomponenttext;
   final NetHandlerPlayServer this$0;

   NetHandlerPlayServer$1(NetHandlerPlayServer var1, ChatComponentText var2) {
      this.this$0 = var1;
      this.val$chatcomponenttext = var2;
   }

   public void operationComplete(Future var1) throws Exception {
      this.this$0.netManager.closeChannel(this.val$chatcomponenttext);
   }
}
