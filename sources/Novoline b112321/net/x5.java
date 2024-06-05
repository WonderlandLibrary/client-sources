package net;

import java.awt.Font;
import java.io.InputStream;

public class x5 {
   public static Font a(Font var0, int var1, float var2) {
      return var0.deriveFont(var1, var2);
   }

   public static Font a(Font var0, int var1) {
      return var0.deriveFont(var1);
   }

   public static String a(Font var0) {
      return var0.getFamily();
   }

   public static Font a(int var0, InputStream var1) {
      return Font.createFont(var0, var1);
   }
}
