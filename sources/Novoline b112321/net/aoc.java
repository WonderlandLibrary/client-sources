package net;

import net.acE;
import net.aq6;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aoc extends acE {
   final aq6 c;

   aoc(aq6 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.POSITION1_14, Type.POSITION);
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.VAR_INT);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aq6.a();
      int var3 = ((Protocol1_13_2To1_14)aq6.d(this.c)).getMappingData().getNewBlockId(((Integer)var1.get(Type.VAR_INT, 0)).intValue());
      if(var3 == -1) {
         var1.cancel();
      } else {
         var1.set(Type.VAR_INT, 0, Integer.valueOf(var3));
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
