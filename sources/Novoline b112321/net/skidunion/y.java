package net.skidunion;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.skidunion.q;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"},
   d2 = {"Lnet/skidunion/y;", "Lnet/skidunion/q;", "e", "", "d", "", "(ILjava/lang/String;)V", "a", "()I", "c", "()Ljava/lang/String;", "client"}
)
public final class y extends q {
   private final int e;
   @NotNull
   private final String d;
   private static int[] a;

   public final int a() {
      return this.e;
   }

   @NotNull
   public final String c() {
      return this.d;
   }

   public y(int var1, @NotNull String var2) {
      Intrinsics.checkParameterIsNotNull(var2, "message");
      b();
      super();
      this.e = var1;
      this.d = var2;
   }

   public static void b(int[] var0) {
      a = var0;
   }

   public static int[] b() {
      return a;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
