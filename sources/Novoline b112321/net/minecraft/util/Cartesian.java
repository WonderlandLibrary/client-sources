package net.minecraft.util;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.lang.reflect.Array;
import java.util.ArrayList;
import net.nw;
import net.minecraft.util.Cartesian$GetList;

public class Cartesian {
   public static Iterable cartesianProduct(Class var0, Iterable var1) {
      return new nw(var0, (Iterable[])toArray(Iterable.class, var1));
   }

   public static Iterable cartesianProduct(Iterable var0) {
      return arraysAsLists(cartesianProduct(Object.class, var0));
   }

   private static Iterable arraysAsLists(Iterable var0) {
      return Iterables.transform(var0, new Cartesian$GetList());
   }

   private static Object[] toArray(Class var0, Iterable var1) {
      ArrayList var2 = Lists.newArrayList();

      for(Object var4 : var1) {
         var2.add(var4);
      }

      return (Object[])var2.toArray(createArray(var0, var2.size()));
   }

   private static Object[] createArray(Class var0, int var1) {
      return (Object[])((Object[])Array.newInstance(var0, var1));
   }

   static Object[] access$200(Class var0, int var1) {
      return createArray(var0, var1);
   }
}
