package net.minecraft.client.network;

import com.google.common.util.concurrent.FutureCallback;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus$Action;

class NetHandlerPlayClient$3 implements FutureCallback {
   final String val$s1;
   final NetHandlerPlayClient this$0;

   NetHandlerPlayClient$3(NetHandlerPlayClient var1, String var2) {
      this.this$0 = var1;
      this.val$s1 = var2;
   }

   public void onSuccess(Object var1) {
      NetHandlerPlayClient.access$000(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus$Action.SUCCESSFULLY_LOADED));
   }

   public void onFailure(Throwable var1) {
      NetHandlerPlayClient.access$000(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus$Action.FAILED_DOWNLOAD));
   }
}
