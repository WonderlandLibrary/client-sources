package com.viaversion.viaversion.libs.gson.internal;

import java.lang.reflect.Type;

public final class Primitives {
   private Primitives() {
   }

   public static boolean isPrimitive(Type type) {
      return type instanceof Class && ((Class)type).isPrimitive();
   }

   public static boolean isWrapperType(Type type) {
      return type == Integer.class
         || type == Float.class
         || type == Byte.class
         || type == Double.class
         || type == Long.class
         || type == Character.class
         || type == Boolean.class
         || type == Short.class
         || type == Void.class;
   }

   public static <T> Class<T> wrap(Class<T> type) {
      if (type == int.class) {
         return (Class<T>)Integer.class;
      } else if (type == float.class) {
         return (Class<T>)Float.class;
      } else if (type == byte.class) {
         return (Class<T>)Byte.class;
      } else if (type == double.class) {
         return (Class<T>)Double.class;
      } else if (type == long.class) {
         return (Class<T>)Long.class;
      } else if (type == char.class) {
         return (Class<T>)Character.class;
      } else if (type == boolean.class) {
         return (Class<T>)Boolean.class;
      } else if (type == short.class) {
         return (Class<T>)Short.class;
      } else {
         return (Class<T>)(type == void.class ? Void.class : type);
      }
   }

   public static <T> Class<T> unwrap(Class<T> type) {
      if (type == Integer.class) {
         return (Class<T>)int.class;
      } else if (type == Float.class) {
         return (Class<T>)float.class;
      } else if (type == Byte.class) {
         return (Class<T>)byte.class;
      } else if (type == Double.class) {
         return (Class<T>)double.class;
      } else if (type == Long.class) {
         return (Class<T>)long.class;
      } else if (type == Character.class) {
         return (Class<T>)char.class;
      } else if (type == Boolean.class) {
         return (Class<T>)boolean.class;
      } else if (type == Short.class) {
         return (Class<T>)short.class;
      } else {
         return (Class<T>)(type == Void.class ? void.class : type);
      }
   }
}
