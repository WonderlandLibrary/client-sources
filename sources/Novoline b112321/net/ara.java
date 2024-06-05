package net;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.af_;

public class ara {
   public static Object a(Class var0, String var1) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      Method var2 = af_.a(var0, var1, new Class[0]);
      return var2.invoke((Object)null, new Object[0]);
   }

   public static Object a(Object var0, String var1) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
      Method var2 = af_.a(var0.getClass(), var1, new Class[0]);
      return var2.invoke(var0, new Object[0]);
   }

   public static Object a(Class var0, String var1, Class var2) throws NoSuchFieldException, IllegalAccessException {
      Field var3 = af_.c(var0, var1);
      var3.setAccessible(true);
      return var3.get((Object)null);
   }

   public static void a(Class var0, String var1, Object var2) throws NoSuchFieldException, IllegalAccessException {
      Field var3 = af_.c(var0, var1);
      var3.setAccessible(true);
      var3.set((Object)null, var2);
   }

   public static Object a(Object var0, String var1, Class var2) throws NoSuchFieldException, IllegalAccessException {
      Field var3 = af_.c(var0.getClass().getSuperclass(), var1);
      var3.setAccessible(true);
      return var3.get(var0);
   }

   public static Object a(Object var0, Class var1, String var2, Class var3) throws NoSuchFieldException, IllegalAccessException {
      Field var4 = af_.c(var1, var2);
      var4.setAccessible(true);
      return var4.get(var0);
   }

   public static Object c(Object var0, String var1, Class var2) throws NoSuchFieldException, IllegalAccessException {
      Field var3 = af_.c(var0.getClass(), var1);
      var3.setAccessible(true);
      return var3.get(var0);
   }

   public static Object b(Object var0, String var1, Class var2) throws NoSuchFieldException, IllegalAccessException {
      Field var3 = af_.b(var0.getClass(), var1);
      var3.setAccessible(true);
      return var3.get(var0);
   }

   public static void a(Object var0, String var1, Object var2) throws NoSuchFieldException, IllegalAccessException {
      Field var3 = af_.c(var0.getClass(), var1);
      var3.setAccessible(true);
      var3.set(var0, var2);
   }
}
