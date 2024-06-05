package store.intent;

public class API {
   private static boolean b;

   public static native String getHost();

   public static native String getHWID();

   public static native void initialize(String var0);

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!b()) {
         b(true);
      }

   }
}
