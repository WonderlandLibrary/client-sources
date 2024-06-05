package net.skidunion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.L;
import net.skidunion.Y;
import net.skidunion.ao;
import net.skidunion.o;
import net.skidunion.q;
import net.skidunion.u;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0005"},
   d2 = {"Lnet/skidunion/am;", "Lnet/skidunion/ao;", "()V", "a", "", "client", "Lnet/skidunion/o;", "args", "Lcom/google/gson/JsonObject;"}
)
public final class am extends ao {
   public static final am h;
   private static String[] a;

   public void a(@NotNull o var1, @NotNull JsonObject var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "args");
      Y var10000 = var1.c();
      JsonElement var10003 = var2.get("message");
      Intrinsics.checkExpressionValueIsNotNull(var10003, "args[\"message\"]");
      String var3 = var10003.getAsString();
      Intrinsics.checkExpressionValueIsNotNull(var3, "args[\"message\"].asString");
      JsonElement var10004 = var2.get("error");
      Intrinsics.checkExpressionValueIsNotNull(var10004, "args[\"error\"]");
      var10000.a((q)(new u(var3, var10004.getAsBoolean())));
   }

   private am() {
      super(8, (L)null, 2, (DefaultConstructorMarker)null);
   }

   static {
      b(new String[1]);
      am var7 = new am();
      h = var7;
   }

   public static void b(String[] var0) {
      a = var0;
   }

   public static String[] a() {
      return a;
   }
}
