package net.skidunion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.L;
import net.skidunion.Y;
import net.skidunion.aQ;
import net.skidunion.ao;
import net.skidunion.ar;
import net.skidunion.o;
import net.skidunion.q;
import net.skidunion.v;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0005"},
   d2 = {"Lnet/skidunion/as;", "Lnet/skidunion/ao;", "()V", "a", "", "client", "Lnet/skidunion/o;", "args", "Lcom/google/gson/JsonObject;"}
)
public final class as extends ao {
   public static final as a;

   public void a(@NotNull o var1, @NotNull JsonObject var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "args");
      ar.a();
      JsonElement var10001 = var2.get("name");
      Intrinsics.checkExpressionValueIsNotNull(var10001, "args[\"name\"]");
      String var4 = var10001.getAsString();
      var10001 = var2.get("data");
      Intrinsics.checkExpressionValueIsNotNull(var10001, "args[\"data\"]");
      String var7 = var10001.getAsString();
      Intrinsics.checkExpressionValueIsNotNull(var7, "args[\"data\"].asString");
      String var5 = aQ.a(var7);
      Y var8 = var1.c();
      Intrinsics.checkExpressionValueIsNotNull(var4, "name");
      var8.a((q)(new v(var4, var5)));
   }

   private as() {
      super(9, (L)null, 2, (DefaultConstructorMarker)null);
   }

   static {
      as var7 = new as();
      a = var7;
   }
}
