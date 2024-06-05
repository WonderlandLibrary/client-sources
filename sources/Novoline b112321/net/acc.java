package net;

import java.io.FileOutputStream;

public class acc {
   public static void b(FileOutputStream var0) {
      var0.flush();
   }

   public static void a(FileOutputStream var0) {
      var0.close();
   }

   public static void a(FileOutputStream var0, byte[] var1) {
      var0.write(var1);
   }
}
