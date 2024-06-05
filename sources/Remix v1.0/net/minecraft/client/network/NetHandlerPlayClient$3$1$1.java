package net.minecraft.client.network;

import com.google.common.util.concurrent.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

class NetHandlerPlayClient$3$1$1 implements FutureCallback {
    public void onSuccess(final Object p_onSuccess_1_) {
        NetHandlerPlayClient.access$000(GuiYesNoCallback.this.this$1.this$0).sendPacket(new C19PacketResourcePackStatus(GuiYesNoCallback.this.this$1.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
    }
    
    public void onFailure(final Throwable p_onFailure_1_) {
        NetHandlerPlayClient.access$000(GuiYesNoCallback.this.this$1.this$0).sendPacket(new C19PacketResourcePackStatus(GuiYesNoCallback.this.this$1.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
    }
}