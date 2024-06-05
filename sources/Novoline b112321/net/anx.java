package net;

import net.acE;
import net.ato;
import net.axZ;
import net.tf;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets$1$1;
import viaversion.viaversion.api.type.Type;

final class anx extends acE {
   public void registerMap() {
      this.a(Type.VAR_INT, Type.INT);
      this.a(Type.SHORT);
      this.a(Type.ITEM, axZ.c);
      this.a(new EntityPackets$1$1(this));
      this.a(new ato(this));
      this.a(new tf(this));
   }
}
