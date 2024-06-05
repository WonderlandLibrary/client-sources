package net.skidunion;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.q;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/skidunion/z;", "Lnet/skidunion/q;", "a", "", "(Ljava/lang/Throwable;)V", "a", "()Ljava/lang/Throwable;", "client"}
)
public final class z extends q {
   @NotNull
   private final Throwable a;

   @NotNull
   public final Throwable a() {
      return this.a;
   }

   public z(@NotNull Throwable var1) {
      Intrinsics.checkParameterIsNotNull(var1, "cause");
      super();
      this.a = var1;
   }
}
