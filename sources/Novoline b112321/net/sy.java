package net;

import com.github.steveice10.opennbt.tag.builtin.StringTag;

public class sy {
   private static String b;

   public static String b(StringTag var0) {
      return var0.getValue();
   }

   public static void a(StringTag var0, String var1) {
      var0.setValue(var1);
   }

   public static String a(StringTag var0) {
      return var0.getName();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("usEi3");
      }

   }
}
