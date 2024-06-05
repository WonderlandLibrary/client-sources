package net.minecraft.client.network;

import net.minecraft.network.status.*;
import net.minecraft.client.multiplayer.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.*;
import net.minecraft.network.status.client.*;
import net.minecraft.network.*;
import com.mojang.authlib.*;
import net.minecraft.network.status.server.*;
import net.minecraft.util.*;

class OldServerPinger$1 implements INetHandlerStatusClient {
    private boolean field_147403_d = false;
    private long field_175092_e = 0L;
    final /* synthetic */ ServerData val$server;
    final /* synthetic */ NetworkManager val$var3;
    
    @Override
    public void handleServerInfo(final S00PacketServerInfo packetIn) {
        final ServerStatusResponse var2 = packetIn.func_149294_c();
        if (var2.getServerDescription() != null) {
            this.val$server.serverMOTD = var2.getServerDescription().getFormattedText();
        }
        else {
            this.val$server.serverMOTD = "";
        }
        if (var2.getProtocolVersionInfo() != null) {
            this.val$server.gameVersion = var2.getProtocolVersionInfo().getName();
            this.val$server.version = var2.getProtocolVersionInfo().getProtocol();
        }
        else {
            this.val$server.gameVersion = "Old";
            this.val$server.version = 0;
        }
        if (var2.getPlayerCountData() != null) {
            this.val$server.populationInfo = EnumChatFormatting.GRAY + "" + var2.getPlayerCountData().getOnlinePlayerCount() + "" + EnumChatFormatting.DARK_GRAY + "/" + EnumChatFormatting.GRAY + var2.getPlayerCountData().getMaxPlayers();
            if (ArrayUtils.isNotEmpty((Object[])var2.getPlayerCountData().getPlayers())) {
                final StringBuilder var3x = new StringBuilder();
                for (final GameProfile var6 : var2.getPlayerCountData().getPlayers()) {
                    if (var3x.length() > 0) {
                        var3x.append("\n");
                    }
                    var3x.append(var6.getName());
                }
                if (var2.getPlayerCountData().getPlayers().length < var2.getPlayerCountData().getOnlinePlayerCount()) {
                    if (var3x.length() > 0) {
                        var3x.append("\n");
                    }
                    var3x.append("... and ").append(var2.getPlayerCountData().getOnlinePlayerCount() - var2.getPlayerCountData().getPlayers().length).append(" more ...");
                }
                this.val$server.playerList = var3x.toString();
            }
        }
        else {
            this.val$server.populationInfo = EnumChatFormatting.DARK_GRAY + "???";
        }
        if (var2.getFavicon() != null) {
            final String var7 = var2.getFavicon();
            if (var7.startsWith("data:image/png;base64,")) {
                this.val$server.setBase64EncodedIconData(var7.substring("data:image/png;base64,".length()));
            }
            else {
                OldServerPinger.access$000().error("Invalid server icon (unknown format)");
            }
        }
        else {
            this.val$server.setBase64EncodedIconData(null);
        }
        this.field_175092_e = Minecraft.getSystemTime();
        this.val$var3.sendPacket(new C01PacketPing(this.field_175092_e));
        this.field_147403_d = true;
    }
    
    @Override
    public void handlePong(final S01PacketPong packetIn) {
        final long var2 = this.field_175092_e;
        final long var3 = Minecraft.getSystemTime();
        this.val$server.pingToServer = var3 - var2;
        this.val$var3.closeChannel(new ChatComponentText("Finished"));
    }
    
    @Override
    public void onDisconnect(final IChatComponent reason) {
        if (!this.field_147403_d) {
            OldServerPinger.access$000().error("Can't ping " + this.val$server.serverIP + ": " + reason.getUnformattedText());
            this.val$server.serverMOTD = EnumChatFormatting.DARK_RED + "Can't connect to server.";
            this.val$server.populationInfo = "";
            OldServerPinger.access$100(OldServerPinger.this, this.val$server);
        }
    }
}