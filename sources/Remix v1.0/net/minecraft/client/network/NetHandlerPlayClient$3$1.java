package net.minecraft.client.network;

import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.google.common.util.concurrent.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;

class NetHandlerPlayClient$3$1 implements GuiYesNoCallback {
    @Override
    public void confirmClicked(final boolean result, final int id) {
        NetHandlerPlayClient.access$102(Runnable.this.this$0, Minecraft.getMinecraft());
        if (result) {
            if (NetHandlerPlayClient.access$100(Runnable.this.this$0).getCurrentServerData() != null) {
                NetHandlerPlayClient.access$100(Runnable.this.this$0).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
            }
            NetHandlerPlayClient.access$000(Runnable.this.this$0).sendPacket(new C19PacketResourcePackStatus(Runnable.this.val$var3, C19PacketResourcePackStatus.Action.ACCEPTED));
            Futures.addCallback(NetHandlerPlayClient.access$100(Runnable.this.this$0).getResourcePackRepository().func_180601_a(Runnable.this.val$var2, Runnable.this.val$var3), (FutureCallback)new FutureCallback() {
                public void onSuccess(final Object p_onSuccess_1_) {
                    NetHandlerPlayClient.access$000(Runnable.this.this$0).sendPacket(new C19PacketResourcePackStatus(Runnable.this.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                }
                
                public void onFailure(final Throwable p_onFailure_1_) {
                    NetHandlerPlayClient.access$000(Runnable.this.this$0).sendPacket(new C19PacketResourcePackStatus(Runnable.this.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                }
            });
        }
        else {
            if (NetHandlerPlayClient.access$100(Runnable.this.this$0).getCurrentServerData() != null) {
                NetHandlerPlayClient.access$100(Runnable.this.this$0).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
            }
            NetHandlerPlayClient.access$000(Runnable.this.this$0).sendPacket(new C19PacketResourcePackStatus(Runnable.this.val$var3, C19PacketResourcePackStatus.Action.DECLINED));
        }
        ServerList.func_147414_b(NetHandlerPlayClient.access$100(Runnable.this.this$0).getCurrentServerData());
        NetHandlerPlayClient.access$100(Runnable.this.this$0).displayGuiScreen(null);
    }
}