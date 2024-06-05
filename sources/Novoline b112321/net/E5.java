package net;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;

public class E5 {
   private static int[] b;

   public static TypeToken a(Class var0) {
      return TypeToken.of(var0);
   }

   public static Type c(TypeToken var0) {
      return var0.getType();
   }

   public static TypeToken a(TypeToken var0, Type var1) {
      return var0.resolveType(var1);
   }

   public static Class b(TypeToken var0) {
      return var0.getRawType();
   }

   public static TypeToken a(TypeToken var0) {
      return var0.wrap();
   }

   public static TypeToken a(Type var0) {
      return TypeToken.of(var0);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[3]);
      }

   }
}
