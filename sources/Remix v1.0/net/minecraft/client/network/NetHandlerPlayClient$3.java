package net.minecraft.client.network;

import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.google.common.util.concurrent.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

class NetHandlerPlayClient$3 implements Runnable {
    final /* synthetic */ String val$var3;
    final /* synthetic */ String val$var2;
    
    @Override
    public void run() {
        NetHandlerPlayClient.access$100(NetHandlerPlayClient.this).displayGuiScreen(new GuiYesNo(new GuiYesNoCallback() {
            @Override
            public void confirmClicked(final boolean result, final int id) {
                NetHandlerPlayClient.access$102(NetHandlerPlayClient.this, Minecraft.getMinecraft());
                if (result) {
                    if (NetHandlerPlayClient.access$100(NetHandlerPlayClient.this).getCurrentServerData() != null) {
                        NetHandlerPlayClient.access$100(NetHandlerPlayClient.this).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
                    }
                    NetHandlerPlayClient.access$000(NetHandlerPlayClient.this).sendPacket(new C19PacketResourcePackStatus(Runnable.this.val$var3, C19PacketResourcePackStatus.Action.ACCEPTED));
                    Futures.addCallback(NetHandlerPlayClient.access$100(NetHandlerPlayClient.this).getResourcePackRepository().func_180601_a(Runnable.this.val$var2, Runnable.this.val$var3), (FutureCallback)new FutureCallback() {
                        public void onSuccess(final Object p_onSuccess_1_) {
                            NetHandlerPlayClient.access$000(NetHandlerPlayClient.this).sendPacket(new C19PacketResourcePackStatus(Runnable.this.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                        }
                        
                        public void onFailure(final Throwable p_onFailure_1_) {
                            NetHandlerPlayClient.access$000(NetHandlerPlayClient.this).sendPacket(new C19PacketResourcePackStatus(Runnable.this.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                        }
                    });
                }
                else {
                    if (NetHandlerPlayClient.access$100(NetHandlerPlayClient.this).getCurrentServerData() != null) {
                        NetHandlerPlayClient.access$100(NetHandlerPlayClient.this).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
                    }
                    NetHandlerPlayClient.access$000(NetHandlerPlayClient.this).sendPacket(new C19PacketResourcePackStatus(Runnable.this.val$var3, C19PacketResourcePackStatus.Action.DECLINED));
                }
                ServerList.func_147414_b(NetHandlerPlayClient.access$100(NetHandlerPlayClient.this).getCurrentServerData());
                NetHandlerPlayClient.access$100(NetHandlerPlayClient.this).displayGuiScreen(null);
            }
        }, I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
    }
}