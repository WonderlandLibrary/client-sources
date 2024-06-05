package net;

import java.io.BufferedReader;
import java.util.stream.Stream;

public class a23 {
   public static String c(BufferedReader var0) {
      return var0.readLine();
   }

   public static void d(BufferedReader var0) {
      var0.close();
   }

   public static void a(BufferedReader var0, int var1) {
      var0.mark(var1);
   }

   public static void b(BufferedReader var0) {
      var0.reset();
   }

   public static Stream a(BufferedReader var0) {
      return var0.lines();
   }
}
