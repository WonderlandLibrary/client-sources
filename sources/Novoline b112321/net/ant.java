package net;

import net.aRi;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.api.type.types.CustomByteType;

class ant extends acE {
   final aRi c;

   ant(aRi var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(ant::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      short var1 = ((Short)var0.read(Type.SHORT)).shortValue();
      var0.write(Type.VAR_INT, Integer.valueOf(var1));
      var0.passthrough(new CustomByteType(Integer.valueOf(var1)));
      short var2 = ((Short)var0.read(Type.SHORT)).shortValue();
      var0.write(Type.VAR_INT, Integer.valueOf(var2));
      var0.passthrough(new CustomByteType(Integer.valueOf(var2)));
   }
}
