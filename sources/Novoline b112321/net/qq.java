package net;

import net.a97;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Particle;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class qq implements PacketHandler {
   final a97 a;

   qq(a97 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      label0: {
         afz.b();
         String var3 = (String)var1.get(Type.STRING, 0);
         Particle var4 = Particle.find(var3);
         if(var4 == Particle.ICON_CRACK || var4 == Particle.BLOCK_CRACK || var4 == Particle.BLOCK_DUST) {
            int var5 = ((Integer)var1.read(Type.VAR_INT)).intValue();
            int var6 = var4 == Particle.ICON_CRACK?((Integer)var1.read(Type.VAR_INT)).intValue():0;
            if(var5 >= 256 && var5 <= 422 || var5 >= 2256 && var5 <= 2267) {
               var4 = Particle.ICON_CRACK;
            }

            if((var5 < 0 || var5 > 164) && (var5 < 170 || var5 > 175)) {
               break label0;
            }

            if(var4 == Particle.ICON_CRACK) {
               var4 = Particle.BLOCK_CRACK;
               break label0;
            }

            var3 = var4.name + "_" + var5 + "_" + var6;
         }

         var1.set(Type.STRING, 0, var3);
         return;
      }

      var1.cancel();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
