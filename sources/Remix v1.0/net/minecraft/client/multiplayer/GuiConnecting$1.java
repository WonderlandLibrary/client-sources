package net.minecraft.client.multiplayer;

import net.minecraft.client.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import java.net.*;

class GuiConnecting$1 extends Thread {
    final /* synthetic */ String val$ip;
    final /* synthetic */ int val$port;
    
    @Override
    public void run() {
        InetAddress var1 = null;
        try {
            if (GuiConnecting.access$000(GuiConnecting.this)) {
                return;
            }
            var1 = InetAddress.getByName(this.val$ip);
            GuiConnecting.access$102(GuiConnecting.this, NetworkManager.provideLanClient(var1, this.val$port));
            GuiConnecting.access$100(GuiConnecting.this).setNetHandler(new NetHandlerLoginClient(GuiConnecting.access$100(GuiConnecting.this), GuiConnecting.access$200(), GuiConnecting.access$300(GuiConnecting.this)));
            GuiConnecting.access$100(GuiConnecting.this).sendPacket(new C00Handshake(47, this.val$ip, this.val$port, EnumConnectionState.LOGIN));
            GuiConnecting.access$100(GuiConnecting.this).sendPacket(new C00PacketLoginStart(GuiConnecting.access$400().getSession().getProfile()));
        }
        catch (UnknownHostException var2) {
            if (GuiConnecting.access$000(GuiConnecting.this)) {
                return;
            }
            GuiConnecting.access$500().error("Couldn't connect to server", (Throwable)var2);
            GuiConnecting.access$600().displayGuiScreen(new GuiDisconnected(GuiConnecting.access$300(GuiConnecting.this), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host" })));
        }
        catch (Exception var3) {
            if (GuiConnecting.access$000(GuiConnecting.this)) {
                return;
            }
            GuiConnecting.access$500().error("Couldn't connect to server", (Throwable)var3);
            String var4 = var3.toString();
            if (var1 != null) {
                final String var5 = var1.toString() + ":" + this.val$port;
                var4 = var4.replaceAll(var5, "");
            }
            GuiConnecting.access$700().displayGuiScreen(new GuiDisconnected(GuiConnecting.access$300(GuiConnecting.this), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { var4 })));
        }
    }
}