package net.skidunion;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
   d2 = {"Lnet/skidunion/q;", "", "()V", "c", "", "b", "()Z", "c", "(Z)V", "client"}
)
public class q {
   private boolean c;
   private static boolean b;

   public final boolean b() {
      return this.c;
   }

   public final void c(boolean var1) {
      this.c = var1;
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean c() {
      return b;
   }

   public static boolean a() {
      boolean var0 = c();
      return true;
   }

   static {
      if(c()) {
         b(true);
      }

   }
}
