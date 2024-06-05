package net;

import com.google.gson.JsonElement;
import net.C4;
import net.aYz;
import net.acE;
import viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class anu extends acE {
   final Protocol1_16_1To1_16_2 c;

   anu(Protocol1_16_1To1_16_2 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aYz.b();
      JsonElement var3 = (JsonElement)var1.passthrough(Type.COMPONENT);
      Protocol1_16_1To1_16_2.access$000(this.c).processText(var3);
      byte var4 = ((Byte)var1.passthrough(Type.BYTE)).byteValue();
      if(var4 == 2) {
         var1.clearPacket();
         var1.setId(C4.TITLE.ordinal());
         var1.write(Type.VAR_INT, Integer.valueOf(2));
         var1.write(Type.COMPONENT, var3);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
