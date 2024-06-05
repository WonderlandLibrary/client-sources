package net;

import com.google.common.util.concurrent.FutureCallback;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus$Action;

class akz implements FutureCallback {
   final String b;
   final NetHandlerPlayClient a;

   akz(NetHandlerPlayClient var1, String var2) {
      this.a = var1;
      this.b = var2;
   }

   public void onSuccess(Object var1) {
      NetHandlerPlayClient.access$000(this.a).sendPacket(new C19PacketResourcePackStatus(this.b, C19PacketResourcePackStatus$Action.SUCCESSFULLY_LOADED));
   }

   public void onFailure(Throwable var1) {
      NetHandlerPlayClient.access$000(this.a).sendPacket(new C19PacketResourcePackStatus(this.b, C19PacketResourcePackStatus$Action.FAILED_DOWNLOAD));
   }
}
