package net;

import net.a97;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Particle;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class u4 implements PacketHandler {
   final a97 a;

   u4(a97 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.a();
      int var3 = ((Integer)var1.read(Type.INT)).intValue();
      Particle var4 = Particle.find(var3);
      if(var4 == null) {
         var4 = Particle.CRIT;
      }

      var1.write(Type.STRING, var4.name);
      var1.read(Type.BOOLEAN);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
