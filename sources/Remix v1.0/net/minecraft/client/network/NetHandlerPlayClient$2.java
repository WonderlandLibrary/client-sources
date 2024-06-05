package net.minecraft.client.network;

import com.google.common.util.concurrent.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

class NetHandlerPlayClient$2 implements FutureCallback {
    final /* synthetic */ String val$var3;
    
    public void onSuccess(final Object p_onSuccess_1_) {
        NetHandlerPlayClient.access$000(NetHandlerPlayClient.this).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
    }
    
    public void onFailure(final Throwable p_onFailure_1_) {
        NetHandlerPlayClient.access$000(NetHandlerPlayClient.this).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
    }
}