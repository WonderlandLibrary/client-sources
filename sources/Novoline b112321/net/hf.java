package net;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import java.util.Collection;

public class hf {
   public static Collection a(Collection var0, Predicate var1) {
      return Collections2.filter(var0, var1);
   }

   public static Collection a(Collection var0, Function var1) {
      return Collections2.transform(var0, var1);
   }
}
