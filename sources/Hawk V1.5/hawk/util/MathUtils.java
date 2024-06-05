package hawk.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {
   public static double square(double var0) {
      var0 *= var0;
      return var0;
   }

   public static double randomNumber(double var0, double var2) {
      return Math.random() * (var0 - var2) + var2;
   }

   public static double round(double var0, double var2) {
      if (var2 < 0.0D) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal var4 = new BigDecimal(var0);
         var4 = var4.setScale((int)var2, RoundingMode.HALF_UP);
         return var4.doubleValue();
      }
   }
}
