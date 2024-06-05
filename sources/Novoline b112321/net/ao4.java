package net;

import net.a0q;
import net.acE;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14;

class ao4 extends acE {
   final PlayerPackets1_14 c;

   ao4(PlayerPackets1_14 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(new a0q(this));
   }
}
