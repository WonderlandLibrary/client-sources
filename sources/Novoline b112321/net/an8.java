package net;

import net.acE;
import net.aqX;
import net.axs;
import net.cT;
import net.cV;
import viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class an8 extends acE {
   final EntityPackets1_16 c;

   an8(EntityPackets1_16 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.BYTE, Type.NOTHING);
      this.a(Type.STRING_ARRAY, Type.NOTHING);
      this.a(Type.NBT, Type.NOTHING);
      this.a(EntityPackets1_16.access$000(this.c));
      this.a(an8::lambda$registerMap$0);
      this.a(Type.LONG);
      this.a(Type.UNSIGNED_BYTE);
      this.a(this::lambda$registerMap$1);
   }

   private void lambda$registerMap$1(PacketWrapper var1) throws Exception {
      aqX.a();
      cT var3 = (cT)var1.user().b(cT.class);
      var3.c(((Integer)var1.get(Type.INT, 1)).intValue());
      this.c.getEntityTracker(var1.user()).trackEntityType(((Integer)var1.get(Type.INT, 0)).intValue(), axs.PLAYER);
      var1.write(Type.STRING, "default");
      var1.passthrough(Type.VAR_INT);
      var1.passthrough(Type.BOOLEAN);
      var1.passthrough(Type.BOOLEAN);
      var1.read(Type.BOOLEAN);
      if(((Boolean)var1.read(Type.BOOLEAN)).booleanValue()) {
         var1.set(Type.STRING, 0, "flat");
      }

   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      cV var1 = (cV)var0.user().b(cV.class);
      var1.c((String)var0.read(Type.STRING));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
