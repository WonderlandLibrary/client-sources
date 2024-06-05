package net;

import net.a9v;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.Windows;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class ar3 implements PacketHandler {
   final a9v a;

   ar3(a9v var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      short var2 = ((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue();
      String var3 = (String)var1.get(Type.STRING, 0);
      ((Windows)var1.user().b(Windows.class)).put(var2, var3);
   }
}
