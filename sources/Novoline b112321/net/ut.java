package net;

import net.aDg;
import net.afz;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ut implements PacketHandler {
   final aDg a;

   ut(aDg var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.a();
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      EntityTracker var4 = (EntityTracker)var1.user().b(EntityTracker.class);
      if(var4.getEntityReplacement(var3) != null) {
         var1.cancel();
      } else {
         int var5 = ((Integer)var1.passthrough(Type.INT)).intValue();
         int var6 = 0;
         if(var6 < var5) {
            var1.passthrough(Type.STRING);
            var1.passthrough(Type.DOUBLE);
            int var7 = ((Integer)var1.read(Type.VAR_INT)).intValue();
            var1.write(Type.SHORT, Short.valueOf((short)var7));
            int var8 = 0;
            if(var8 < var7) {
               var1.passthrough(Type.UUID);
               var1.passthrough(Type.DOUBLE);
               var1.passthrough(Type.BYTE);
               ++var8;
            }

            ++var6;
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
