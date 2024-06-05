package net;

import net.N0;
import net.VV;
import net.aoF;
import net.aq6;
import net.jh;
import viaversion.viabackwards.api.entities.storage.EntityData;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a0b implements PacketHandler {
   final aoF a;

   a0b(aoF var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aq6.a();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 1)).intValue();
      N0 var4 = jh.a(var3);
      this.a.c.addTrackedEntity(var1, ((Integer)var1.get(Type.VAR_INT, 0)).intValue(), var4);
      int var5 = EntityPackets1_14.access$300(this.a.c).get(var3);
      if(var5 == -1) {
         EntityData var6 = EntityPackets1_14.access$400(this.a.c, var4);
         VV.d().getLogger().warning("Could not find 1.13.2 entity type for 1.14 entity type " + var3 + "/" + var4);
         var1.cancel();
         var1.set(Type.VAR_INT, 1, Integer.valueOf(var6.getReplacementId()));
      }

      var1.set(Type.VAR_INT, 1, Integer.valueOf(var5));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
