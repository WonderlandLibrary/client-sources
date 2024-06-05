package net;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import net.Op;

public final class a5j {
   private static final LoadingCache a = CacheBuilder.newBuilder().weakKeys().maximumSize(512L).build(new Op());

   private static String b(String var0) {
      return "\ud83c\udf38" + var0.toLowerCase().replace("_", "");
   }

   public static Optional a(Class param0, String param1) {
      // $FF: Couldn't be decompiled
   }

   static String a(String var0) {
      return b(var0);
   }

   private static ExecutionException a(ExecutionException var0) {
      return var0;
   }
}
