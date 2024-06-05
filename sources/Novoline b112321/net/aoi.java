package net;

import net.acE;
import net.aq6;
import viaversion.viaversion.api.type.Type;

class aoi extends acE {
   final aq6 c;

   aoi(aq6 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.POSITION1_14, Type.POSITION);
      this.a(Type.BYTE);
   }
}
