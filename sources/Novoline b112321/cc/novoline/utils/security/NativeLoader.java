package cc.novoline.utils.security;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.ProviderNotFoundException;
import java.util.Random;

public class NativeLoader {
   private static final int MIN_PREFIX_LENGTH = 3;
   public static final String NATIVE_FOLDER_PATH_PREFIX = "novoline-temp";
   private static File temporaryDir;
   // $FF: synthetic field
   static final boolean $assertionsDisabled = !NativeLoader.class.desiredAssertionStatus();

   public static void loadLibraryFromJar(String param0) throws IOException {
      // $FF: Couldn't be decompiled
   }

   private static File appendNonceToFile(File file) {
      return new File(file.getParent(), file.getName().substring(0, file.getName().lastIndexOf(46)) + "-" + (new Random()).nextInt() + file.getName().substring(file.getName().lastIndexOf(46)));
   }

   private static boolean isPosixCompliant() {
      try {
         return FileSystems.getDefault().supportedFileAttributeViews().contains("posix");
      } catch (ProviderNotFoundException | SecurityException | FileSystemNotFoundException var1) {
         return false;
      }
   }

   private static File createTempDirectory() {
      String tempDir = System.getProperty("java.io.tmpdir");
      File generatedDir = new File(tempDir, "novoline-temp");
      generatedDir.mkdir();
      return generatedDir;
   }
}
