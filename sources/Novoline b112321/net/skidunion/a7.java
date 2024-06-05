package net.skidunion;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.T;
import net.skidunion.an;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"},
   d2 = {"Lnet/skidunion/a7;", "Lnet/skidunion/an;", "serverEntity", "Lnet/skidunion/T;", "(Lnet/skidunion/T;)V", "client"}
)
public final class a7 extends an {
   private static boolean i;

   public a7(@NotNull T var1) {
      Intrinsics.checkParameterIsNotNull(var1, "serverEntity");
      super(2, (Pair)TuplesKt.to("data", var1));
   }

   public static void b(boolean var0) {
      i = var0;
   }

   public static boolean c() {
      return i;
   }

   public static boolean a() {
      boolean var0 = c();
      return true;
   }

   static {
      b(false);
   }
}
