package net.minecraft.network;

import net.minecraft.network.NetHandlerPlayServer;

class NetHandlerPlayServer$2 implements Runnable {
   final NetHandlerPlayServer this$0;

   NetHandlerPlayServer$2(NetHandlerPlayServer var1) {
      this.this$0 = var1;
   }

   public void run() {
      this.this$0.netManager.checkDisconnected();
   }
}
