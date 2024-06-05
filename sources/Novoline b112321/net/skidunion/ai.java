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
   d2 = {"Lnet/skidunion/ai;", "Lnet/skidunion/an;", "name", "", "data", "(Ljava/lang/String;Ljava/lang/String;)V", "client"}
)
public final class ai extends an {
   private static int[] i;

   public ai(@NotNull String var1, @NotNull String var2) {
      c();
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "data");
      super(4, (Pair[])(new Pair[]{TuplesKt.to("name", var1), TuplesKt.to("data", var2)}));
   }

   public static void b(int[] var0) {
      i = var0;
   }

   public static int[] c() {
      return i;
   }

   static {
      b(new int[2]);
   }
}
