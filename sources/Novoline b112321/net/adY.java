package net;

import net.aKy;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;

class adY implements ValueCreator {
   final aKy a;

   adY(aKy var1) {
      this.a = var1;
   }

   public void write(PacketWrapper var1) throws Exception {
      var1.write(Type.BOOLEAN, Boolean.valueOf(false));
      var1.write(Type.BOOLEAN, Boolean.valueOf(false));
   }
}
