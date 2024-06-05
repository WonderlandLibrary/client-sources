package net;

import net.acE;
import net.aq3;
import net.cW;
import viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ao_ extends acE {
   final aq3 c;

   ao_(aq3 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(ao_::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      aq3.a();
      float var2 = ((Float)var0.passthrough(Type.FLOAT)).floatValue();
      if(var2 <= 0.0F) {
         if(((cW)var0.user().b(cW.class)).a()) {
            PacketWrapper var3 = var0.create(4);
            var3.write(Type.VAR_INT, Integer.valueOf(0));
            var3.sendToServer(Protocol1_14_4To1_15.class);
         }
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
