package net;

import net.aK_;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;

class pg implements ValueCreator {
   final aK_ a;

   pg(aK_ var1) {
      this.a = var1;
   }

   public void write(PacketWrapper var1) throws Exception {
      var1.write(Type.STRING, "MC|PickItem");
   }
}
