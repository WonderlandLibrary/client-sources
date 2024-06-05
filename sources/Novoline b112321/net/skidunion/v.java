package net.skidunion;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.q;
import net.skidunion.x;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"},
   d2 = {"Lnet/skidunion/v;", "Lnet/skidunion/q;", "a", "", "d", "(Ljava/lang/String;Ljava/lang/String;)V", "a", "()Ljava/lang/String;", "b", "client"}
)
public final class v extends q {
   @NotNull
   private final String a;
   @NotNull
   private final String d;

   @NotNull
   public final String b() {
      return this.a;
   }

   @NotNull
   public final String a() {
      return this.d;
   }

   public v(@NotNull String var1, @NotNull String var2) {
      Intrinsics.checkParameterIsNotNull(var1, "name");
      Intrinsics.checkParameterIsNotNull(var2, "data");
      super();
      x.a();
      this.a = var1;
      this.d = var2;
   }
}
