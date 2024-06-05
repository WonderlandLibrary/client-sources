package net;

import net.aVC;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a8y implements PacketHandler {
   final aVC a;

   a8y(aVC var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      short var2 = ((Short)var1.read(Type.UNSIGNED_BYTE)).shortValue();
      PacketWrapper var3 = var1.create(13);
      var3.write(Type.UNSIGNED_BYTE, Short.valueOf(var2));
      var3.write(Type.BOOLEAN, Boolean.valueOf(false));
      var3.send(this.a.c.getClass());
      var1.passthrough(Type.UNSIGNED_BYTE);
      var1.passthrough(Type.STRING);
      var1.write(Type.VAR_INT, Integer.valueOf(64));
   }
}
