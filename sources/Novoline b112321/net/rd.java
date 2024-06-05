package net;

import java.io.OutputStream;

public class rd {
   public static void b(OutputStream var0) {
      var0.flush();
   }

   public static void a(OutputStream var0, byte[] var1) {
      var0.write(var1);
   }

   public static void a(OutputStream var0) {
      var0.close();
   }

   public static void a(OutputStream var0, byte[] var1, int var2, int var3) {
      var0.write(var1, var2, var3);
   }
}
