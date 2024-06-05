package net.skidunion;

import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.W;
import net.skidunion.ac;
import net.skidunion.an;
import net.skidunion.aq;
import net.skidunion.o;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J(\u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\r2\u000e\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\rH\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0003"},
   d2 = {"Lnet/skidunion/g;", "Lnet/skidunion/W;", "", "a", "Lnet/skidunion/o;", "b", "Lnet/skidunion/an;", "(Lnet/skidunion/o;Lnet/skidunion/an;)V", "a", "()Lnet/skidunion/o;", "a", "", "successCallback", "Ljava/util/function/Consumer;", "failureCallback", ""}
)
public final class g implements W {
   @NotNull
   private final o a;
   private final an b;

   public void a(@Nullable Consumer var1, @Nullable Consumer var2) throws IllegalStateException {
      String var3 = ac.b();
      if(var1 == null && var2 == null) {
         this.a.a(this.b);
      } else {
         throw (Throwable)(new IllegalStateException("Callbacks are unavailable in a non-receiving request"));
      }
   }

   @NotNull
   public final o a() {
      return this.a;
   }

   public g(@NotNull o var1, @NotNull an var2) {
      Intrinsics.checkParameterIsNotNull(var1, "client");
      Intrinsics.checkParameterIsNotNull(var2, "sending");
      super();
      this.a = var1;
      this.b = var2;
   }

   public void a() {
      aq.a(this);
   }

   public void a(@Nullable Consumer var1) {
      aq.a(this, var1);
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
