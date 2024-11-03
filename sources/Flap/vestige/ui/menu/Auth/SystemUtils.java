package vestige.ui.menu.Auth;

import java.net.URI;

public class SystemUtils {
   public static void openWebLink(URI url) {
      try {
         Class<?> desktop = Class.forName("java.awt.Desktop");
         Object object = desktop.getMethod("getDesktop").invoke((Object)null);
         desktop.getMethod("browse", URI.class).invoke(object, url);
      } catch (Throwable var3) {
         System.err.println(var3.getCause().getMessage());
      }

   }
}
