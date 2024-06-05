package net.minecraft.realms;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.*;
import net.minecraft.network.login.client.*;
import net.minecraft.util.*;
import java.net.*;

class RealmsConnect$1 extends Thread {
    final /* synthetic */ String val$p_connect_1_;
    final /* synthetic */ int val$p_connect_2_;
    
    @Override
    public void run() {
        InetAddress var1 = null;
        try {
            var1 = InetAddress.getByName(this.val$p_connect_1_);
            if (RealmsConnect.access$000(RealmsConnect.this)) {
                return;
            }
            RealmsConnect.access$102(RealmsConnect.this, NetworkManager.provideLanClient(var1, this.val$p_connect_2_));
            if (RealmsConnect.access$000(RealmsConnect.this)) {
                return;
            }
            RealmsConnect.access$100(RealmsConnect.this).setNetHandler(new NetHandlerLoginClient(RealmsConnect.access$100(RealmsConnect.this), Minecraft.getMinecraft(), RealmsConnect.access$200(RealmsConnect.this).getProxy()));
            if (RealmsConnect.access$000(RealmsConnect.this)) {
                return;
            }
            RealmsConnect.access$100(RealmsConnect.this).sendPacket(new C00Handshake(47, this.val$p_connect_1_, this.val$p_connect_2_, EnumConnectionState.LOGIN));
            if (RealmsConnect.access$000(RealmsConnect.this)) {
                return;
            }
            RealmsConnect.access$100(RealmsConnect.this).sendPacket(new C00PacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
        }
        catch (UnknownHostException var2) {
            if (RealmsConnect.access$000(RealmsConnect.this)) {
                return;
            }
            RealmsConnect.access$300().error("Couldn't connect to world", (Throwable)var2);
            Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.access$200(RealmsConnect.this), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { "Unknown host '" + this.val$p_connect_1_ + "'" })));
        }
        catch (Exception var3) {
            if (RealmsConnect.access$000(RealmsConnect.this)) {
                return;
            }
            RealmsConnect.access$300().error("Couldn't connect to world", (Throwable)var3);
            String var4 = var3.toString();
            if (var1 != null) {
                final String var5 = var1.toString() + ":" + this.val$p_connect_2_;
                var4 = var4.replaceAll(var5, "");
            }
            Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.access$200(RealmsConnect.this), "connect.failed", new ChatComponentTranslation("disconnect.genericReason", new Object[] { var4 })));
        }
    }
}