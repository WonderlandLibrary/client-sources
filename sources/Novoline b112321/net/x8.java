package net;

import viaversion.viaversion.exception.InformativeException;

public class x8 {
   private static String[] b;

   public static InformativeException a(InformativeException var0, Class var1) {
      return var0.addSource(var1);
   }

   public static InformativeException a(InformativeException var0, String var1, Object var2) {
      return var0.set(var1, var2);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[4]);
      }

   }
}
