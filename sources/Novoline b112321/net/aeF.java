package net;

import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueTransformer;
import viaversion.viaversion.api.type.Type;

public class aeF {
   private static acE[] b;

   public static void a(ValueTransformer var0, PacketWrapper var1, Object var2) {
      var0.write(var1, var2);
   }

   public static Object b(ValueTransformer var0, PacketWrapper var1, Object var2) {
      return var0.transform(var1, var2);
   }

   public static Type a(ValueTransformer var0) {
      return var0.getInputType();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[2]);
      }

   }
}
