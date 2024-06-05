package novoline0;

import net.Main;

public class Bootstrap {
   public static native void registerNativesForClass(int var0);

   public static void main(String[] var0) {
      cc.novoline.utils.security.NativeLoader.loadLibraryFromJar("/natives/novo-native.dll");
      Main.main(var0);
   }
}
