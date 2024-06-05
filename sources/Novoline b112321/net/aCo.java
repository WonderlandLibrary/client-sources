package net;

import net.acE;
import net.aqw;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aCo extends acE {
   final aqw c;

   aCo(aqw var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.INT);
      this.a(aCo::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      aqw.a();
      int var2 = ((Integer)var0.get(Type.INT, 0)).intValue();
      int var3 = var2;
      int var4 = 0;
      if(var4 < var2) {
         String var5 = (String)var0.read(Type.STRING);
         if(var5.equals("generic.flyingSpeed")) {
            var3 = var2 - 1;
            var0.read(Type.DOUBLE);
            int var6 = ((Integer)var0.read(Type.VAR_INT)).intValue();
            int var7 = 0;
            if(var7 < var6) {
               var0.read(Type.UUID);
               var0.read(Type.DOUBLE);
               var0.read(Type.BYTE);
               ++var7;
            }
         }

         var0.write(Type.STRING, var5);
         var0.passthrough(Type.DOUBLE);
         int var9 = ((Integer)var0.passthrough(Type.VAR_INT)).intValue();
         int var11 = 0;
         if(var11 < var9) {
            var0.passthrough(Type.UUID);
            var0.passthrough(Type.DOUBLE);
            var0.passthrough(Type.BYTE);
            ++var11;
         }

         ++var4;
      }

      if(var3 != var2) {
         var0.set(Type.INT, 0, Integer.valueOf(var3));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
