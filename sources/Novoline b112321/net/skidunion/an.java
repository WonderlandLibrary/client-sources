package net.skidunion;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import net.skidunion.L;
import net.skidunion.ao;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B5\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001c\u0010\u0005\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u00070\u0006\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fB-\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001c\u0010\u0005\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u00070\u0006¢\u0006\u0002\u0010\rB-\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\u000fB%\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007¢\u0006\u0002\u0010\u0010B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\u0011J\b\u0010\u0013\u001a\u00020\bH\u0016R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lnet/skidunion/an;", "Lnet/skidunion/ao;", "opcode", "", "(I)V", "h", "", "Lkotlin/Pair;", "", "", "packetType", "Lnet/skidunion/L;", "(I[Lkotlin/Pair;Lnet/skidunion/L;)V", "(I[Lkotlin/Pair;)V", "arg", "(ILkotlin/Pair;Lnet/skidunion/L;)V", "(ILkotlin/Pair;)V", "(ILnet/skidunion/L;)V", "", "toString", "client"}
)
public class an extends ao {
   private Map h;
   private static int[] a;

   @NotNull
   public String toString() {
      ao.c();
      JsonObject var2 = new JsonObject();
      Long var10000 = this.d();
      if(var10000 != null) {
         Long var3 = var10000;
         boolean var4 = false;
         boolean var5 = false;
         long var6 = ((Number)var3).longValue();
         boolean var8 = false;
         var2.addProperty("ts", (Number)Long.valueOf(var6));
      }

      var2.addProperty("opcode", (Number)Integer.valueOf(this.b()));
      Map var20 = this.h;
      if(this.h != null) {
         Map var15 = var20;
         boolean var16 = false;
         boolean var17 = false;
         Iterator var7 = var15.entrySet().iterator();
         if(var7.hasNext()) {
            Entry var18 = (Entry)var7.next();
            boolean var10 = false;
            boolean var12 = false;
            String var13 = (String)var18.getKey();
            var12 = false;
            Object var14 = var18.getValue();
            var2.add(var13, this.f().toJsonTree(var14));
         }
      }

      String var21 = this.f().toJson((JsonElement)var2);
      Intrinsics.checkExpressionValueIsNotNull(var21, "gson.toJson(jsonObject)");
      if(acE.b() == null) {
         ao.b("VocfEc");
      }

      return var21;
   }

   public an(int var1, @NotNull L var2) {
      Intrinsics.checkParameterIsNotNull(var2, "packetType");
      super(var1, var2);
   }

   public an(int var1) {
      this(var1, L.PLAY);
   }

   public an(int var1, @NotNull Pair[] var2, @NotNull L var3) {
      Intrinsics.checkParameterIsNotNull(var2, "args");
      Intrinsics.checkParameterIsNotNull(var3, "packetType");
      this(var1, var3);
      this.h = MapsKt.mapOf((Pair[])Arrays.copyOf(var2, var2.length));
   }

   public an(int var1, @NotNull Pair[] var2) {
      Intrinsics.checkParameterIsNotNull(var2, "args");
      this(var1, var2, L.PLAY);
   }

   public an(int var1, @NotNull Pair var2, @NotNull L var3) {
      Intrinsics.checkParameterIsNotNull(var2, "arg");
      Intrinsics.checkParameterIsNotNull(var3, "packetType");
      this(var1, var3);
      this.h = MapsKt.mapOf(var2);
   }

   public an(int var1, @NotNull Pair var2) {
      Intrinsics.checkParameterIsNotNull(var2, "arg");
      this(var1, var2, L.PLAY);
   }

   public static void a(int[] var0) {
      a = var0;
   }

   public static int[] a() {
      return a;
   }

   static {
      a((int[])null);
   }
}
