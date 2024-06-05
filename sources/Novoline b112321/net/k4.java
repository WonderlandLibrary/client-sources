package net;

import net.a7k;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;

class k4 implements ValueCreator {
   final a7k a;

   k4(a7k var1) {
      this.a = var1;
   }

   public void write(PacketWrapper var1) throws Exception {
      var1.write(Type.VAR_INT, Integer.valueOf(1));
   }
}
