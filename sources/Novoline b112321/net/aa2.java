package net;

import net.aMr;
import net.amb;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;

class aa2 implements PacketHandler {
   final aMr a;

   aa2(aMr var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      amb var2 = (amb)Via.getManager().f().b(amb.class);
      var2.a(var1.user());
   }
}
