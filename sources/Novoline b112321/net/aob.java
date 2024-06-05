package net;

import net.aSy;
import net.acE;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14;
import viaversion.viaversion.api.type.Type;

class aob extends acE {
   final PlayerPackets1_14 c;

   aob(PlayerPackets1_14 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(new aSy(this));
   }
}
