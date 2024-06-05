package net;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class jE {
   private static boolean b;

   public static ArrayList e() {
      return Lists.newArrayList();
   }

   public static ArrayList a(Object[] var0) {
      return Lists.newArrayList(var0);
   }

   public static ArrayList a(Iterable var0) {
      return Lists.newArrayList(var0);
   }

   public static ArrayList a(int var0) {
      return Lists.newArrayListWithCapacity(var0);
   }

   public static LinkedList a() {
      return Lists.newLinkedList();
   }

   public static List a(List var0) {
      return Lists.reverse(var0);
   }

   public static ArrayList a(Iterator var0) {
      return Lists.newArrayList(var0);
   }

   public static CopyOnWriteArrayList b() {
      return Lists.newCopyOnWriteArrayList();
   }

   public static ArrayList b(int var0) {
      return Lists.newArrayListWithExpectedSize(var0);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean d() {
      return b;
   }

   public static boolean c() {
      boolean var0 = d();
      return true;
   }

   static {
      if(!c()) {
         b(true);
      }

   }
}
