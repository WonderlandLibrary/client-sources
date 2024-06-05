package net;

import net.VV;
import net.aCr;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class u3 implements PacketHandler {
   final aCr a;

   u3(aCr var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      if(VV.c().isFix1_13FacePlayer()) {
         PlayerPositionStorage1_13 var2 = (PlayerPositionStorage1_13)var1.user().b(PlayerPositionStorage1_13.class);
         byte var3 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
         var2.setX(this.a(var3, 0, var2.getX(), ((Double)var1.get(Type.DOUBLE, 0)).doubleValue()));
         var2.setY(this.a(var3, 1, var2.getY(), ((Double)var1.get(Type.DOUBLE, 1)).doubleValue()));
         var2.setZ(this.a(var3, 2, var2.getZ(), ((Double)var1.get(Type.DOUBLE, 2)).doubleValue()));
      }
   }

   private double a(int var1, int var2, double var3, double var5) {
      return (var1 & 1 << var2) != 0?var3 + var5:var5;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
