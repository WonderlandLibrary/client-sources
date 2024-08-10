package cc.slack.ui.altmanager.utils;

import java.net.URI;

@SuppressWarnings("unused")
public class SystemUtils {
  public static void openWebLink(URI url) {
    try {
      Class<?> desktop = Class.forName("java.awt.Desktop");
      Object object = desktop.getMethod("getDesktop", new Class[0]).invoke(null);
      desktop.getMethod("browse", new Class[]{URI.class}).invoke(object, url);
    } catch (Throwable throwable) {
      System.err.println(throwable.getCause().getMessage());
    }
  }
}
