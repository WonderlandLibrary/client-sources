package net;

import java.util.Deque;

public class aIm {
   public static boolean c(Deque var0, Object var1) {
      return var0.add(var1);
   }

   public static boolean b(Deque var0) {
      return var0.isEmpty();
   }

   public static Object c(Deque var0) {
      return var0.pop();
   }

   public static void d(Deque var0) {
      var0.clear();
   }

   public static Object e(Deque var0) {
      return var0.poll();
   }

   public static void b(Deque var0, Object var1) {
      var0.push(var1);
   }

   public static Object f(Deque var0) {
      return var0.getLast();
   }

   public static Object a(Deque var0) {
      return var0.removeLast();
   }

   public static void a(Deque var0, Object var1) {
      var0.addLast(var1);
   }
}
