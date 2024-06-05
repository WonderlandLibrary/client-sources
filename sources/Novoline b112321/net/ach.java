package net;

import net.acE;
import net.aqT;
import net.aqp;
import net.co;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ach extends acE {
   final aqT c;

   ach(aqT var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.STRING);
      this.a(Type.COMPONENT);
      this.a(Type.UNSIGNED_BYTE);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aqp.d();
      int var3 = -1;
      if(((String)var1.get(Type.STRING, 0)).equals("EntityHorse")) {
         var3 = ((Integer)var1.passthrough(Type.INT)).intValue();
      }

      String var4 = (String)var1.get(Type.STRING, 0);
      co var5 = (co)var1.user().b(co.class);
      var5.c(var4);
      var5.a(var3);
      if(aqT.b(this.c, var1.user())) {
         var1.set(Type.UNSIGNED_BYTE, 1, Short.valueOf((short)17));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
