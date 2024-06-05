package net;

import java.util.Queue;

public class vw {
   public static boolean b(Queue var0) {
      return var0.isEmpty();
   }

   public static Object a(Queue var0) {
      return var0.poll();
   }

   public static boolean a(Queue var0, Object var1) {
      return var0.add(var1);
   }

   public static int e(Queue var0) {
      return var0.size();
   }

   public static Object d(Queue var0) {
      return var0.remove();
   }

   public static void c(Queue var0) {
      var0.clear();
   }
}
