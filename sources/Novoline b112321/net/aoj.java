package net;

import net.acE;
import net.aq3;
import net.cW;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aoj extends acE {
   final aq3 c;

   aoj(aq3 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.FLOAT);
      this.a(aoj::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      String var1 = aq3.a();
      if(((Short)var0.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 11) {
         ((cW)var0.user().b(cW.class)).a(((Float)var0.get(Type.FLOAT, 0)).floatValue() == 1.0F);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
