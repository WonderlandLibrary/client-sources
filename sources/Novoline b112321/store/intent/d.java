package store.intent;

import java.io.IOException;
import novoline0.Loader;
import store.intent.b;

public class d implements store.intent.a {
   private static String[] d;
   private static final String[] e;
   private static final String[] f;

   public static native String a(String var0) throws IOException;

   @SafeVarargs
   public static native String a(String var0, b... var1) throws IOException;

   public static native void b(String[] var0);

   public static native String[] b();

   private static native Throwable a(Throwable var0);

   static {
      Loader.registerNativesForClass(8);
      native_special_clinit6();
   }

   private static native String a(int var0, int var1);

   // $FF: synthetic method
   private static native void native_special_clinit6();
}
