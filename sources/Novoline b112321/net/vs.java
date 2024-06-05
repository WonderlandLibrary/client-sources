package net;

import net.awj;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.Protocol;
import viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.PlayerPackets$1;
import viaversion.viaversion.protocols.protocol1_15to1_14_4.packets.PlayerPackets$2;

public class vs {
   public static void a(Protocol var0) {
      var0.a((ClientboundPacketType)awj.RESPAWN, new PlayerPackets$1());
      var0.a((ClientboundPacketType)awj.JOIN_GAME, new PlayerPackets$2());
   }
}
