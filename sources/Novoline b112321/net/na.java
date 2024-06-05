package net;

public class na extends Exception {
   private static String[] b;

   public na() {
   }

   public na(String var1) {
      super(var1);
   }

   public na(String var1, Throwable var2) {
      super(var1, var2);
   }

   public na(Throwable var1) {
      super(var1);
   }

   public na(String var1, Throwable var2, boolean var3, boolean var4) {
      b();
      super(var1, var2, var3, var4);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[4]);
      }

   }
}
