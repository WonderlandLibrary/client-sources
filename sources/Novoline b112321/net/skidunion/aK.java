package net.skidunion;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import net.skidunion.aV;
import net.skidunion.an;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/skidunion/aK;", "Lnet/skidunion/an;", "i", "", "(Ljava/lang/String;)V", "a", "()Ljava/lang/String;", "client"}
)
public final class aK extends an {
   @NotNull
   private final String i;

   @NotNull
   public final String a() {
      return this.i;
   }

   public aK(@NotNull String var1) {
      aV.a();
      Intrinsics.checkParameterIsNotNull(var1, "message");
      super(11, (Pair)TuplesKt.to("message", var1));
      this.i = var1;
      if(acE.b() == null) {
         aV.b(new acE[5]);
      }

   }
}
