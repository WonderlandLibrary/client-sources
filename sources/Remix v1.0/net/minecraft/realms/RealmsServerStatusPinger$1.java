package net.minecraft.realms;

import net.minecraft.network.status.*;
import net.minecraft.network.status.client.*;
import net.minecraft.network.*;
import net.minecraft.network.status.server.*;
import net.minecraft.util.*;

class RealmsServerStatusPinger$1 implements INetHandlerStatusClient {
    private boolean field_154345_e = false;
    final /* synthetic */ RealmsServerPing val$p_pingServer_2_;
    final /* synthetic */ NetworkManager val$var4;
    final /* synthetic */ String val$p_pingServer_1_;
    
    @Override
    public void handleServerInfo(final S00PacketServerInfo packetIn) {
        final ServerStatusResponse var2 = packetIn.func_149294_c();
        if (var2.getPlayerCountData() != null) {
            this.val$p_pingServer_2_.nrOfPlayers = String.valueOf(var2.getPlayerCountData().getOnlinePlayerCount());
        }
        this.val$var4.sendPacket(new C01PacketPing(Realms.currentTimeMillis()));
        this.field_154345_e = true;
    }
    
    @Override
    public void handlePong(final S01PacketPong packetIn) {
        this.val$var4.closeChannel(new ChatComponentText("Finished"));
    }
    
    @Override
    public void onDisconnect(final IChatComponent reason) {
        if (!this.field_154345_e) {
            RealmsServerStatusPinger.access$000().error("Can't ping " + this.val$p_pingServer_1_ + ": " + reason.getUnformattedText());
        }
    }
}