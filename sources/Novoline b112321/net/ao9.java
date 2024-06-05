package net;

import net.acE;
import net.aq6;
import viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ao9 extends acE {
   final aq6 c;

   ao9(aq6 var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.INT);
      this.a(Type.POSITION1_14, Type.POSITION);
      this.a(Type.INT);
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aq6.a();
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      int var4 = ((Integer)var1.get(Type.INT, 1)).intValue();
      if(var3 == 1010) {
         var1.set(Type.INT, 1, Integer.valueOf(((Protocol1_13_2To1_14)aq6.a(this.c)).getMappingData().getNewItemId(var4)));
      }

      if(var3 == 2001) {
         var1.set(Type.INT, 1, Integer.valueOf(((Protocol1_13_2To1_14)aq6.b(this.c)).getMappingData().getNewBlockStateId(var4)));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
