package net.skidunion;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import net.skidunion.L;
import net.skidunion.ao;
import net.skidunion.o;
import net.skidunion.q;
import net.skidunion.t;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0005"},
   d2 = {"Lnet/skidunion/a3;", "Lnet/skidunion/ao;", "()V", "a", "", "client", "Lnet/skidunion/o;", "args", "Lcom/google/gson/JsonObject;"}
)
public final class a3 extends ao {
   public static final a3 a;
   private static acE[] h;

   public void a(@NotNull o var1, @NotNull JsonObject var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "args");
      a();
      var1.c().a((q)(new t()));
   }

   private a3() {
      super(11, (L)null, 2, (DefaultConstructorMarker)null);
   }

   static {
      b(new acE[3]);
      a3 var7 = new a3();
      a = var7;
   }

   public static void b(acE[] var0) {
      h = var0;
   }

   public static acE[] a() {
      return h;
   }
}
