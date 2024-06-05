package net;

import com.google.gson.JsonElement;
import net.aKO;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

class k implements PacketHandler {
   final aKO a;

   k(aKO var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      q1.b();
      int var3 = ((Integer)var1.get(Type.VAR_INT, 0)).intValue();
      if(var3 >= 0 && var3 <= 2) {
         ChatRewriter.processTranslate((JsonElement)var1.passthrough(Type.COMPONENT));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
