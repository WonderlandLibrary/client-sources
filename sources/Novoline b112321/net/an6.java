package net;

import net.acE;
import net.aqR;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class an6 extends acE {
   final aqR c;

   an6(aqR var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.BOOLEAN);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.INT);
      this.a(an6::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      int var1 = ((Integer)var0.get(Type.INT, 0)).intValue();
      if(var1 == 46) {
         var0.set(Type.INT, 0, Integer.valueOf(38));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
