package net;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

public class g_ {
   public static Object a(ImmutableTable var0, Object var1, Object var2) {
      return var0.get(var1, var2);
   }

   public static ImmutableTable a(Table var0) {
      return ImmutableTable.copyOf(var0);
   }
}
