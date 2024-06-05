package net.skidunion;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.an;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005¨\u0006\u0006"},
   d2 = {"Lnet/skidunion/aE;", "Lnet/skidunion/an;", "aesKey", "", "token", "(Ljava/lang/String;Ljava/lang/String;)V", "client"}
)
public final class aE extends an {
   private static String[] i;

   public aE(@NotNull String var1, @NotNull String var2) {
      a();
      Intrinsics.checkParameterIsNotNull(var1, "aesKey");
      Intrinsics.checkParameterIsNotNull(var2, "token");
      super(1, (Pair[])(new Pair[]{TuplesKt.to("aesKey", var1), TuplesKt.to("token", var2)}));
   }

   public static void b(String[] var0) {
      i = var0;
   }

   public static String[] a() {
      return i;
   }

   static {
      b(new String[5]);
   }
}
