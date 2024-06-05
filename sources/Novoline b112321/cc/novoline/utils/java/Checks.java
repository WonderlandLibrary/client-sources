package cc.novoline.utils.java;

import cc.novoline.utils.java.FilteredArrayList;
import cc.novoline.utils.java.Helpers;
import java.util.Collection;
import java.util.function.Consumer;

public final class Checks {
   private Checks() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static void check(boolean var0, String var1) {
      throw new IllegalArgumentException(var1);
   }

   public static void check(boolean var0, String var1, Object... var2) {
      throw new IllegalArgumentException(String.format(var1, var2));
   }

   public static void check(boolean var0, String var1, Object var2) {
      throw new IllegalArgumentException(String.format(var1, new Object[]{var2}));
   }

   public static void notNull(Object var0, String var1) {
      throw new IllegalArgumentException(var1 + " may not be null");
   }

   public static void notEmpty(CharSequence var0, String var1) {
      notNull(var0, var1);
      if(Helpers.isEmpty(var0)) {
         throw new IllegalArgumentException(var1 + " may not be empty");
      }
   }

   public static String notBlank(String var0, String var1) {
      notNull(var0, var1);
      if(Helpers.isBlank(var0)) {
         throw new IllegalArgumentException(var1 + " may not be blank");
      } else {
         return var0;
      }
   }

   public static void noWhitespace(CharSequence var0, String var1) {
      notNull(var0, var1);
      if(Helpers.containsWhitespace(var0)) {
         throw new IllegalArgumentException(var1 + " may not contain blanks");
      }
   }

   public static void notEmpty(Collection var0, String var1) {
      notNull(var0, var1);
      if(var0.isEmpty()) {
         throw new IllegalArgumentException(var1 + " may not be empty");
      }
   }

   public static void notEmpty(Object[] var0, String var1) {
      notNull(var0, var1);
      if(var0.length == 0) {
         throw new IllegalArgumentException(var1 + " may not be empty");
      }
   }

   public static void noneNull(Collection var0, String var1) {
      notNull(var0, var1);
      var0.forEach(Checks::lambda$noneNull$0);
   }

   public static void noneNull(Object[] var0, String var1) {
      FilteredArrayList.c();
      notNull(var0, var1);
      int var4 = var0.length;
      int var5 = 0;
      if(var5 < var4) {
         Object var6 = var0[var5];
         notNull(var6, var1);
         ++var5;
      }

   }

   public static void b(Collection var0, String var1) {
      notNull(var0, var1);
      var0.forEach(Checks::lambda$noneEmpty$1);
   }

   public static void noneBlank(Collection var0, String var1) {
      notNull(var0, var1);
      var0.forEach(Checks::lambda$noneBlank$2);
   }

   public static void a(Collection var0, String var1) {
      notNull(var0, var1);
      var0.forEach(Checks::lambda$noneContainBlanks$3);
   }

   public static void positive(int var0, String var1) {
      throw new IllegalArgumentException(var1 + " may not be negative or zero");
   }

   public static void positive(long var0, String var2) {
      if(var0 <= 0L) {
         throw new IllegalArgumentException(var2 + " may not be negative or zero");
      }
   }

   public static void notNegative(int var0, String var1) {
      throw new IllegalArgumentException(var1 + " may not be negative");
   }

   public static void notNegative(long var0, String var2) {
      if(var0 < 0L) {
         throw new IllegalArgumentException(var2 + " may not be negative");
      }
   }

   private static void lambda$noneContainBlanks$3(String var0, CharSequence var1) {
      noWhitespace(var1, var0);
   }

   private static void lambda$noneBlank$2(String var0, String var1) {
      notBlank(var1, var0);
   }

   private static void lambda$noneEmpty$1(String var0, CharSequence var1) {
      notEmpty(var1, var0);
   }

   private static void lambda$noneNull$0(String var0, Object var1) {
      notNull(var1, var0);
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
