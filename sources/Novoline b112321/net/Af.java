package net;

import com.google.common.cache.LoadingCache;

public class Af {
   public static Object a(LoadingCache var0, Object var1) {
      return var0.getUnchecked(var1);
   }

   public static Object b(LoadingCache var0, Object var1) {
      return var0.get(var1);
   }
}
