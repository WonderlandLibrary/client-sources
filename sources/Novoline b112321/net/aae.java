package net;

import net.aWQ;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class aae implements PacketHandler {
   final aWQ a;

   aae(aWQ var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      q1.b();
      int var3 = ((Integer)var1.read(Type.VAR_INT)).intValue();
      byte var4 = ((Byte)var1.read(Type.BYTE)).byteValue();
      String var5 = var3 == 0?"SEQUENCE":(var3 == 1?"AUTO":"REDSTONE");
      var1.write(Type.BOOLEAN, Boolean.valueOf((var4 & 1) != 0));
      var1.write(Type.STRING, var5);
      var1.write(Type.BOOLEAN, Boolean.valueOf((var4 & 2) != 0));
      var1.write(Type.BOOLEAN, Boolean.valueOf((var4 & 4) != 0));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
