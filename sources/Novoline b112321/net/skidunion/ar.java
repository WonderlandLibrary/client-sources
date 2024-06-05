package net.skidunion;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import net.skidunion.L;
import net.skidunion.ao;
import net.skidunion.b;
import net.skidunion.o;
import net.skidunion.q;
import net.skidunion.x;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u0005"},
   d2 = {"Lnet/skidunion/ar;", "Lnet/skidunion/ao;", "()V", "a", "", "client", "Lnet/skidunion/o;", "args", "Lcom/google/gson/JsonObject;"}
)
public final class ar extends ao {
   public static final ar h;
   private static String a;

   public void a(@NotNull o var1, @NotNull JsonObject var2) {
      a();
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "args");
      Gson var10000 = this.f();
      JsonElement var10001 = var2.get("configs");
      Intrinsics.checkExpressionValueIsNotNull(var10001, "args[\"configs\"]");
      var10000 = (Gson)var10000.fromJson((JsonElement)var10001.getAsJsonArray(), b[].class);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "gson.fromJson(args[\"conf…onfigEntity>::class.java)");
      List var4 = ArraysKt.toMutableList((Object[])var10000);
      var1.c().a((q)(new x(var4)));
      if(acE.b() == null) {
         a("wUzEs");
      }

   }

   private ar() {
      super(13, (L)null, 2, (DefaultConstructorMarker)null);
   }

   static {
      a((String)null);
      ar var7 = new ar();
      h = var7;
   }

   public static void a(String var0) {
      a = var0;
   }

   public static String a() {
      return a;
   }
}
