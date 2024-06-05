package net;

import net.a0V;
import net.acE;
import net.cM;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class an9 extends acE {
   final Protocol1_15_2To1_16 c;

   an9(Protocol1_15_2To1_16 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(an9::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      a0V.b();
      var0.passthrough(Type.VAR_INT);
      int var2 = ((Integer)var0.passthrough(Type.VAR_INT)).intValue();
      if(var2 == 0) {
         ((cM)var0.user().b(cM.class)).a(true);
      }

      if(var2 == 1) {
         ((cM)var0.user().b(cM.class)).a(false);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
