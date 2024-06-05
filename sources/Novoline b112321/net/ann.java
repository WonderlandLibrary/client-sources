package net;

import com.google.gson.JsonElement;
import net.a0V;
import net.acE;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ann extends acE {
   final Protocol1_15_2To1_16 c;

   ann(Protocol1_15_2To1_16 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.VAR_INT);
      this.a(Type.VAR_INT);
      this.a(this::lambda$registerMap$0);
      this.a(ann::lambda$registerMap$1);
   }

   private static void lambda$registerMap$1(PacketWrapper var0) throws Exception {
      a0V.b();
      int var2 = ((Integer)var0.get(Type.VAR_INT, 1)).intValue();
      if(var2 == 20) {
         var0.set(Type.VAR_INT, 1, Integer.valueOf(7));
      }

      if(var2 > 20) {
         --var2;
         var0.set(Type.VAR_INT, 1, Integer.valueOf(var2));
      }

   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      Protocol1_15_2To1_16.access$000(this.c).processText((JsonElement)var1.passthrough(Type.COMPONENT));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
