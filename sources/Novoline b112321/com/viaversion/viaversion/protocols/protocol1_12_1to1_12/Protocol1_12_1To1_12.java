package com.viaversion.viaversion.protocols.protocol1_12_1to1_12;

import net.aEb;
import net.ayx;
import net.azW;
import net.lV;
import net.r;

public class Protocol1_12_1To1_12 extends ayx {
   public Protocol1_12_1To1_12() {
      super(lV.class, azW.class, aEb.class, r.class);
   }

   protected void registerPackets() {
      this.a(r.CRAFT_RECIPE_REQUEST);
   }
}
