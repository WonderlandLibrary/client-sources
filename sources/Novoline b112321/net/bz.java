package net;

import net.aKz;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class bz implements PacketHandler {
   final aKz a;

   bz(aKz var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      String var3 = (String)var1.read(Type.STRING);
      var3 = this.a.c.rewriteTeamMemberName(var3);
      var1.write(Type.STRING, var3);
      q1.b();
      byte var4 = ((Byte)var1.read(Type.BYTE)).byteValue();
      var1.write(Type.BYTE, Byte.valueOf(var4));
      var1.passthrough(Type.STRING);
      if(var4 != 1) {
         var1.passthrough(Type.VAR_INT);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
