package net;

import net.afz;
import net.and;
import net.t4;
import viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class mb implements PacketHandler {
   final and a;

   mb(and var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      afz.b();
      int var3 = ((Integer)var1.get(Type.INT, 0)).intValue();
      EntityTracker var4 = (EntityTracker)var1.user().b(EntityTracker.class);
      t4 var5 = (t4)var4.getClientEntityTypes().get(Integer.valueOf(var3));
      if(var5 == t4.MINECART_ABSTRACT) {
         int var6 = ((Integer)var1.get(Type.INT, 2)).intValue();
         var6 = var6 + 12;
         var1.set(Type.INT, 2, Integer.valueOf(var6));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
