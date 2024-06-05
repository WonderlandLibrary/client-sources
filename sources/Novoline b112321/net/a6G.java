package net;

import java.util.zip.Deflater;

public class a6G {
   public static void d(Deflater var0) {
      var0.reset();
   }

   public static void b(Deflater var0, byte[] var1) {
      var0.setInput(var1);
   }

   public static void a(Deflater var0) {
      var0.finish();
   }

   public static int a(Deflater var0, byte[] var1) {
      return var0.deflate(var1);
   }

   public static void a(Deflater var0, byte[] var1, int var2, int var3) {
      var0.setInput(var1, var2, var3);
   }

   public static void c(Deflater var0) {
      var0.end();
   }

   public static boolean b(Deflater var0) {
      return var0.finished();
   }
}
