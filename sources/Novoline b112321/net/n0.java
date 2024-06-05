package net;

import net.S3;
import net.a9L;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class n0 implements PacketHandler {
   final a9L a;

   n0(a9L var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      short var3 = ((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue();
      Windows var4 = (Windows)var1.user().b(Windows.class);
      S3.b();
      String var5 = var4.get(var3);
      if(var5 != null) {
         if(var5.equalsIgnoreCase("minecraft:brewing_stand")) {
            short var6 = ((Short)var1.get(Type.SHORT, 0)).shortValue();
            if(var6 > 3) {
               ++var6;
               var1.set(Type.SHORT, 0, Short.valueOf(var6));
            }
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
