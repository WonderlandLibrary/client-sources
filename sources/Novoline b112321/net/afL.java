package net;

import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.IntStream.Builder;

public class afL {
   public static IntStream a(IntStream var0, IntPredicate var1) {
      return var0.filter(var1);
   }

   public static OptionalInt c(IntStream var0) {
      return var0.max();
   }

   public static Builder a() {
      return IntStream.builder();
   }

   public static int[] b(IntStream var0) {
      return var0.toArray();
   }

   public static long a(IntStream var0) {
      return var0.count();
   }

   public static Stream a(IntStream var0, IntFunction var1) {
      return var0.mapToObj(var1);
   }
}
