package com.viaversion.viaversion.bungee.platform;

import com.viaversion.viaversion.ViaAPIBase;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.bungee.service.ProtocolDetectorService;
import io.netty.buffer.ByteBuf;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeViaAPI extends ViaAPIBase<ProxiedPlayer> {
   public int getPlayerVersion(ProxiedPlayer player) {
      return this.getPlayerVersion(player.getUniqueId());
   }

   public void sendRawPacket(ProxiedPlayer player, ByteBuf packet) throws IllegalArgumentException {
      this.sendRawPacket(player.getUniqueId(), packet);
   }

   public void probeServer(ServerInfo serverInfo) {
      ((ProtocolDetectorService)Via.proxyPlatform().protocolDetectorService()).probeServer(serverInfo);
   }
}
