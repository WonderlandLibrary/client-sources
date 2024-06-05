package net;

import net.acE;
import net.aqX;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class anB extends acE {
   final EntityPackets1_16 c;

   anB(EntityPackets1_16 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aqX.a();
      var1.passthrough(Type.VAR_INT);
      int var3 = ((Integer)var1.passthrough(Type.INT)).intValue();
      int var4 = 0;
      if(var4 < var3) {
         String var5 = (String)var1.read(Type.STRING);
         String var6 = (String)((Protocol1_15_2To1_16)EntityPackets1_16.a(this.c)).getMappingData().getAttributeMappings().get(var5);
         var1.write(Type.STRING, var6 != null?var6:var5.replace("minecraft:", ""));
         var1.passthrough(Type.DOUBLE);
         int var7 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
         int var8 = 0;
         if(var8 < var7) {
            var1.passthrough(Type.UUID);
            var1.passthrough(Type.DOUBLE);
            var1.passthrough(Type.BYTE);
            ++var8;
         }

         ++var4;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
