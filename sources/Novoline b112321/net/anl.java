package net;

import net.Mo;
import net.acE;
import net.aqX;
import net.cT;
import net.cV;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class anl extends acE {
   final EntityPackets1_16 c;

   anl(EntityPackets1_16 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(EntityPackets1_16.access$000(this.c));
      this.a(anl::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      cV var2 = (cV)var0.user().b(cV.class);
      aqX.a();
      String var3 = (String)var0.read(Type.STRING);
      var0.passthrough(Type.LONG);
      var0.passthrough(Type.UNSIGNED_BYTE);
      var0.read(Type.BYTE);
      cT var4 = (cT)var0.user().b(cT.class);
      int var5 = ((Integer)var0.get(Type.INT, 0)).intValue();
      if(var4.a() != null && var5 == var4.a().getId() && !var3.equals(var2.a())) {
         PacketWrapper var6 = var0.create(Mo.RESPAWN.ordinal());
         var6.write(Type.INT, Integer.valueOf(var5 == 0?-1:0));
         var6.write(Type.LONG, Long.valueOf(0L));
         var6.write(Type.UNSIGNED_BYTE, Short.valueOf((short)0));
         var6.write(Type.STRING, "default");
         var6.send(Protocol1_15_2To1_16.class, true, true);
      }

      var4.c(var5);
      var0.write(Type.STRING, "default");
      var0.read(Type.BOOLEAN);
      if(((Boolean)var0.read(Type.BOOLEAN)).booleanValue()) {
         var0.set(Type.STRING, 0, "flat");
      }

      var0.read(Type.BOOLEAN);
      var2.c(var3);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
