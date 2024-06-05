package net;

import net.acE;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerPosition;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class aDp extends acE {
   public void registerMap() {
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(aDp::lambda$registerMap$0);
      this.a(Type.DOUBLE);
      this.a(Type.BOOLEAN);
      this.a(aDp::lambda$registerMap$1);
   }

   private static void lambda$registerMap$1(PacketWrapper var0) throws Exception {
      afz.b();
      double var2 = ((Double)var0.get(Type.DOUBLE, 0)).doubleValue();
      double var4 = ((Double)var0.get(Type.DOUBLE, 1)).doubleValue();
      double var6 = ((Double)var0.get(Type.DOUBLE, 2)).doubleValue();
      PlayerPosition var8 = (PlayerPosition)var0.user().b(PlayerPosition.class);
      if(var8.isPositionPacketReceived()) {
         var8.setPositionPacketReceived(false);
         var4 -= 0.01D;
         var0.set(Type.DOUBLE, 1, Double.valueOf(var4));
      }

      var8.setOnGround(((Boolean)var0.get(Type.BOOLEAN, 0)).booleanValue());
      var8.setPos(var2, var4, var6);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      Double var10000 = (Double)var0.read(Type.DOUBLE);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
