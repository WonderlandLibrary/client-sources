package cc.novoline.utils.java;

import cc.novoline.utils.java.Checks;
import cc.novoline.utils.java.FilteredArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public final class Helpers {
   private Helpers() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static boolean isEmpty(CharSequence var0) {
      return var0.length() == 0;
   }

   public static boolean containsWhitespace(CharSequence var0) {
      String[] var1 = FilteredArrayList.c();
      if(isEmpty(var0)) {
         return false;
      } else {
         int var2 = 0;
         if(var2 < var0.length()) {
            if(Character.isWhitespace(var0.charAt(var2))) {
               return true;
            }

            ++var2;
         }

         return false;
      }
   }

   public static boolean isBlank(CharSequence var0) {
      if(isEmpty(var0)) {
         return true;
      } else {
         for(int var1 = 0; var1 < var0.length(); ++var1) {
            if(!Character.isWhitespace(var0.charAt(var1))) {
               return false;
            }
         }

         return true;
      }
   }

   public static int countMatches(CharSequence var0, char var1) {
      String[] var2 = FilteredArrayList.c();
      if(isEmpty(var0)) {
         return 0;
      } else {
         int var3 = 0;
         int var4 = 0;
         if(var4 < var0.length()) {
            if(var0.charAt(var4) == var1) {
               ++var3;
            }

            ++var4;
         }

         return var3;
      }
   }

   public static String truncate(String var0, int var1) {
      String[] var2 = FilteredArrayList.c();
      if(var0 == null) {
         return null;
      } else {
         Checks.notNegative(var1, "maxWidth");
         return var0.length() <= var1?var0:(var1 == 0?"":var0.substring(0, var1));
      }
   }

   public static boolean isNumeric(String var0) {
      String[] var1 = FilteredArrayList.c();
      if(isEmpty(var0)) {
         return false;
      } else {
         char[] var2 = var0.toCharArray();
         int var3 = var2.length;
         int var4 = 0;
         if(var4 < var3) {
            char var5 = var2[var4];
            if(!Character.isDigit(var5)) {
               return false;
            }

            ++var4;
         }

         return true;
      }
   }

   public static boolean deepEquals(Collection var0, Collection var1) {
      String[] var2 = FilteredArrayList.c();
      if(var0 == var1) {
         return true;
      } else if(var0 != null && var1 != null && var0.size() == var1.size()) {
         Iterator var3 = var0.iterator();
         Iterator var4 = var1.iterator();
         if(var3.hasNext()) {
            Object var5 = var3.next();
            Object var6 = var4.next();
            if(!Objects.equals(var5, var6)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean deepEqualsUnordered(Collection var0, Collection var1) {
      String[] var2 = FilteredArrayList.c();
      return var0 == var1?true:(var0 != null && var1 != null?var0.size() == var1.size() && var1.containsAll(var0):false);
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
