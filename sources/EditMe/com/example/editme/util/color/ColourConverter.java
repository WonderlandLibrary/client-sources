package com.example.editme.util.color;

public class ColourConverter {
   public static float toF(double var0) {
      return (float)(var0 / 255.0D);
   }

   public static float toF(int var0) {
      return (float)var0 / 255.0F;
   }

   public static int rgbToInt(int var0, int var1, int var2) {
      return var0 << 16 | var1 << 8 | var2;
   }

   public static int rgbToInt(int var0, int var1, int var2, int var3) {
      return var0 << 16 | var1 << 8 | var2 | var3 << 24;
   }
}
