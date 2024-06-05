package net;

import net.aMv;
import net.aRY;
import net.aXe;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aSp implements PacketHandler {
   final aMv a;

   aSp(aMv var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.b();
      byte var3 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
      if(var3 == 2) {
         var1.passthrough(Type.STRING);
         var1.passthrough(Type.STRING);
         var1.passthrough(Type.STRING);
         var1.passthrough(Type.BYTE);
         var1.passthrough(Type.STRING);
         var1.write(Type.STRING, Via.getConfig().isPreventCollision()?"never":"");
         var1.passthrough(Type.BYTE);
      }

      if(var3 == 0 || var3 == 3 || var3 == 4) {
         String[] var4 = (String[])var1.passthrough(Type.STRING_ARRAY);
         cq var5 = (cq)var1.user().b(cq.class);
         String var6 = var1.user().getProtocolInfo().getUsername();
         String var7 = (String)var1.get(Type.STRING, 0);
         int var9 = var4.length;
         int var10 = 0;
         if(var10 < var9) {
            String var11 = var4[var10];
            if(var5.f() && var11.equalsIgnoreCase(var6)) {
               if(var3 == 4) {
                  var1.send(aRY.class, true, true);
                  var1.cancel();
                  var5.a(true, true);
                  var5.a("viaversion");
               }

               var5.a(false, true);
               var5.a(var7);
            }

            ++var10;
         }
      }

      if(var3 == 1) {
         cq var12 = (cq)var1.user().b(cq.class);
         String var13 = (String)var1.get(Type.STRING, 0);
         if(var12.f() && var13.equals(var12.n())) {
            var1.send(aRY.class, true, true);
            var1.cancel();
            var12.a(true, true);
            var12.a("viaversion");
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
