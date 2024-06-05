package net;

import net.afz;
import net.and;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viarewind.replacement.EntityReplacement;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aa4 implements PacketHandler {
   final and a;

   aa4(and var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      afz.a();
      EntityTracker var4 = (EntityTracker)var1.user().b(EntityTracker.class);
      EntityReplacement var5 = var4.getEntityReplacement(var3);
      if(var5 != null) {
         var1.cancel();
         int var6 = ((Integer)var1.get(Type.INT, 1)).intValue();
         int var7 = ((Integer)var1.get(Type.INT, 2)).intValue();
         int var8 = ((Integer)var1.get(Type.INT, 3)).intValue();
         byte var9 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
         byte var10 = ((Byte)var1.get(Type.BYTE, 1)).byteValue();
         var5.setLocation((double)var6 / 32.0D, (double)var7 / 32.0D, (double)var8 / 32.0D);
         var5.setYawPitch((float)var9 * 360.0F / 256.0F, (float)var10 * 360.0F / 256.0F);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
