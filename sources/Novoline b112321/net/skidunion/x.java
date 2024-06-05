package net.skidunion;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import net.skidunion.q;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"},
   d2 = {"Lnet/skidunion/x;", "Lnet/skidunion/q;", "d", "", "Lnet/skidunion/b;", "(Ljava/util/List;)V", "a", "()Ljava/util/List;", "client"}
)
public final class x extends q {
   @NotNull
   private final List d;
   private static acE[] a;

   @NotNull
   public final List b() {
      return this.d;
   }

   public x(@NotNull List var1) {
      Intrinsics.checkParameterIsNotNull(var1, "configs");
      super();
      this.d = var1;
   }

   public static void b(acE[] var0) {
      a = var0;
   }

   public static acE[] a() {
      return a;
   }

   static {
      if(a() == null) {
         b(new acE[4]);
      }

   }
}
