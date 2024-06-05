package net;

import net.Jy;
import net.acE;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_11to1_10.Protocol1_11To1_10;

class a7_ extends acE {
   final Protocol1_11To1_10 c;

   a7_(Protocol1_11To1_10 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.STRING);
      this.a(new Jy(this));
   }
}
