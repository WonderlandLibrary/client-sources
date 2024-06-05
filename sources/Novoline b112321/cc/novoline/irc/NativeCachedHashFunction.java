package cc.novoline.irc;

import cc.novoline.irc.NativeCachedHashFunction$NativeHashFunction;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.function.Function;
import net.acE;
import net.skidunion.d;
import org.jetbrains.annotations.NotNull;

public class NativeCachedHashFunction implements d {
   private final NativeCachedHashFunction$NativeHashFunction hashFunction;
   private final Object2ObjectArrayMap cache;
   private static int b;

   public NativeCachedHashFunction() {
      b();
      super();
      this.hashFunction = new NativeCachedHashFunction$NativeHashFunction();
      this.cache = new Object2ObjectArrayMap();
   }

   @NotNull
   public String a(@NotNull String var1) {
      int var2 = b();
      String var10000 = (String)this.cache.computeIfAbsent(var1, this::lambda$hash$0);
      if(acE.b() == null) {
         ++var2;
         b(var2);
      }

      return var10000;
   }

   private String lambda$hash$0(String var1, String var2) {
      String var3 = this.hashFunction.a(var2);
      this.cache.put(var1, var2);
      return var3;
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 91;
   }

   static {
      if(b() == 0) {
         b(14);
      }

   }
}
