package net;

import io.netty.buffer.ByteBuf;
import net.acE;
import viaversion.viaversion.api.type.Type;

public class t6 {
   private static acE[] b;

   public static String c(Type var0) {
      return var0.getTypeName();
   }

   public static Class b(Type var0) {
      return var0.getBaseClass();
   }

   public static Object a(Type var0, ByteBuf var1) {
      return var0.read(var1);
   }

   public static Class a(Type var0) {
      return var0.getOutputClass();
   }

   public static void a(Type var0, ByteBuf var1, Object var2) {
      var0.write(var1, var2);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[4]);
      }

   }
}
