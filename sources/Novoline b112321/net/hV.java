package net;

import java.net.URL;
import sun.misc.URLClassPath;

public class hV {
   private static int[] b;

   public static void a(URLClassPath var0, URL var1) {
      var0.addURL(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
