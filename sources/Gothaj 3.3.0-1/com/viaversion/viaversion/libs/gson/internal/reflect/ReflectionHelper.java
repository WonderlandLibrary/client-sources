package com.viaversion.viaversion.libs.gson.internal.reflect;

import com.viaversion.viaversion.libs.gson.JsonIOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper {
   private static final ReflectionHelper.RecordHelper RECORD_HELPER;

   private ReflectionHelper() {
   }

   public static void makeAccessible(AccessibleObject object) throws JsonIOException {
      try {
         object.setAccessible(true);
      } catch (Exception var3) {
         String description = getAccessibleObjectDescription(object, false);
         throw new JsonIOException(
            "Failed making " + description + " accessible; either increase its visibility or write a custom TypeAdapter for its declaring type.", var3
         );
      }
   }

   public static String getAccessibleObjectDescription(AccessibleObject object, boolean uppercaseFirstLetter) {
      String description;
      if (object instanceof Field) {
         description = "field '" + fieldToString((Field)object) + "'";
      } else if (object instanceof Method) {
         Method method = (Method)object;
         StringBuilder methodSignatureBuilder = new StringBuilder(method.getName());
         appendExecutableParameters(method, methodSignatureBuilder);
         String methodSignature = methodSignatureBuilder.toString();
         description = "method '" + method.getDeclaringClass().getName() + "#" + methodSignature + "'";
      } else if (object instanceof Constructor) {
         description = "constructor '" + constructorToString((Constructor<?>)object) + "'";
      } else {
         description = "<unknown AccessibleObject> " + object.toString();
      }

      if (uppercaseFirstLetter && Character.isLowerCase(description.charAt(0))) {
         description = Character.toUpperCase(description.charAt(0)) + description.substring(1);
      }

      return description;
   }

   public static String fieldToString(Field field) {
      return field.getDeclaringClass().getName() + "#" + field.getName();
   }

   public static String constructorToString(Constructor<?> constructor) {
      StringBuilder stringBuilder = new StringBuilder(constructor.getDeclaringClass().getName());
      appendExecutableParameters(constructor, stringBuilder);
      return stringBuilder.toString();
   }

   private static void appendExecutableParameters(AccessibleObject executable, StringBuilder stringBuilder) {
      stringBuilder.append('(');
      Class<?>[] parameters = executable instanceof Method ? ((Method)executable).getParameterTypes() : ((Constructor)executable).getParameterTypes();

      for (int i = 0; i < parameters.length; i++) {
         if (i > 0) {
            stringBuilder.append(", ");
         }

         stringBuilder.append(parameters[i].getSimpleName());
      }

      stringBuilder.append(')');
   }

   public static String tryMakeAccessible(Constructor<?> constructor) {
      try {
         constructor.setAccessible(true);
         return null;
      } catch (Exception var2) {
         return "Failed making constructor '"
            + constructorToString(constructor)
            + "' accessible; either increase its visibility or write a custom InstanceCreator or TypeAdapter for its declaring type: "
            + var2.getMessage();
      }
   }

   public static boolean isRecord(Class<?> raw) {
      return RECORD_HELPER.isRecord(raw);
   }

   public static String[] getRecordComponentNames(Class<?> raw) {
      return RECORD_HELPER.getRecordComponentNames(raw);
   }

   public static Method getAccessor(Class<?> raw, Field field) {
      return RECORD_HELPER.getAccessor(raw, field);
   }

   public static <T> Constructor<T> getCanonicalRecordConstructor(Class<T> raw) {
      return RECORD_HELPER.getCanonicalRecordConstructor(raw);
   }

   public static RuntimeException createExceptionForUnexpectedIllegalAccess(IllegalAccessException exception) {
      throw new RuntimeException(
         "Unexpected IllegalAccessException occurred (Gson 2.10.1). Certain ReflectionAccessFilter features require Java >= 9 to work correctly. If you are not using ReflectionAccessFilter, report this to the Gson maintainers.",
         exception
      );
   }

   private static RuntimeException createExceptionForRecordReflectionException(ReflectiveOperationException exception) {
      throw new RuntimeException(
         "Unexpected ReflectiveOperationException occurred (Gson 2.10.1). To support Java records, reflection is utilized to read out information about records. All these invocations happens after it is established that records exist in the JVM. This exception is unexpected behavior.",
         exception
      );
   }

   static {
      ReflectionHelper.RecordHelper instance;
      try {
         instance = new ReflectionHelper.RecordSupportedHelper();
      } catch (NoSuchMethodException var2) {
         instance = new ReflectionHelper.RecordNotSupportedHelper();
      }

      RECORD_HELPER = instance;
   }

   private abstract static class RecordHelper {
      private RecordHelper() {
      }

      abstract boolean isRecord(Class<?> var1);

      abstract String[] getRecordComponentNames(Class<?> var1);

      abstract <T> Constructor<T> getCanonicalRecordConstructor(Class<T> var1);

      public abstract Method getAccessor(Class<?> var1, Field var2);
   }

   private static class RecordNotSupportedHelper extends ReflectionHelper.RecordHelper {
      private RecordNotSupportedHelper() {
      }

      @Override
      boolean isRecord(Class<?> clazz) {
         return false;
      }

      @Override
      String[] getRecordComponentNames(Class<?> clazz) {
         throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
      }

      @Override
      <T> Constructor<T> getCanonicalRecordConstructor(Class<T> raw) {
         throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
      }

      @Override
      public Method getAccessor(Class<?> raw, Field field) {
         throw new UnsupportedOperationException("Records are not supported on this JVM, this method should not be called");
      }
   }

   private static class RecordSupportedHelper extends ReflectionHelper.RecordHelper {
      private final Method isRecord = Class.class.getMethod("isRecord");
      private final Method getRecordComponents = Class.class.getMethod("getRecordComponents");
      private final Method getName;
      private final Method getType;

      private RecordSupportedHelper() throws NoSuchMethodException {
         Class<?> classRecordComponent = this.getRecordComponents.getReturnType().getComponentType();
         this.getName = classRecordComponent.getMethod("getName");
         this.getType = classRecordComponent.getMethod("getType");
      }

      @Override
      boolean isRecord(Class<?> raw) {
         try {
            return (Boolean)this.isRecord.invoke(raw);
         } catch (ReflectiveOperationException var3) {
            throw ReflectionHelper.createExceptionForRecordReflectionException(var3);
         }
      }

      @Override
      String[] getRecordComponentNames(Class<?> raw) {
         try {
            Object[] recordComponents = (Object[])this.getRecordComponents.invoke(raw);
            String[] componentNames = new String[recordComponents.length];

            for (int i = 0; i < recordComponents.length; i++) {
               componentNames[i] = (String)this.getName.invoke(recordComponents[i]);
            }

            return componentNames;
         } catch (ReflectiveOperationException var5) {
            throw ReflectionHelper.createExceptionForRecordReflectionException(var5);
         }
      }

      @Override
      public <T> Constructor<T> getCanonicalRecordConstructor(Class<T> raw) {
         try {
            Object[] recordComponents = (Object[])this.getRecordComponents.invoke(raw);
            Class<?>[] recordComponentTypes = new Class[recordComponents.length];

            for (int i = 0; i < recordComponents.length; i++) {
               recordComponentTypes[i] = (Class<?>)this.getType.invoke(recordComponents[i]);
            }

            return raw.getDeclaredConstructor(recordComponentTypes);
         } catch (ReflectiveOperationException var5) {
            throw ReflectionHelper.createExceptionForRecordReflectionException(var5);
         }
      }

      @Override
      public Method getAccessor(Class<?> raw, Field field) {
         try {
            return raw.getMethod(field.getName());
         } catch (ReflectiveOperationException var4) {
            throw ReflectionHelper.createExceptionForRecordReflectionException(var4);
         }
      }
   }
}
