package net;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.ProviderNotFoundException;
import java.util.Random;

public class app {
   private static final int e = 3;
   public static final String d = "novoline-temp";
   private static File a;
   // $FF: synthetic field
   static final boolean c = !app.class.desiredAssertionStatus();
   private static int[] b;

   public static void a(String param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static File a(File var0) {
      return new File(var0.getParent(), var0.getName().substring(0, var0.getName().lastIndexOf(46)) + "-" + (new Random()).nextInt() + var0.getName().substring(var0.getName().lastIndexOf(46)));
   }

   private static boolean b() {
      try {
         return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
      } catch (ProviderNotFoundException | SecurityException | FileSystemNotFoundException var1) {
         return false;
      }
   }

   private static File a() {
      String var0 = System.getProperty("java.io.tmpdir");
      File var1 = new File(var0, "novoline-temp");
      var1.mkdir();
      return var1;
   }

   static {
      b((int[])null);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] c() {
      return b;
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
