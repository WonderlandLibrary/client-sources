package net;

import net.aRo;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aoz extends acE {
   final aRo c;

   aoz(aRo var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.POSITION1_14);
      this.a(Type.VAR_INT);
      this.a(aoz::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      aRo.b();
      int var2 = ((Integer)var0.read(Type.VAR_INT)).intValue();
      boolean var3 = ((Boolean)var0.read(Type.BOOLEAN)).booleanValue();
      if(var3 && var2 == 0) {
         var0.cancel();
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
