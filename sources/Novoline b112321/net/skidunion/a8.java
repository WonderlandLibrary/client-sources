package net.skidunion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.F;
import net.skidunion.L;
import net.skidunion.Y;
import net.skidunion.ao;
import net.skidunion.o;
import net.skidunion.q;
import net.skidunion.r;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0005"},
   d2 = {"Lnet/skidunion/a8;", "Lnet/skidunion/ao;", "()V", "a", "", "client", "Lnet/skidunion/o;", "args", "Lcom/google/gson/JsonObject;"}
)
public final class a8 extends ao {
   public static final a8 a;

   public void a(@NotNull o var1, @NotNull JsonObject var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "args");
      JsonElement var10000 = var2.get("from");
      Intrinsics.checkExpressionValueIsNotNull(var10000, "args[\"from\"]");
      String var3 = var10000.getAsString();
      var10000 = var2.get("message");
      Intrinsics.checkExpressionValueIsNotNull(var10000, "args[\"message\"]");
      String var4 = var10000.getAsString();
      Y var6 = var1.c();
      Intrinsics.checkExpressionValueIsNotNull(var3, "from");
      Intrinsics.checkExpressionValueIsNotNull(var4, "message");
      var6.a((q)(new r(new F(var3, var4, var1))));
   }

   private a8() {
      super(7, (L)null, 2, (DefaultConstructorMarker)null);
   }

   static {
      a8 var7 = new a8();
      a = var7;
   }
}
