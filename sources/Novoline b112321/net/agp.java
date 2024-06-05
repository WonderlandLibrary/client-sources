package net;

import net.UB;
import net.aQl;
import net.optifine.Reflector;
import net.optifine.ReflectorField;

public class agp {
   private static int b;

   public static boolean a(Class[] var0, Class[] var1) {
      return Reflector.matchesTypes(var0, var1);
   }

   public static Object c(aQl var0, Object[] var1) {
      return Reflector.f(var0, var1);
   }

   public static boolean c(Object var0, aQl var1, Object[] var2) {
      return Reflector.d(var0, var1, var2);
   }

   public static void d(aQl var0, Object[] var1) {
      Reflector.a(var0, var1);
   }

   public static Object b(UB var0, Object[] var1) {
      return Reflector.b(var0, var1);
   }

   public static Object a(Object var0, aQl var1, Object[] var2) {
      return Reflector.f(var0, var1, var2);
   }

   public static double b(Object var0, aQl var1, Object[] var2) {
      return Reflector.c(var0, var1, var2);
   }

   public static Object a(Object var0, ReflectorField var1) {
      return Reflector.getFieldValue(var0, var1);
   }

   public static boolean a(Object var0, ReflectorField var1, Object var2) {
      return Reflector.setFieldValue(var0, var1, var2);
   }

   public static boolean a(UB var0, Object[] var1) {
      return Reflector.a(var0, var1);
   }

   public static boolean a(Object var0) {
      return Reflector.postForgeBusEvent(var0);
   }

   public static float a(Object var0, ReflectorField var1, float var2) {
      return Reflector.getFieldValueFloat(var0, var1, var2);
   }

   public static boolean e(aQl var0, Object[] var1) {
      return Reflector.b(var0, var1);
   }

   public static void f(Object var0, aQl var1, Object[] var2) {
      Reflector.g(var0, var1, var2);
   }

   public static float b(aQl var0, Object[] var1) {
      return Reflector.g(var0, var1);
   }

   public static int e(Object var0, aQl var1, Object[] var2) {
      return Reflector.e(var0, var1, var2);
   }

   public static String d(Object var0, aQl var1, Object[] var2) {
      return Reflector.b(var0, var1, var2);
   }

   public static String a(aQl var0, Object[] var1) {
      return Reflector.e(var0, var1);
   }

   public static Object a(ReflectorField var0) {
      return Reflector.getFieldValue(var0);
   }

   public static int f(aQl var0, Object[] var1) {
      return Reflector.d(var0, var1);
   }

   public static boolean a(ReflectorField var0, Object var1) {
      return Reflector.setFieldValue(var0, var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 50;
   }

   static {
      if(a() == 0) {
         b(19);
      }

   }
}
