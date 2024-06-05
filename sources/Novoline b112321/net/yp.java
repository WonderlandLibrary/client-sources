package net;

import net.aKY;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;

class yp implements PacketHandler {
   final aKY a;

   yp(aKY var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      q1.b();
      byte var3 = ((Byte)var1.get(Type.BYTE, 0)).byteValue();
      if(var3 == 0 || var3 == 2) {
         String var4 = (String)var1.read(Type.STRING);
         var1.write(Type.COMPONENT, ChatRewriter.legacyTextToJson(var4));
         String var5 = (String)var1.read(Type.STRING);
         var1.write(Type.VAR_INT, Integer.valueOf(var5.equals("integer")?0:1));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
