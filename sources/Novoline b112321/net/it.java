package net;

import net.aMp;
import net.aRY;
import net.aXe;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class it implements PacketHandler {
   final aMp a;

   it(aMp var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aXe.c();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      int var4 = ((Integer)var1.get(Type.VAR_INT, 1)).intValue();
      int var5 = 0;
      if(var5 < var4) {
         var1.passthrough(Type.UUID);
         if(var3 == 0) {
            var1.passthrough(Type.STRING);
            int var6 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
            int var7 = 0;
            if(var7 < var6) {
               var1.passthrough(Type.STRING);
               var1.passthrough(Type.STRING);
               boolean var8 = ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue();
               if(var8) {
                  var1.passthrough(Type.STRING);
               }

               ++var7;
            }

            var1.passthrough(Type.VAR_INT);
            var1.passthrough(Type.VAR_INT);
            var7 = ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue();
            if(var7) {
               aRY.i.write(var1, var1.read(Type.STRING));
            }
         }

         if(var3 == 1 || var3 == 2) {
            var1.passthrough(Type.VAR_INT);
         }

         if(var3 == 3) {
            boolean var10 = ((Boolean)var1.passthrough(Type.BOOLEAN)).booleanValue();
            aRY.i.write(var1, var1.read(Type.STRING));
         }

         if(var3 == 4) {
            ;
         }

         ++var5;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
