package net;

import net.aCV;
import net.acE;
import net.cD;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class azm implements PacketHandler {
   final aCV a;

   azm(aCV var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      cD.b();
      Long var3 = (Long)var1.read(Type.LONG);
      ((cD)var1.user().b(cD.class)).a(var3.longValue());
      var1.write(Type.VAR_INT, Integer.valueOf(var3.hashCode()));
      if(acE.b() == null) {
         cD.b(new int[3]);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
