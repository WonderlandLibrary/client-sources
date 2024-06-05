package net;

import net.a9C;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aGz implements PacketHandler {
   final a9C a;

   aGz(a9C var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      Position var2 = (Position)var1.read(Type.POSITION);
      var1.write(Type.INT, Integer.valueOf(var2.getX()));
      var1.write(Type.UNSIGNED_BYTE, Short.valueOf(var2.getY()));
      var1.write(Type.INT, Integer.valueOf(var2.getZ()));
   }
}
