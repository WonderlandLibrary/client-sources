package net;

import net.S3;
import net.aRE;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.PlayerPosition;
import viaversion.viarewind.utils.PacketUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a94 extends acE {
   public void registerMap() {
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.DOUBLE);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(Type.BOOLEAN);
      this.a(a94::lambda$registerMap$0);
      this.a(a94::lambda$registerMap$1);
   }

   private static void lambda$registerMap$1(PacketWrapper var0) throws Exception {
      ((BossBarStorage)var0.user().b(BossBarStorage.class)).updateLocation();
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      double var2 = ((Double)var0.get(Type.DOUBLE, 0)).doubleValue();
      S3.b();
      double var4 = ((Double)var0.get(Type.DOUBLE, 1)).doubleValue();
      double var6 = ((Double)var0.get(Type.DOUBLE, 2)).doubleValue();
      float var8 = ((Float)var0.get(Type.FLOAT, 0)).floatValue();
      float var9 = ((Float)var0.get(Type.FLOAT, 1)).floatValue();
      boolean var10 = ((Boolean)var0.get(Type.BOOLEAN, 0)).booleanValue();
      PlayerPosition var11 = (PlayerPosition)var0.user().b(PlayerPosition.class);
      if(var11.getConfirmId() != -1) {
         if(var11.getPosX() != var2 || var11.getPosY() != var4 || var11.getPosZ() != var6 || var11.getYaw() != var8 || var11.getPitch() != var9) {
            return;
         }

         PacketWrapper var12 = var0.create(0);
         var12.write(Type.VAR_INT, Integer.valueOf(var11.getConfirmId()));
         PacketUtil.sendToServer(var12, aRE.class, true, true);
         var11.setConfirmId(-1);
      }

      var11.setPos(var2, var4, var6);
      var11.setYaw(var8);
      var11.setPitch(var9);
      var11.setOnGround(var10);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
