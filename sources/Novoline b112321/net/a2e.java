package net;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import net.Ql;
import net.X9;
import net.a0r;
import net.aPq;
import net.uw;

public final class a2e implements a0r {
   private static final a2e b = new a2e();
   private final LoadingCache a = CacheBuilder.newBuilder().weakKeys().maximumSize(500L).build(new aPq(this));

   public static a0r a() {
      return b;
   }

   public uw a(Class var1) throws X9 {
      uw.b();
      Ql.a((Object)var1, "type");

      try {
         return (uw)this.a.get(var1);
      } catch (ExecutionException var4) {
         if(var4.getCause() instanceof X9) {
            throw (X9)var4.getCause();
         } else {
            throw new X9(var4);
         }
      }
   }

   public String toString() {
      return "PropertyMapperFactory{}";
   }

   private static ExecutionException a(ExecutionException var0) {
      return var0;
   }
}
