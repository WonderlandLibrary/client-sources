package net;

import net.RL;
import net.VV;
import net.a_4;
import net.aoh;
import net.aqN;
import viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class DY implements PacketHandler {
   final aoh a;

   DY(aoh var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aqN.a();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      byte var4 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
      a_4 var5 = RL.a(var4, true);
      if(var5 == null) {
         VV.d().getLogger().warning("Could not find 1.13 entity type " + var4);
      } else {
         if(var5.is(a_4.FALLING_BLOCK)) {
            int var6 = ((Integer)var1.get(Type.INT, 0)).intValue();
            var1.set(Type.INT, 0, Integer.valueOf(((Protocol1_13To1_13_1)aqN.b(this.a.c)).getMappingData().getNewBlockStateId(var6)));
         }

         aqN.a(this.a.c, (PacketWrapper)var1, var3, (EntityType)var5);
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
