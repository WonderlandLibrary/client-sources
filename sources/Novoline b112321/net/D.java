package net;

import io.netty.buffer.ByteBuf;
import viaversion.viaversion.api.type.types.VarLongType;

public class D {
   public static long a(VarLongType var0, ByteBuf var1) {
      return var0.readPrimitive(var1);
   }

   public static void a(VarLongType var0, ByteBuf var1, long var2) {
      var0.writePrimitive(var1, var2);
   }
}
