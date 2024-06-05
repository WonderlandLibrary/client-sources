package net;

import java.awt.Color;

public class M5 {
   private static String b;

   public static int b(Color var0) {
      return var0.getRed();
   }

   public static int c(Color var0) {
      return var0.getGreen();
   }

   public static int g(Color var0) {
      return var0.getBlue();
   }

   public static int f(Color var0) {
      return var0.getRGB();
   }

   public static Color e(Color var0) {
      return var0.brighter();
   }

   public static float[] a(int var0, int var1, int var2, float[] var3) {
      return Color.RGBtoHSB(var0, var1, var2, var3);
   }

   public static Color a(float var0, float var1, float var2) {
      return Color.getHSBColor(var0, var1, var2);
   }

   public static int d(Color var0) {
      return var0.getAlpha();
   }

   public static Color a(Color var0) {
      return var0.darker();
   }

   public static float[] a(Color var0, float[] var1) {
      return var0.getColorComponents(var1);
   }

   public static int b(float var0, float var1, float var2) {
      return Color.HSBtoRGB(var0, var1, var2);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("dyRhtb");
      }

   }
}
