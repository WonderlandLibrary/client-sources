package net;

import net.PE;
import net.acE;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.packets.WorldPackets$2$1;

final class aMe extends acE {
   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.POSITION);
      this.a(Type.INT);
      this.a(Type.BOOLEAN);
      this.a(new WorldPackets$2$1(this));
      this.a(new PE(this));
   }
}
