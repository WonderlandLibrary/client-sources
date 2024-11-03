package org.jsoup.helper;

import javax.annotation.Nullable;

public final class Validate {
   private Validate() {
   }

   public static void notNull(@Nullable Object obj) {
      if (obj == null) {
         throw new ValidationException("Object must not be null");
      }
   }

   public static void notNullParam(@Nullable Object obj, String param) {
      if (obj == null) {
         throw new ValidationException(String.format("The parameter '%s' must not be null.", param));
      }
   }

   public static void notNull(@Nullable Object obj, String msg) {
      if (obj == null) {
         throw new ValidationException(msg);
      }
   }

   public static Object ensureNotNull(@Nullable Object obj) {
      if (obj == null) {
         throw new ValidationException("Object must not be null");
      } else {
         return obj;
      }
   }

   public static Object ensureNotNull(@Nullable Object obj, String msg, Object... args) {
      if (obj == null) {
         throw new ValidationException(String.format(msg, args));
      } else {
         return obj;
      }
   }

   public static void isTrue(boolean val) {
      if (!val) {
         throw new ValidationException("Must be true");
      }
   }

   public static void isTrue(boolean val, String msg) {
      if (!val) {
         throw new ValidationException(msg);
      }
   }

   public static void isFalse(boolean val) {
      if (val) {
         throw new ValidationException("Must be false");
      }
   }

   public static void isFalse(boolean val, String msg) {
      if (val) {
         throw new ValidationException(msg);
      }
   }

   public static void noNullElements(Object[] objects) {
      noNullElements(objects, "Array must not contain any null objects");
   }

   public static void noNullElements(Object[] objects, String msg) {
      for (Object obj : objects) {
         if (obj == null) {
            throw new ValidationException(msg);
         }
      }
   }

   public static void notEmpty(@Nullable String string) {
      if (string == null || string.length() == 0) {
         throw new ValidationException("String must not be empty");
      }
   }

   public static void notEmptyParam(@Nullable String string, String param) {
      if (string == null || string.length() == 0) {
         throw new ValidationException(String.format("The '%s' parameter must not be empty.", param));
      }
   }

   public static void notEmpty(@Nullable String string, String msg) {
      if (string == null || string.length() == 0) {
         throw new ValidationException(msg);
      }
   }

   public static void wtf(String msg) {
      throw new IllegalStateException(msg);
   }

   public static void fail(String msg) {
      throw new ValidationException(msg);
   }
}
