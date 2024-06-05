package net;

import java.text.NumberFormat;
import java.util.Locale;

public class akf {
   public static NumberFormat a(Locale var0) {
      return NumberFormat.getIntegerInstance(var0);
   }

   public static String a(NumberFormat var0, long var1) {
      return var0.format(var1);
   }
}
