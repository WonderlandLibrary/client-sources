package net;

import java.util.zip.Inflater;

public class y2 {
   public static void a(Inflater var0, byte[] var1) {
      var0.setInput(var1);
   }

   public static int b(Inflater var0, byte[] var1) {
      return var0.inflate(var1);
   }

   public static void a(Inflater var0) {
      var0.reset();
   }
}
