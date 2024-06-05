package net;

import net.acE;
import net.aq0;
import viaversion.viabackwards.protocol.protocol1_11_1to1_12.packets.BlockItemPackets1_12$4$1;

class ac_ extends acE {
   final aq0 c;

   ac_(aq0 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(new BlockItemPackets1_12$4$1(this));
   }
}
