package net;

import cc.novoline.modules.configurations.property.object.StringProperty;
import java.util.List;

public class aXh {
   private static String[] b;

   public static StringProperty a(StringProperty var0, String[] var1) {
      return var0.acceptableValues(var1);
   }

   public static Object a(StringProperty var0) {
      return var0.get();
   }

   public static boolean c(StringProperty var0, String var1) {
      return var0.equals(var1);
   }

   public static void a(StringProperty var0, String var1) {
      var0.set(var1);
   }

   public static List b(StringProperty var0) {
      return var0.getAcceptableValues();
   }

   public static StringProperty a(StringProperty var0, Object var1) {
      return var0.append(var1);
   }

   public static boolean b(StringProperty var0, String var1) {
      return var0.equalsIgnoreCase(var1);
   }

   public static StringProperty a(String var0) {
      return StringProperty.of(var0);
   }

   public static StringProperty a() {
      return StringProperty.create();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[2]);
      }

   }
}
