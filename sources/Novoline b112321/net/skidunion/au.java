package net.skidunion;

import com.google.gson.JsonObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.C;
import net.skidunion.E;
import net.skidunion.L;
import net.skidunion.T;
import net.skidunion.aL;
import net.skidunion.am;
import net.skidunion.ao;
import net.skidunion.m;
import net.skidunion.o;
import net.skidunion.q;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0005"},
   d2 = {"Lnet/skidunion/au;", "Lnet/skidunion/ao;", "()V", "a", "", "client", "Lnet/skidunion/o;", "args", "Lcom/google/gson/JsonObject;"}
)
public final class au extends ao {
   public static final au a;

   public void a(@NotNull o var1, @NotNull JsonObject var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "args");
      am.a();
      m var4 = (m)this.f().fromJson(var2.get("data"), m.class);
      boolean var5 = var1.a().b().containsKey(var4.c());
      Map var10001 = (Map)var1.a().b();
      String var10002 = var4.c();
      Intrinsics.checkExpressionValueIsNotNull(var4, "data");
      var10001.put(var10002, var4);
      ConcurrentHashMap var6 = var1.a().c();
      boolean var7 = false;
      boolean var8 = false;
      boolean var10 = false;
      T var10000 = var4.d();
      String var11 = var10000 != null?var10000.e():null;
      var6.entrySet().removeIf((Predicate)(new aL(var11)));
      var1.c().a((q)(new C(var4)));
      var1.c().a((q)(new E(var4)));
   }

   private au() {
      super(3, (L)null, 2, (DefaultConstructorMarker)null);
   }

   static {
      au var7 = new au();
      a = var7;
   }
}
