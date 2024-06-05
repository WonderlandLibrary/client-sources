package net;

import net.Cw;
import net.aM6;
import net.aMG;
import net.aMI;
import net.aMR;
import net.aMT;
import net.aMX;
import net.aMe;
import net.aMh;
import net.aMq;
import net.aMy;
import net.acE;
import net.aeU;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.Protocol;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets$7;

public class a0v {
   public static void a(Protocol var0) {
      var0.a((ClientboundPacketType)aeU.UPDATE_SIGN, new aMq());
      var0.a((ClientboundPacketType)aeU.EFFECT, new aMe());
      var0.a((ClientboundPacketType)aeU.NAMED_SOUND, new aMX());
      var0.a((ClientboundPacketType)aeU.CHUNK_DATA, new aMG());
      var0.a((ClientboundPacketType)aeU.MAP_BULK_CHUNK, (ClientboundPacketType)null, (acE)(new aMh()));
      var0.a((ClientboundPacketType)aeU.BLOCK_ENTITY_DATA, new aMT());
      var0.a((ClientboundPacketType)aeU.BLOCK_CHANGE, new WorldPackets$7());
      var0.a((ServerboundPacketType)Cw.UPDATE_SIGN, new aMy());
      var0.a((ServerboundPacketType)Cw.PLAYER_DIGGING, new aMR());
      var0.a((ServerboundPacketType)Cw.USE_ITEM, (ServerboundPacketType)null, (acE)(new aMI()));
      var0.a((ServerboundPacketType)Cw.PLAYER_BLOCK_PLACEMENT, new aM6());
   }
}
