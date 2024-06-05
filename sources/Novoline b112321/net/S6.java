package net;

import net.aMZ;
import net.aXe;
import net.cq;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9to1_8.chat.GameMode;

class S6 implements PacketHandler {
   final aMZ a;

   S6(aMZ var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      int var2 = aXe.b();
      if(((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 3) {
         int var3 = ((Float)var1.get(Type.FLOAT, 0)).intValue();
         ((cq)var1.user().b(cq.class)).a(GameMode.getById(var3));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
