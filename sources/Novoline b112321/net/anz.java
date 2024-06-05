package net;

import net.aRi;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.CustomByteType;

class anz extends acE {
   final aRi c;

   anz(aRi var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.STRING);
      this.a(anz::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      int var1 = ((Integer)var0.read(Type.VAR_INT)).intValue();
      var0.write(Type.SHORT, Short.valueOf((short)var1));
      var0.passthrough(new CustomByteType(Integer.valueOf(var1)));
      int var2 = ((Integer)var0.read(Type.VAR_INT)).intValue();
      var0.write(Type.SHORT, Short.valueOf((short)var2));
      var0.passthrough(new CustomByteType(Integer.valueOf(var2)));
   }
}
