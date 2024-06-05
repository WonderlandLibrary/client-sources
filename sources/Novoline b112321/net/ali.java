package net;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

public class ali {
   public static Comparator a(Function var0) {
      return Comparator.comparing(var0);
   }

   public static Comparator a(ToDoubleFunction var0) {
      return Comparator.comparingDouble(var0);
   }

   public static Comparator a(Comparator var0) {
      return var0.reversed();
   }

   public static Comparator a(ToIntFunction var0) {
      return Comparator.comparingInt(var0);
   }
}
