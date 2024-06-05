package net;

import net.acE;
import net.aq6;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aoo extends acE {
   final aq6 c;

   aoo(aq6 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(aoo::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      aq6.a();
      int var2 = 0;
      if(var2 < 3) {
         float var3 = ((Float)var0.get(Type.FLOAT, var2)).floatValue();
         if(var3 < 0.0F) {
            var3 = (float)Math.floor((double)var3);
            var0.set(Type.FLOAT, var2, Float.valueOf(var3));
         }

         ++var2;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
