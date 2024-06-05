package net;

import net.afz;
import net.ana;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.replacement.EntityReplacement;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class atd implements PacketHandler {
   final ana a;

   atd(ana var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.b();
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      EntityTracker var4 = (EntityTracker)var1.user().b(EntityTracker.class);
      EntityReplacement var5 = var4.getEntityReplacement(var3);
      if(var5 != null) {
         var1.cancel();
         byte var6 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
         var5.setHeadYaw((float)var6 * 360.0F / 256.0F);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
