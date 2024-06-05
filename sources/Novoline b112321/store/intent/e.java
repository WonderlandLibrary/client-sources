package store.intent;

import com.google.gson.JsonSyntaxException;
import novoline0.Loader;
import store.intent.c;

public class e implements store.intent.a {
   static final String g;
   static final String e;
   public static final c f;
   public static final c d;
   private static final String[] h;
   private static final String[] i;

   public native c a(String var1);

   public native c a(String var1, String var2);

   static {
      Loader.registerNativesForClass(9);
      native_special_clinit3();
   }

   private static native JsonSyntaxException a(JsonSyntaxException var0);

   private static native String a(int var0, int var1);

   // $FF: synthetic method
   private static native void native_special_clinit3();
}
