package net.skidunion;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.T;
import net.skidunion.m;
import net.skidunion.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0015\u001a\u00020\u0007R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u0007X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\n¨\u0006\u0016"},
   d2 = {"Lnet/skidunion/a4;", "", "b", "Lnet/skidunion/o;", "(Lnet/skidunion/o;)V", "a", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lnet/skidunion/m;", "c", "()Ljava/util/concurrent/ConcurrentHashMap;", "d", "()Lnet/skidunion/o;", "d", "a", "()Ljava/lang/String;", "a", "(Ljava/lang/String;)V", "c", "b", "b", "nickname", "client"}
)
public final class a4 {
   @NotNull
   public String d;
   @NotNull
   private final ConcurrentHashMap c;
   @NotNull
   private final ConcurrentHashMap a;
   @NotNull
   private final o b;

   @NotNull
   public final String a() {
      boolean var1 = o.k();
      String var10000 = this.d;
      if(this.d == null) {
         Intrinsics.throwUninitializedPropertyAccessException("selfUsername");
      }

      return var10000;
   }

   public final void a(@NotNull String var1) {
      Intrinsics.checkParameterIsNotNull(var1, "<set-?>");
      this.d = var1;
   }

   @NotNull
   public final ConcurrentHashMap b() {
      return this.c;
   }

   @NotNull
   public final ConcurrentHashMap c() {
      return this.a;
   }

   @Nullable
   public final m b(@NotNull String var1) {
      o.k();
      Intrinsics.checkParameterIsNotNull(var1, "nickname");
      m var10000 = (m)this.a.get(var1);
      if(var10000 == null) {
         Collection var13 = this.c.values();
         Intrinsics.checkExpressionValueIsNotNull(var13, "users.values");
         Iterable var3 = (Iterable)var13;
         boolean var4 = false;

         label120: {
            for(Object var6 : var3) {
               m var7 = (m)var6;
               boolean var8 = false;
               String var14 = this.b.l().a(var1);
               T var10001 = var7.d();
               if(Intrinsics.areEqual(var14, var10001 != null?var10001.e():null)) {
                  var13 = (Collection)var6;
                  break label120;
               }
            }

            var13 = null;
         }

         m var16 = (m)var13;
         if(var16 != null) {
            m var9 = var16;
            var4 = false;
            boolean var11 = false;
            boolean var12 = false;
            Map var17 = (Map)this.a;
            Intrinsics.checkExpressionValueIsNotNull(var9, "it");
            var17.put(var1, var9);
            var10000 = var9;
         } else {
            var10000 = null;
         }
      }

      return var10000;
   }

   @NotNull
   public final o d() {
      return this.b;
   }

   public a4(@NotNull o var1) {
      Intrinsics.checkParameterIsNotNull(var1, "ircClient");
      super();
      this.b = var1;
      this.c = new ConcurrentHashMap();
      this.a = new ConcurrentHashMap();
   }
}
