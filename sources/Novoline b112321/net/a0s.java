package net;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class a0s {
   public static boolean a(File var0) {
      return FileUtils.deleteQuietly(var0);
   }

   public static Collection a(File var0, IOFileFilter var1, IOFileFilter var2) {
      return FileUtils.listFiles(var0, var1, var2);
   }

   public static void a(InputStream var0, File var1) {
      FileUtils.copyInputStreamToFile(var0, var1);
   }

   public static String b(File var0) {
      return FileUtils.readFileToString(var0);
   }

   public static void a(File var0, String var1) {
      FileUtils.writeStringToFile(var0, var1);
   }
}
