package net;

import net.N0;
import net.acE;
import net.aq6;
import viaversion.viabackwards.api.entities.storage.EntityTracker$ProtocolEntityTracker;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aol extends acE {
   final EntityPackets1_14 c;

   aol(EntityPackets1_14 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aq6.a();
      int var3 = ((Integer)var1.passthrough(Type.INT)).intValue();
      byte var4 = ((Byte)var1.passthrough(Type.BYTE)).byteValue();
      if(var4 == 3) {
         EntityTracker$ProtocolEntityTracker var5 = this.c.getEntityTracker(var1.user());
         EntityType var6 = var5.getEntityType(var3);
         if(var6 == N0.PLAYER) {
            int var7 = 0;
            if(var7 <= 5) {
               PacketWrapper var8 = var1.create(66);
               var8.write(Type.VAR_INT, Integer.valueOf(var3));
               var8.write(Type.VAR_INT, Integer.valueOf(var7));
               var8.write(Type.FLAT_VAR_INT_ITEM, (Object)null);
               var8.send(Protocol1_13_2To1_14.class, true, true);
               ++var7;
            }

         }
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
