package net;

import cc.novoline.utils.fonts.api.FontRenderer;

public class io {
   private static int b;

   public static float a(FontRenderer var0, CharSequence var1, double var2, double var4, int var6, boolean var7) {
      return var0.drawString(var1, var2, var4, var6, var7);
   }

   public static int a(FontRenderer var0) {
      return var0.getHeight();
   }

   public static float a(FontRenderer var0, CharSequence var1, float var2, float var3, int var4) {
      return var0.drawCenteredString(var1, var2, var3, var4);
   }

   public static int a(FontRenderer var0, CharSequence var1) {
      return var0.stringWidth(var1);
   }

   public static float b(FontRenderer var0, CharSequence var1, float var2, float var3, int var4) {
      return var0.drawString(var1, var2, var3, var4);
   }

   public static String a(FontRenderer var0, CharSequence var1, int var2, boolean var3) {
      return var0.trimStringToWidth(var1, var2, var3);
   }

   public static String a(FontRenderer var0, CharSequence var1, int var2) {
      return var0.trimStringToWidth(var1, var2);
   }

   public static float a(FontRenderer var0, CharSequence var1, float var2, float var3, int var4, boolean var5) {
      return var0.drawCenteredString(var1, var2, var3, var4, var5);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 1;
   }

   static {
      if(c() == 0) {
         b(40);
      }

   }
}
