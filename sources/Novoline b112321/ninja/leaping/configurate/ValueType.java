package ninja.leaping.configurate;

import net.acE;

public enum ValueType {
   SCALAR,
   MAP,
   LIST,
   NULL;

   private static acE[] b;

   public boolean canHaveChildren() {
      acE[] var1 = b();
      return this == MAP || this == LIST;
   }

   static {
      b(new acE[4]);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }
}
