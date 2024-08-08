package net.jodah.typetools;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import sun.misc.Unsafe;

public final class TypeResolver {
   private static final Map TYPE_VARIABLE_CACHE = Collections.synchronizedMap(new WeakHashMap());
   private static boolean CACHE_ENABLED = true;
   private static boolean RESOLVES_LAMBDAS;
   private static Method GET_CONSTANT_POOL;
   private static Method GET_CONSTANT_POOL_SIZE;
   private static Method GET_CONSTANT_POOL_METHOD_AT;
   private static final Map OBJECT_METHODS = new HashMap();
   private static final Map PRIMITIVE_WRAPPERS;
   private static final Double JAVA_VERSION = Double.parseDouble(System.getProperty("java.specification.version", "0"));

   private TypeResolver() {
   }

   public static void enableCache() {
      CACHE_ENABLED = true;
   }

   public static void disableCache() {
      TYPE_VARIABLE_CACHE.clear();
      CACHE_ENABLED = false;
   }

   public static Class resolveRawArgument(Class var0, Class var1) {
      return resolveRawArgument(resolveGenericType(var0, var1), var1);
   }

   public static Class resolveRawArgument(Type var0, Class var1) {
      Class[] var2 = resolveRawArguments(var0, var1);
      if (var2 == null) {
         return TypeResolver.Unknown.class;
      } else if (var2.length != 1) {
         throw new IllegalArgumentException("Expected 1 argument for generic type " + var0 + " but found " + var2.length);
      } else {
         return var2[0];
      }
   }

   public static Class[] resolveRawArguments(Class var0, Class var1) {
      return resolveRawArguments(resolveGenericType(var0, var1), var1);
   }

   public static Class[] resolveRawArguments(Type var0, Class var1) {
      Class[] var2 = null;
      Class var3 = null;
      if (RESOLVES_LAMBDAS && var1.isSynthetic()) {
         Class var4 = var0 instanceof ParameterizedType && ((ParameterizedType)var0).getRawType() instanceof Class ? (Class)((ParameterizedType)var0).getRawType() : (var0 instanceof Class ? (Class)var0 : null);
         if (var4 != null && var4.isInterface()) {
            var3 = var4;
         }
      }

      if (var0 instanceof ParameterizedType) {
         ParameterizedType var7 = (ParameterizedType)var0;
         Type[] var5 = var7.getActualTypeArguments();
         var2 = new Class[var5.length];

         for(int var6 = 0; var6 < var5.length; ++var6) {
            var2[var6] = resolveRawClass(var5[var6], var1, var3);
         }
      } else if (var0 instanceof TypeVariable) {
         var2 = new Class[]{resolveRawClass(var0, var1, var3)};
      } else if (var0 instanceof Class) {
         TypeVariable[] var8 = ((Class)var0).getTypeParameters();
         var2 = new Class[var8.length];

         for(int var9 = 0; var9 < var8.length; ++var9) {
            var2[var9] = resolveRawClass(var8[var9], var1, var3);
         }
      }

      return var2;
   }

   public static Type resolveGenericType(Class var0, Type var1) {
      Class var2;
      if (var1 instanceof ParameterizedType) {
         var2 = (Class)((ParameterizedType)var1).getRawType();
      } else {
         var2 = (Class)var1;
      }

      if (var0.equals(var2)) {
         return var1;
      } else {
         Type var3;
         if (var0.isInterface()) {
            Type[] var4 = var2.getGenericInterfaces();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Type var7 = var4[var6];
               if (var7 != null && !var7.equals(Object.class) && (var3 = resolveGenericType(var0, var7)) != null) {
                  return var3;
               }
            }
         }

         Type var8 = var2.getGenericSuperclass();
         return var8 != null && !var8.equals(Object.class) && (var3 = resolveGenericType(var0, var8)) != null ? var3 : null;
      }
   }

   public static Class resolveRawClass(Type var0, Class var1) {
      return resolveRawClass(var0, var1, (Class)null);
   }

   private static Class resolveRawClass(Type var0, Class var1, Class var2) {
      if (var0 instanceof Class) {
         return (Class)var0;
      } else if (var0 instanceof ParameterizedType) {
         return resolveRawClass(((ParameterizedType)var0).getRawType(), var1, var2);
      } else if (var0 instanceof GenericArrayType) {
         GenericArrayType var6 = (GenericArrayType)var0;
         Class var4 = resolveRawClass(var6.getGenericComponentType(), var1, var2);
         return Array.newInstance(var4, 0).getClass();
      } else {
         if (var0 instanceof TypeVariable) {
            TypeVariable var3 = (TypeVariable)var0;
            Type var5 = (Type)getTypeVariableMap(var1, var2).get(var3);
            var0 = var5 == null ? resolveBound(var3) : resolveRawClass(var5, var1, var2);
         }

         return var0 instanceof Class ? (Class)var0 : TypeResolver.Unknown.class;
      }
   }

   private static Map getTypeVariableMap(Class var0, Class var1) {
      Reference var2 = (Reference)TYPE_VARIABLE_CACHE.get(var0);
      Object var3 = var2 != null ? (Map)var2.get() : null;
      if (var3 == null) {
         var3 = new HashMap();
         if (var1 != null) {
            populateLambdaArgs(var1, var0, (Map)var3);
         }

         populateSuperTypeArgs(var0.getGenericInterfaces(), (Map)var3, var1 != null);
         Type var4 = var0.getGenericSuperclass();

         Class var5;
         for(var5 = var0.getSuperclass(); var5 != null && !Object.class.equals(var5); var5 = var5.getSuperclass()) {
            if (var4 instanceof ParameterizedType) {
               populateTypeArgs((ParameterizedType)var4, (Map)var3, false);
            }

            populateSuperTypeArgs(var5.getGenericInterfaces(), (Map)var3, false);
            var4 = var5.getGenericSuperclass();
         }

         for(var5 = var0; var5.isMemberClass(); var5 = var5.getEnclosingClass()) {
            var4 = var5.getGenericSuperclass();
            if (var4 instanceof ParameterizedType) {
               populateTypeArgs((ParameterizedType)var4, (Map)var3, var1 != null);
            }
         }

         if (CACHE_ENABLED) {
            TYPE_VARIABLE_CACHE.put(var0, new WeakReference(var3));
         }
      }

      return (Map)var3;
   }

   private static void populateSuperTypeArgs(Type[] var0, Map var1, boolean var2) {
      Type[] var3 = var0;
      int var4 = var0.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type var6 = var3[var5];
         if (var6 instanceof ParameterizedType) {
            ParameterizedType var7 = (ParameterizedType)var6;
            if (!var2) {
               populateTypeArgs(var7, var1, var2);
            }

            Type var8 = var7.getRawType();
            if (var8 instanceof Class) {
               populateSuperTypeArgs(((Class)var8).getGenericInterfaces(), var1, var2);
            }

            if (var2) {
               populateTypeArgs(var7, var1, var2);
            }
         } else if (var6 instanceof Class) {
            populateSuperTypeArgs(((Class)var6).getGenericInterfaces(), var1, var2);
         }
      }

   }

   private static void populateTypeArgs(ParameterizedType var0, Map var1, boolean var2) {
      if (var0.getRawType() instanceof Class) {
         TypeVariable[] var3 = ((Class)var0.getRawType()).getTypeParameters();
         Type[] var4 = var0.getActualTypeArguments();
         if (var0.getOwnerType() != null) {
            Type var5 = var0.getOwnerType();
            if (var5 instanceof ParameterizedType) {
               populateTypeArgs((ParameterizedType)var5, var1, var2);
            }
         }

         for(int var10 = 0; var10 < var4.length; ++var10) {
            TypeVariable var6 = var3[var10];
            Type var7 = var4[var10];
            if (var7 instanceof Class) {
               var1.put(var6, var7);
            } else if (var7 instanceof GenericArrayType) {
               var1.put(var6, var7);
            } else if (var7 instanceof ParameterizedType) {
               var1.put(var6, var7);
            } else if (var7 instanceof TypeVariable) {
               TypeVariable var8 = (TypeVariable)var7;
               Type var9;
               if (var2) {
                  var9 = (Type)var1.get(var6);
                  if (var9 != null) {
                     var1.put(var8, var9);
                     continue;
                  }
               }

               var9 = (Type)var1.get(var8);
               if (var9 == null) {
                  var9 = resolveBound(var8);
               }

               var1.put(var6, var9);
            }
         }
      }

   }

   public static Type resolveBound(TypeVariable var0) {
      Type[] var1 = var0.getBounds();
      if (var1.length == 0) {
         return TypeResolver.Unknown.class;
      } else {
         Type var2 = var1[0];
         if (var2 instanceof TypeVariable) {
            var2 = resolveBound((TypeVariable)var2);
         }

         return (Type)(var2 == Object.class ? TypeResolver.Unknown.class : var2);
      }
   }

   private static void populateLambdaArgs(Class var0, Class var1, Map var2) {
      if (RESOLVES_LAMBDAS) {
         Method[] var3 = var0.getMethods();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method var6 = var3[var5];
            if (!isDefaultMethod(var6) && !Modifier.isStatic(var6.getModifiers()) && !var6.isBridge()) {
               Method var7 = (Method)OBJECT_METHODS.get(var6.getName());
               if (var7 == null || !Arrays.equals(var6.getTypeParameters(), var7.getTypeParameters())) {
                  Type var8 = var6.getGenericReturnType();
                  Type[] var9 = var6.getGenericParameterTypes();
                  Member var10 = getMemberRef(var1);
                  if (var10 == null) {
                     return;
                  } else {
                     if (var8 instanceof TypeVariable) {
                        Class var11 = var10 instanceof Method ? ((Method)var10).getReturnType() : ((Constructor)var10).getDeclaringClass();
                        var11 = wrapPrimitives(var11);
                        if (!var11.equals(Void.class)) {
                           var2.put((TypeVariable)var8, var11);
                        }
                     }

                     Class[] var15 = var10 instanceof Method ? ((Method)var10).getParameterTypes() : ((Constructor)var10).getParameterTypes();
                     byte var12 = 0;
                     if (var9.length > 0 && var9[0] instanceof TypeVariable && var9.length == var15.length + 1) {
                        Class var13 = var10.getDeclaringClass();
                        var2.put((TypeVariable)var9[0], var13);
                        var12 = 1;
                     }

                     int var16 = 0;
                     if (var9.length < var15.length) {
                        var16 = var15.length - var9.length;
                     }

                     for(int var14 = 0; var14 + var16 < var15.length; ++var14) {
                        if (var9[var14] instanceof TypeVariable) {
                           var2.put((TypeVariable)var9[var14 + var12], wrapPrimitives(var15[var14 + var16]));
                        }
                     }

                     return;
                  }
               }
            }
         }
      }

   }

   private static boolean isDefaultMethod(Method var0) {
      return JAVA_VERSION >= 1.8D && var0.isDefault();
   }

   private static Member getMemberRef(Class var0) {
      Object var1;
      try {
         var1 = GET_CONSTANT_POOL.invoke(var0);
      } catch (Exception var5) {
         return null;
      }

      Member var2 = null;

      for(int var3 = getConstantPoolSize(var1) - 1; var3 >= 0; --var3) {
         Member var4 = getConstantPoolMethodAt(var1, var3);
         if (var4 != null && (!(var4 instanceof Constructor) || !var4.getDeclaringClass().getName().equals("java.lang.invoke.SerializedLambda")) && !var4.getDeclaringClass().isAssignableFrom(var0)) {
            var2 = var4;
            if (!(var4 instanceof Method) || !isAutoBoxingMethod((Method)var4)) {
               break;
            }
         }
      }

      return var2;
   }

   private static boolean isAutoBoxingMethod(Method var0) {
      Class[] var1 = var0.getParameterTypes();
      return var0.getName().equals("valueOf") && var1.length == 1 && var1[0].isPrimitive() && wrapPrimitives(var1[0]).equals(var0.getDeclaringClass());
   }

   private static Class wrapPrimitives(Class var0) {
      return var0.isPrimitive() ? (Class)PRIMITIVE_WRAPPERS.get(var0) : var0;
   }

   private static int getConstantPoolSize(Object var0) {
      try {
         return (Integer)GET_CONSTANT_POOL_SIZE.invoke(var0);
      } catch (Exception var2) {
         return 0;
      }
   }

   private static Member getConstantPoolMethodAt(Object var0, int var1) {
      try {
         return (Member)GET_CONSTANT_POOL_METHOD_AT.invoke(var0, var1);
      } catch (Exception var3) {
         return null;
      }
   }

   static {
      try {
         Unsafe var0 = (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Unsafe run() throws Exception {
               Field var1 = Unsafe.class.getDeclaredField("theUnsafe");
               var1.setAccessible(true);
               return (Unsafe)var1.get((Object)null);
            }

            public Object run() throws Exception {
               return this.run();
            }
         });
         GET_CONSTANT_POOL = Class.class.getDeclaredMethod("getConstantPool");
         String var1 = JAVA_VERSION < 9.0D ? "sun.reflect.ConstantPool" : "jdk.internal.reflect.ConstantPool";
         Class var2 = Class.forName(var1);
         GET_CONSTANT_POOL_SIZE = var2.getDeclaredMethod("getSize");
         GET_CONSTANT_POOL_METHOD_AT = var2.getDeclaredMethod("getMethodAt", Integer.TYPE);
         Field var3 = AccessibleObject.class.getDeclaredField("override");
         long var4 = var0.objectFieldOffset(var3);
         var0.putBoolean(GET_CONSTANT_POOL, var4, true);
         var0.putBoolean(GET_CONSTANT_POOL_SIZE, var4, true);
         var0.putBoolean(GET_CONSTANT_POOL_METHOD_AT, var4, true);
         Object var6 = GET_CONSTANT_POOL.invoke(Object.class);
         GET_CONSTANT_POOL_SIZE.invoke(var6);
         Method[] var7 = Object.class.getDeclaredMethods();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Method var10 = var7[var9];
            OBJECT_METHODS.put(var10.getName(), var10);
         }

         RESOLVES_LAMBDAS = true;
      } catch (Exception var11) {
      }

      HashMap var12 = new HashMap();
      var12.put(Boolean.TYPE, Boolean.class);
      var12.put(Byte.TYPE, Byte.class);
      var12.put(Character.TYPE, Character.class);
      var12.put(Double.TYPE, Double.class);
      var12.put(Float.TYPE, Float.class);
      var12.put(Integer.TYPE, Integer.class);
      var12.put(Long.TYPE, Long.class);
      var12.put(Short.TYPE, Short.class);
      var12.put(Void.TYPE, Void.class);
      PRIMITIVE_WRAPPERS = Collections.unmodifiableMap(var12);
   }

   public static final class Unknown {
      private Unknown() {
      }
   }
}
