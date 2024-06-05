package net;

import net.aKA;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;

class a04 implements PacketHandler {
   final aKA a;

   a04(aKA var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      String[] var2 = EntityIdRewriter.b();
      if(Via.getConfig().isPistonAnimationPatch()) {
         int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
         if(var3 == 33 || var3 == 29) {
            var1.cancel();
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
