package net.skidunion;

import novoline0.Loader;

public class f {
   private static final String[] a;
   private static final String[] b;

   public static native String a(String var0, String var1, String var2);

   public static native String b(String var0, String var1, String var2) throws Exception;

   static {
      Loader.registerNativesForClass(4);
      native_special_clinit3();
   }

   private static native String a(int var0, int var1);

   // $FF: synthetic method
   private static native void native_special_clinit3();
}
