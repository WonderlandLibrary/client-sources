package net.skidunion;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.acE;
import net.skidunion.q;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"},
   d2 = {"Lnet/skidunion/u;", "Lnet/skidunion/q;", "a", "", "e", "", "(Ljava/lang/String;Z)V", "d", "()Z", "a", "()Ljava/lang/String;", "client"}
)
public final class u extends q {
   @NotNull
   private final String a;
   private final boolean e;
   private static acE[] d;

   @NotNull
   public final String a() {
      return this.a;
   }

   public final boolean d() {
      return this.e;
   }

   public u(@NotNull String var1, boolean var2) {
      Intrinsics.checkParameterIsNotNull(var1, "message");
      b();
      super();
      this.a = var1;
      this.e = var2;
   }

   public static void b(acE[] var0) {
      d = var0;
   }

   public static acE[] b() {
      return d;
   }

   static {
      if(b() != null) {
         b(new acE[5]);
      }

   }
}
