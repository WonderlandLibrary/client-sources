package net.skidunion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.a3;
import net.skidunion.a8;
import net.skidunion.aB;
import net.skidunion.aF;
import net.skidunion.aS;
import net.skidunion.am;
import net.skidunion.ao;
import net.skidunion.ap;
import net.skidunion.ar;
import net.skidunion.as;
import net.skidunion.au;
import net.skidunion.av;
import net.skidunion.aw;
import net.skidunion.o;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
   d2 = {"Lnet/skidunion/a1;", "", "()V", "b", "Ljava/util/HashMap;", "", "Lnet/skidunion/ao;", "Lkotlin/collections/HashMap;", "a", "", "client", "Lnet/skidunion/o;", "message", ""}
)
public final class a1 {
   private static final HashMap b = new HashMap();
   public static final a1 a;

   public final void a(@NotNull o var1, @NotNull String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      ao.c();
      Intrinsics.checkParameterIsNotNull(var2, "message");
      JsonElement var10001 = (new JsonParser()).parse(var2);
      Intrinsics.checkExpressionValueIsNotNull(var10001, "JsonParser().parse(message)");
      JsonObject var4 = var10001.getAsJsonObject();
      JsonElement var10000 = var4.get("opcode");
      Intrinsics.checkExpressionValueIsNotNull(var10000, "messageJson[\"opcode\"]");
      int var5 = var10000.getAsInt();
      ao var11 = (ao)b.get(Integer.valueOf(var5));
      if(var11 != null) {
         ao var6 = var11;
         boolean var7 = false;
         boolean var8 = false;
         boolean var10 = false;
         if(var4.has("ts")) {
            var10001 = var4.get("ts");
            Intrinsics.checkExpressionValueIsNotNull(var10001, "messageJson[\"ts\"]");
            var6.a(Long.valueOf(var10001.getAsLong()));
         }

         Intrinsics.checkExpressionValueIsNotNull(var4, "messageJson");
         var6.a(var1, var4);
      }

   }

   static {
      a1 var7 = new a1();
      a = var7;
      ((Map)b).put(Integer.valueOf(1), ap.a);
      ((Map)b).put(Integer.valueOf(2), aw.a);
      ((Map)b).put(Integer.valueOf(3), au.a);
      ((Map)b).put(Integer.valueOf(4), aF.a);
      ((Map)b).put(Integer.valueOf(6), aS.a);
      ((Map)b).put(Integer.valueOf(7), a8.a);
      ((Map)b).put(Integer.valueOf(8), am.h);
      ((Map)b).put(Integer.valueOf(9), av.a);
      ((Map)b).put(Integer.valueOf(10), as.a);
      ((Map)b).put(Integer.valueOf(11), a3.a);
      ((Map)b).put(Integer.valueOf(12), aB.a);
      ((Map)b).put(Integer.valueOf(13), ar.h);
   }
}
