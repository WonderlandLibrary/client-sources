package net;

import java.io.FileWriter;
import java.io.Writer;

public class aic {
   public static Writer a(FileWriter var0, CharSequence var1) {
      return var0.append(var1);
   }

   public static void a(FileWriter var0) {
      var0.close();
   }

   public static void a(FileWriter var0, String var1) {
      var0.write(var1);
   }

   public static void b(FileWriter var0) {
      var0.flush();
   }
}
