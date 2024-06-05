package net;

import net.aDD;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.replacement.EntityReplacement;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class a1k implements PacketHandler {
   final aDD a;

   a1k(aDD var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      afz.b();
      EntityTracker var4 = (EntityTracker)var1.user().b(EntityTracker.class);
      EntityReplacement var5 = var4.getEntityReplacement(var3);
      if(var5 != null) {
         var1.cancel();
         byte var6 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
         byte var7 = ((Byte)var1.get(Type.BYTE, 1)).byteValue();
         var5.setYawPitch((float)var6 * 360.0F / 256.0F, (float)var7 * 360.0F / 256.0F);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
