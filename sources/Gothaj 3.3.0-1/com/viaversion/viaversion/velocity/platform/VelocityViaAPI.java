package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.ViaAPIBase;
import io.netty.buffer.ByteBuf;

public class VelocityViaAPI extends ViaAPIBase<Player> {
   public int getPlayerVersion(Player player) {
      return this.getPlayerVersion(player.getUniqueId());
   }

   public void sendRawPacket(Player player, ByteBuf packet) throws IllegalArgumentException {
      this.sendRawPacket(player.getUniqueId(), packet);
   }
}
