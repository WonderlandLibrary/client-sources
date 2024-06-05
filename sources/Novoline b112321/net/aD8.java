package net;

import net.acE;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerAbilities;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class aD8 extends acE {
   public void registerMap() {
      this.a(Type.BYTE);
      this.a(Type.FLOAT);
      this.a(Type.FLOAT);
      this.a(aD8::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      afz.a();
      byte var2 = ((Byte)var0.get(Type.BYTE, 0)).byteValue();
      float var3 = ((Float)var0.get(Type.FLOAT, 0)).floatValue();
      float var4 = ((Float)var0.get(Type.FLOAT, 1)).floatValue();
      PlayerAbilities var5 = (PlayerAbilities)var0.user().b(PlayerAbilities.class);
      var5.setInvincible((var2 & 8) == 8);
      var5.setAllowFly((var2 & 4) == 4);
      var5.setFlying((var2 & 2) == 2);
      var5.setCreative((var2 & 1) == 1);
      var5.setFlySpeed(var3);
      var5.setWalkSpeed(var4);
      if(var5.isSprinting() && var5.isFlying()) {
         var0.set(Type.FLOAT, 0, Float.valueOf(var5.getFlySpeed() * 2.0F));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
