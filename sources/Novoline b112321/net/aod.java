package net;

import net.XN;
import net.acE;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15;

class aod extends acE {
   final BlockItemPackets1_15 c;

   aod(BlockItemPackets1_15 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(new XN(this));
   }
}
