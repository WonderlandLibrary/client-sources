package net;

import net.VV;
import net.aCN;
import net.aqI;
import net.ayk;
import net.z8;
import viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage.PlayerPositionStorage1_13;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class AZ implements PacketHandler {
   final aCN a;

   AZ(aCN var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      aqI.a();
      var1.cancel();
      if(VV.c().isFix1_13FacePlayer()) {
         int var3 = ((Integer)var1.read(Type.VAR_INT)).intValue();
         double var4 = ((Double)var1.read(Type.DOUBLE)).doubleValue();
         double var6 = ((Double)var1.read(Type.DOUBLE)).doubleValue();
         double var8 = ((Double)var1.read(Type.DOUBLE)).doubleValue();
         PlayerPositionStorage1_13 var10 = (PlayerPositionStorage1_13)var1.user().b(PlayerPositionStorage1_13.class);
         PacketWrapper var11 = var1.create(47);
         var11.write(Type.DOUBLE, Double.valueOf(0.0D));
         var11.write(Type.DOUBLE, Double.valueOf(0.0D));
         var11.write(Type.DOUBLE, Double.valueOf(0.0D));
         z8.a(var11, var10.getX(), var3 == 1?var10.getY() + 1.62D:var10.getY(), var10.getZ(), var4, var6, var8);
         var11.write(Type.BYTE, Byte.valueOf((byte)7));
         var11.write(Type.VAR_INT, Integer.valueOf(-1));
         var11.send(ayk.class, true, true);
      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
