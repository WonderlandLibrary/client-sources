package net;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class abR {
   public static Object a(CopyOnWriteArrayList var0, int var1) {
      return var0.get(var1);
   }

   public static boolean a(CopyOnWriteArrayList var0, Object var1) {
      return var0.remove(var1);
   }

   public static boolean b(CopyOnWriteArrayList var0, Object var1) {
      return var0.add(var1);
   }

   public static Iterator a(CopyOnWriteArrayList var0) {
      return var0.iterator();
   }
}
