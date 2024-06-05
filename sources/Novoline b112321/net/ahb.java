package net;

import java.io.InputStream;
import sun.misc.IOUtils;

public class ahb {
   public static byte[] a(InputStream var0, int var1, boolean var2) {
      return IOUtils.readFully(var0, var1, var2);
   }
}
