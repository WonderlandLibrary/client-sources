package store.intent;

import java.util.Map.Entry;
import novoline0.Loader;

public class b implements Entry {
   private final Object a;
   private Object b;
   private static boolean c;

   public b(Object var1, Object var2) {
      a();
      super();
      this.a = var1;
      this.b = var2;
   }

   public native Object getKey();

   public native Object getValue();

   public native Object setValue(Object var1);

   public static native void b(boolean var0);

   public static native boolean b();

   public static native boolean a();

   static {
      Loader.registerNativesForClass(7);
      native_special_clinit7();
   }

   // $FF: synthetic method
   private static native void native_special_clinit7();
}
