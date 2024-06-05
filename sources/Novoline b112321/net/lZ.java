package net;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import net.af_;

final class lZ implements Predicate {
   private static final Method a;
   private final TypeToken b;

   lZ(TypeToken var1) {
      this.b = var1;
   }

   public boolean a(TypeToken var1) {
      try {
         return ((Boolean)a.invoke(this.b, new Object[]{var1})).booleanValue();
      } catch (Exception var3) {
         var3.printStackTrace();
         return false;
      }
   }

   static {
      Method var8;
      try {
         var8 = af_.b(TypeToken.class, "isSupertypeOf", new Class[]{TypeToken.class});
      } catch (NoSuchMethodException var12) {
         try {
            var8 = af_.b(TypeToken.class, "isAssignableFrom", new Class[]{TypeToken.class});
         } catch (NoSuchMethodException var11) {
            throw new RuntimeException("Unable to get TypeToken#isSupertypeOf or TypeToken#isAssignableFrom method");
         }
      }

      a = var8;
   }
}
