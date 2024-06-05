package net;

import net.acE;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets$2$1;
import viaversion.viaversion.api.type.Type;

final class a9c extends acE {
   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.INT);
      this.a(new WorldPackets$2$1(this));
   }
}
