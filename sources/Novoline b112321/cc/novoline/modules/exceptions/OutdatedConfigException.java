package cc.novoline.modules.exceptions;

public final class OutdatedConfigException extends IllegalArgumentException {
   private static int[] b;

   public OutdatedConfigException() {
   }

   public OutdatedConfigException(String var1) {
      super(var1);
   }

   public OutdatedConfigException(String var1, Throwable var2) {
      super(var1, var2);
   }

   public OutdatedConfigException(Throwable var1) {
      super(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[1]);
      }

   }
}
