package net;

import io.netty.buffer.ByteBuf;
import viaversion.viaversion.api.type.types.StringType;

public class gf {
   public static String a(StringType var0, ByteBuf var1) {
      return var0.read(var1);
   }

   public static void a(StringType var0, ByteBuf var1, String var2) {
      var0.write(var1, var2);
   }
}
