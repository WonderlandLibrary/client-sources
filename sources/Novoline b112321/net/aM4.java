package net;

import net.acE;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.packets.SpawnPackets$8$1;

final class aM4 extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT_ARRAY_PRIMITIVE);
      this.a(new SpawnPackets$8$1(this));
   }
}
