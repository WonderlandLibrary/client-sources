package net;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class ap5 {
   public static boolean d(LinkedList var0, Object var1) {
      return var0.add(var1);
   }

   public static void b(LinkedList var0) {
      var0.clear();
   }

   public static boolean c(LinkedList var0, Object var1) {
      return var0.remove(var1);
   }

   public static void a(LinkedList var0, Object var1) {
      var0.addFirst(var1);
   }

   public static Iterator g(LinkedList var0) {
      return var0.iterator();
   }

   public static boolean f(LinkedList var0) {
      return var0.isEmpty();
   }

   public static Object a(LinkedList var0, int var1) {
      return var0.get(var1);
   }

   public static Object c(LinkedList var0) {
      return var0.poll();
   }

   public static boolean a(LinkedList var0, Collection var1) {
      return var0.addAll(var1);
   }

   public static void b(LinkedList var0, Object var1) {
      var0.addLast(var1);
   }

   public static Object[] d(LinkedList var0) {
      return var0.toArray();
   }

   public static int a(LinkedList var0) {
      return var0.size();
   }

   public static Object e(LinkedList var0) {
      return var0.getFirst();
   }

   public static ListIterator i(LinkedList var0) {
      return var0.listIterator();
   }

   public static Object h(LinkedList var0) {
      return var0.removeFirst();
   }
}
