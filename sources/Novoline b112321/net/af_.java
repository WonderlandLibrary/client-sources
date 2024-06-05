package net;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.security.ProtectionDomain;
import net.baa;

public class af_ {
   private static int b;

   public static ClassLoader r(Class var0) {
      return var0.getClassLoader();
   }

   public static String b(Class var0) {
      return var0.getName();
   }

   public static Method[] f(Class var0) {
      return var0.getDeclaredMethods();
   }

   public static Field[] l(Class var0) {
      return var0.getDeclaredFields();
   }

   public static boolean a(Class var0, Class var1) {
      return var0.isAssignableFrom(var1);
   }

   public static InputStream a(Class var0, String var1) {
      return var0.getResourceAsStream(var1);
   }

   public static boolean p(Class var0) {
      return var0.desiredAssertionStatus();
   }

   public static Class a(String var0) {
      return Class.forName(baa.a(var0));
   }

   public static Field c(Class var0, String var1) {
      return var0.getDeclaredField(baa.c(var0, var1));
   }

   public static String q(Class var0) {
      return var0.getCanonicalName();
   }

   public static TypeVariable[] c(Class var0) {
      return var0.getTypeParameters();
   }

   public static boolean b(Class var0, Class var1) {
      return var0.isAnnotationPresent(var1);
   }

   public static Class u(Class var0) {
      return var0.getSuperclass();
   }

   public static boolean g(Class var0) {
      return var0.isInterface();
   }

   public static int a(Class var0) {
      return var0.getModifiers();
   }

   public static Object[] n(Class var0) {
      return var0.getSigners();
   }

   public static Method b(Class var0, String var1, Class[] var2) {
      return var0.getMethod(baa.b(var1, var0, var2), var2);
   }

   public static Package m(Class var0) {
      return var0.getPackage();
   }

   public static Object[] k(Class var0) {
      return var0.getEnumConstants();
   }

   public static Object d(Class var0) {
      return var0.newInstance();
   }

   public static Class c(Class var0, Class var1) {
      return var0.asSubclass(var1);
   }

   public static Method a(Class var0, String var1, Class[] var2) {
      return var0.getDeclaredMethod(baa.b(var1, var0, var2), var2);
   }

   public static Constructor a(Class var0, Class[] var1) {
      return var0.getConstructor(var1);
   }

   public static Field[] t(Class var0) {
      return var0.getFields();
   }

   public static Field b(Class var0, String var1) {
      return var0.getField(baa.c(var0, var1));
   }

   public static Class s(Class var0) {
      return var0.getComponentType();
   }

   public static boolean b(Class var0, Object var1) {
      return var0.isInstance(var1);
   }

   public static Object a(Class var0, Object var1) {
      return var0.cast(var1);
   }

   public static Constructor b(Class var0, Class[] var1) {
      return var0.getDeclaredConstructor(var1);
   }

   public static boolean v(Class var0) {
      return var0.isEnum();
   }

   public static Class h(Class var0) {
      return var0.getEnclosingClass();
   }

   public static boolean j(Class var0) {
      return var0.isAnonymousClass();
   }

   public static String e(Class var0) {
      return var0.getSimpleName();
   }

   public static ProtectionDomain i(Class var0) {
      return var0.getProtectionDomain();
   }

   public static Constructor[] o(Class var0) {
      return var0.getDeclaredConstructors();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 116;
   }

   static {
      if(c() == 0) {
         b(115);
      }

   }
}
