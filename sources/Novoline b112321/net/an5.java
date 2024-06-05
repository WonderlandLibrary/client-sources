package net;

import net.acE;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets$14$1;
import viaversion.viaversion.api.type.Type;

final class an5 extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT, Type.INT);
      this.a(Type.BYTE);
      this.a(Type.BYTE);
      this.a(Type.VAR_INT, Type.SHORT);
      this.a(new EntityPackets$14$1(this));
   }
}
