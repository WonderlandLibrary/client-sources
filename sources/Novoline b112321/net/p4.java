package net;

import net.aWC;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

class p4 implements PacketHandler {
   final aWC a;

   p4(aWC var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      q1.b();
      float var3 = ((Float)var1.read(Type.FLOAT)).floatValue();
      long var4 = ((Long)var1.read(Type.VAR_LONG)).longValue();
      byte var6 = ((Byte)var1.read(Type.BYTE)).byteValue();
      var1.write(Type.BOOLEAN, Boolean.valueOf((var6 & 1) != 0));
      var1.write(Type.BOOLEAN, Boolean.valueOf((var6 & 2) != 0));
      var1.write(Type.BOOLEAN, Boolean.valueOf((var6 & 4) != 0));
      var1.write(Type.FLOAT, Float.valueOf(var3));
      var1.write(Type.VAR_LONG, Long.valueOf(var4));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
