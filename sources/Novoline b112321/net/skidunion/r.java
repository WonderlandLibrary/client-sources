package net.skidunion;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.F;
import net.skidunion.q;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/skidunion/r;", "Lnet/skidunion/q;", "a", "Lnet/skidunion/F;", "(Lnet/skidunion/F;)V", "a", "()Lnet/skidunion/F;", "client"}
)
public final class r extends q {
   @NotNull
   private final F a;

   @NotNull
   public final F a() {
      return this.a;
   }

   public r(@NotNull F var1) {
      Intrinsics.checkParameterIsNotNull(var1, "entity");
      super();
      this.a = var1;
   }
}
