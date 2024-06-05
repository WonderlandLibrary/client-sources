package net.skidunion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.I;
import net.skidunion.L;
import net.skidunion.N;
import net.skidunion.aE;
import net.skidunion.aN;
import net.skidunion.aU;
import net.skidunion.an;
import net.skidunion.ao;
import net.skidunion.aw;
import net.skidunion.c;
import net.skidunion.o;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0005"},
   d2 = {"Lnet/skidunion/ap;", "Lnet/skidunion/ao;", "()V", "a", "", "client", "Lnet/skidunion/o;", "args", "Lcom/google/gson/JsonObject;"}
)
public final class ap extends ao {
   public static final ap a;

   public void a(@NotNull o var1, @NotNull JsonObject var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      aw.a();
      Intrinsics.checkParameterIsNotNull(var2, "args");
      JsonElement var10001 = var2.get("key");
      Intrinsics.checkExpressionValueIsNotNull(var10001, "args[\"key\"]");
      String var4 = var10001.getAsString();
      String var5 = N.b.c();
      aU var10002 = aN.c;
      Intrinsics.checkExpressionValueIsNotNull(var4, "publicKey");
      aN var6 = new aN(var10002.a(var4));
      var1.a((an)(new aE(var6.a(var5), var6.a(var1.e()))));
      var1.a(I.LOGIN_SENT);
      var1.a((c)(new net.skidunion.a(var5)));
   }

   private ap() {
      super(1, (L)null, 2, (DefaultConstructorMarker)null);
   }

   static {
      ap var7 = new ap();
      a = var7;
   }
}
