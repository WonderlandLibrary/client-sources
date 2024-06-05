package net;

import net.aWc;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;

class h9 implements ValueCreator {
   final aWc a;

   h9(aWc var1) {
      this.a = var1;
   }

   public void write(PacketWrapper var1) throws Exception {
      var1.write(Type.STRING, "MC|AdvCmd");
      var1.write(Type.BYTE, Byte.valueOf((byte)1));
   }
}
