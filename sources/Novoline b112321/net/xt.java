package net;

import net.aW_;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class xt implements PacketHandler {
   final aW_ a;

   xt(aW_ var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      short var2 = ((Short)var1.read(Type.UNSIGNED_BYTE)).shortValue();
      PacketWrapper var3 = var1.create(13);
      var3.write(Type.UNSIGNED_BYTE, Short.valueOf(var2));
      var3.write(Type.BOOLEAN, Boolean.valueOf(false));
      var3.send(this.a.c.getClass());
   }
}
