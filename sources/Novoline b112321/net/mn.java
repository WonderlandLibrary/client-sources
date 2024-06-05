package net;

import java.util.Properties;
import net.shadersmod.client.PropertyDefaultTrueFalse;

public class mn {
   public static void e(PropertyDefaultTrueFalse var0) {
      var0.b();
   }

   public static boolean a(PropertyDefaultTrueFalse var0, String var1) {
      return var0.setPropertyValue(var1);
   }

   public static String g(PropertyDefaultTrueFalse var0) {
      return var0.getPropertyValue();
   }

   public static void b(PropertyDefaultTrueFalse var0) {
      var0.resetValue();
   }

   public static boolean a(PropertyDefaultTrueFalse var0, Properties var1) {
      return var0.a(var1);
   }

   public static boolean a(PropertyDefaultTrueFalse var0) {
      return var0.isDefault();
   }

   public static boolean d(PropertyDefaultTrueFalse var0) {
      return var0.isTrue();
   }

   public static boolean c(PropertyDefaultTrueFalse var0) {
      return var0.isFalse();
   }

   public static String f(PropertyDefaultTrueFalse var0) {
      return var0.getUserValue();
   }
}
