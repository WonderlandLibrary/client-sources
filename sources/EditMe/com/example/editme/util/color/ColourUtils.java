package com.example.editme.util.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class ColourUtils {
   public static int[] toRGBAArray(int var0) {
      return new int[]{var0 >> 16 & 255, var0 >> 8 & 255, var0 & 255, var0 >> 24 & 255};
   }

   public String getColorNameFromRgb(int var1, int var2, int var3) {
      ArrayList var4 = this.initColorList();
      ColourUtils.ColorName var5 = null;
      int var6 = Integer.MAX_VALUE;
      Iterator var8 = var4.iterator();

      while(var8.hasNext()) {
         ColourUtils.ColorName var9 = (ColourUtils.ColorName)var8.next();
         int var7 = var9.computeMSE(var1, var2, var3);
         if (var7 < var6) {
            var6 = var7;
            var5 = var9;
         }
      }

      if (var5 != null) {
         return var5.getName();
      } else {
         return "No matched color name.";
      }
   }

   public static int toRGBA(double[] var0) {
      if (var0.length != 4) {
         throw new IllegalArgumentException("colors[] must have a length of 4!");
      } else {
         return toRGBA((float)var0[0], (float)var0[1], (float)var0[2], (float)var0[3]);
      }
   }

   public static final int changeAlpha(int var0, int var1) {
      var0 &= 16777215;
      return var1 << 24 | var0;
   }

   public String getColorNameFromHex(int var1) {
      int var2 = (var1 & 16711680) >> 16;
      int var3 = (var1 & '\uff00') >> 8;
      int var4 = var1 & 255;
      return this.getColorNameFromRgb(var2, var3, var4);
   }

   public static int toRGBA(float[] var0) {
      if (var0.length != 4) {
         throw new IllegalArgumentException("colors[] must have a length of 4!");
      } else {
         return toRGBA(var0[0], var0[1], var0[2], var0[3]);
      }
   }

   private ArrayList initColorList() {
      ArrayList var1 = new ArrayList();
      var1.add(new ColourUtils.ColorName(this, "AliceBlue", 240, 248, 255));
      var1.add(new ColourUtils.ColorName(this, "AntiqueWhite", 250, 235, 215));
      var1.add(new ColourUtils.ColorName(this, "Aqua", 0, 255, 255));
      var1.add(new ColourUtils.ColorName(this, "Aquamarine", 127, 255, 212));
      var1.add(new ColourUtils.ColorName(this, "Azure", 240, 255, 255));
      var1.add(new ColourUtils.ColorName(this, "Beige", 245, 245, 220));
      var1.add(new ColourUtils.ColorName(this, "Bisque", 255, 228, 196));
      var1.add(new ColourUtils.ColorName(this, "Black", 0, 0, 0));
      var1.add(new ColourUtils.ColorName(this, "BlanchedAlmond", 255, 235, 205));
      var1.add(new ColourUtils.ColorName(this, "Blue", 0, 0, 255));
      var1.add(new ColourUtils.ColorName(this, "BlueViolet", 138, 43, 226));
      var1.add(new ColourUtils.ColorName(this, "Brown", 165, 42, 42));
      var1.add(new ColourUtils.ColorName(this, "BurlyWood", 222, 184, 135));
      var1.add(new ColourUtils.ColorName(this, "CadetBlue", 95, 158, 160));
      var1.add(new ColourUtils.ColorName(this, "Chartreuse", 127, 255, 0));
      var1.add(new ColourUtils.ColorName(this, "Chocolate", 210, 105, 30));
      var1.add(new ColourUtils.ColorName(this, "Coral", 255, 127, 80));
      var1.add(new ColourUtils.ColorName(this, "CornflowerBlue", 100, 149, 237));
      var1.add(new ColourUtils.ColorName(this, "Cornsilk", 255, 248, 220));
      var1.add(new ColourUtils.ColorName(this, "Crimson", 220, 20, 60));
      var1.add(new ColourUtils.ColorName(this, "Cyan", 0, 255, 255));
      var1.add(new ColourUtils.ColorName(this, "DarkBlue", 0, 0, 139));
      var1.add(new ColourUtils.ColorName(this, "DarkCyan", 0, 139, 139));
      var1.add(new ColourUtils.ColorName(this, "DarkGoldenRod", 184, 134, 11));
      var1.add(new ColourUtils.ColorName(this, "DarkGray", 169, 169, 169));
      var1.add(new ColourUtils.ColorName(this, "DarkGreen", 0, 100, 0));
      var1.add(new ColourUtils.ColorName(this, "DarkKhaki", 189, 183, 107));
      var1.add(new ColourUtils.ColorName(this, "DarkMagenta", 139, 0, 139));
      var1.add(new ColourUtils.ColorName(this, "DarkOliveGreen", 85, 107, 47));
      var1.add(new ColourUtils.ColorName(this, "DarkOrange", 255, 140, 0));
      var1.add(new ColourUtils.ColorName(this, "DarkOrchid", 153, 50, 204));
      var1.add(new ColourUtils.ColorName(this, "DarkRed", 139, 0, 0));
      var1.add(new ColourUtils.ColorName(this, "DarkSalmon", 233, 150, 122));
      var1.add(new ColourUtils.ColorName(this, "DarkSeaGreen", 143, 188, 143));
      var1.add(new ColourUtils.ColorName(this, "DarkSlateBlue", 72, 61, 139));
      var1.add(new ColourUtils.ColorName(this, "DarkSlateGray", 47, 79, 79));
      var1.add(new ColourUtils.ColorName(this, "DarkTurquoise", 0, 206, 209));
      var1.add(new ColourUtils.ColorName(this, "DarkViolet", 148, 0, 211));
      var1.add(new ColourUtils.ColorName(this, "DeepPink", 255, 20, 147));
      var1.add(new ColourUtils.ColorName(this, "DeepSkyBlue", 0, 191, 255));
      var1.add(new ColourUtils.ColorName(this, "DimGray", 105, 105, 105));
      var1.add(new ColourUtils.ColorName(this, "DodgerBlue", 30, 144, 255));
      var1.add(new ColourUtils.ColorName(this, "FireBrick", 178, 34, 34));
      var1.add(new ColourUtils.ColorName(this, "FloralWhite", 255, 250, 240));
      var1.add(new ColourUtils.ColorName(this, "ForestGreen", 34, 139, 34));
      var1.add(new ColourUtils.ColorName(this, "Fuchsia", 255, 0, 255));
      var1.add(new ColourUtils.ColorName(this, "Gainsboro", 220, 220, 220));
      var1.add(new ColourUtils.ColorName(this, "GhostWhite", 248, 248, 255));
      var1.add(new ColourUtils.ColorName(this, "Gold", 255, 215, 0));
      var1.add(new ColourUtils.ColorName(this, "GoldenRod", 218, 165, 32));
      var1.add(new ColourUtils.ColorName(this, "Gray", 128, 128, 128));
      var1.add(new ColourUtils.ColorName(this, "Green", 0, 128, 0));
      var1.add(new ColourUtils.ColorName(this, "GreenYellow", 173, 255, 47));
      var1.add(new ColourUtils.ColorName(this, "HoneyDew", 240, 255, 240));
      var1.add(new ColourUtils.ColorName(this, "HotPink", 255, 105, 180));
      var1.add(new ColourUtils.ColorName(this, "IndianRed", 205, 92, 92));
      var1.add(new ColourUtils.ColorName(this, "Indigo", 75, 0, 130));
      var1.add(new ColourUtils.ColorName(this, "Ivory", 255, 255, 240));
      var1.add(new ColourUtils.ColorName(this, "Khaki", 240, 230, 140));
      var1.add(new ColourUtils.ColorName(this, "Lavender", 230, 230, 250));
      var1.add(new ColourUtils.ColorName(this, "LavenderBlush", 255, 240, 245));
      var1.add(new ColourUtils.ColorName(this, "LawnGreen", 124, 252, 0));
      var1.add(new ColourUtils.ColorName(this, "LemonChiffon", 255, 250, 205));
      var1.add(new ColourUtils.ColorName(this, "LightBlue", 173, 216, 230));
      var1.add(new ColourUtils.ColorName(this, "LightCoral", 240, 128, 128));
      var1.add(new ColourUtils.ColorName(this, "LightCyan", 224, 255, 255));
      var1.add(new ColourUtils.ColorName(this, "LightGoldenRodYellow", 250, 250, 210));
      var1.add(new ColourUtils.ColorName(this, "LightGray", 211, 211, 211));
      var1.add(new ColourUtils.ColorName(this, "LightGreen", 144, 238, 144));
      var1.add(new ColourUtils.ColorName(this, "LightPink", 255, 182, 193));
      var1.add(new ColourUtils.ColorName(this, "LightSalmon", 255, 160, 122));
      var1.add(new ColourUtils.ColorName(this, "LightSeaGreen", 32, 178, 170));
      var1.add(new ColourUtils.ColorName(this, "LightSkyBlue", 135, 206, 250));
      var1.add(new ColourUtils.ColorName(this, "LightSlateGray", 119, 136, 153));
      var1.add(new ColourUtils.ColorName(this, "LightSteelBlue", 176, 196, 222));
      var1.add(new ColourUtils.ColorName(this, "LightYellow", 255, 255, 224));
      var1.add(new ColourUtils.ColorName(this, "Lime", 0, 255, 0));
      var1.add(new ColourUtils.ColorName(this, "LimeGreen", 50, 205, 50));
      var1.add(new ColourUtils.ColorName(this, "Linen", 250, 240, 230));
      var1.add(new ColourUtils.ColorName(this, "Magenta", 255, 0, 255));
      var1.add(new ColourUtils.ColorName(this, "Maroon", 128, 0, 0));
      var1.add(new ColourUtils.ColorName(this, "MediumAquaMarine", 102, 205, 170));
      var1.add(new ColourUtils.ColorName(this, "MediumBlue", 0, 0, 205));
      var1.add(new ColourUtils.ColorName(this, "MediumOrchid", 186, 85, 211));
      var1.add(new ColourUtils.ColorName(this, "MediumPurple", 147, 112, 219));
      var1.add(new ColourUtils.ColorName(this, "MediumSeaGreen", 60, 179, 113));
      var1.add(new ColourUtils.ColorName(this, "MediumSlateBlue", 123, 104, 238));
      var1.add(new ColourUtils.ColorName(this, "MediumSpringGreen", 0, 250, 154));
      var1.add(new ColourUtils.ColorName(this, "MediumTurquoise", 72, 209, 204));
      var1.add(new ColourUtils.ColorName(this, "MediumVioletRed", 199, 21, 133));
      var1.add(new ColourUtils.ColorName(this, "MidnightBlue", 25, 25, 112));
      var1.add(new ColourUtils.ColorName(this, "MintCream", 245, 255, 250));
      var1.add(new ColourUtils.ColorName(this, "MistyRose", 255, 228, 225));
      var1.add(new ColourUtils.ColorName(this, "Moccasin", 255, 228, 181));
      var1.add(new ColourUtils.ColorName(this, "NavajoWhite", 255, 222, 173));
      var1.add(new ColourUtils.ColorName(this, "Navy", 0, 0, 128));
      var1.add(new ColourUtils.ColorName(this, "OldLace", 253, 245, 230));
      var1.add(new ColourUtils.ColorName(this, "Olive", 128, 128, 0));
      var1.add(new ColourUtils.ColorName(this, "OliveDrab", 107, 142, 35));
      var1.add(new ColourUtils.ColorName(this, "Orange", 255, 165, 0));
      var1.add(new ColourUtils.ColorName(this, "OrangeRed", 255, 69, 0));
      var1.add(new ColourUtils.ColorName(this, "Orchid", 218, 112, 214));
      var1.add(new ColourUtils.ColorName(this, "PaleGoldenRod", 238, 232, 170));
      var1.add(new ColourUtils.ColorName(this, "PaleGreen", 152, 251, 152));
      var1.add(new ColourUtils.ColorName(this, "PaleTurquoise", 175, 238, 238));
      var1.add(new ColourUtils.ColorName(this, "PaleVioletRed", 219, 112, 147));
      var1.add(new ColourUtils.ColorName(this, "PapayaWhip", 255, 239, 213));
      var1.add(new ColourUtils.ColorName(this, "PeachPuff", 255, 218, 185));
      var1.add(new ColourUtils.ColorName(this, "Peru", 205, 133, 63));
      var1.add(new ColourUtils.ColorName(this, "Pink", 255, 192, 203));
      var1.add(new ColourUtils.ColorName(this, "Plum", 221, 160, 221));
      var1.add(new ColourUtils.ColorName(this, "PowderBlue", 176, 224, 230));
      var1.add(new ColourUtils.ColorName(this, "Purple", 128, 0, 128));
      var1.add(new ColourUtils.ColorName(this, "Red", 255, 0, 0));
      var1.add(new ColourUtils.ColorName(this, "RosyBrown", 188, 143, 143));
      var1.add(new ColourUtils.ColorName(this, "RoyalBlue", 65, 105, 225));
      var1.add(new ColourUtils.ColorName(this, "SaddleBrown", 139, 69, 19));
      var1.add(new ColourUtils.ColorName(this, "Salmon", 250, 128, 114));
      var1.add(new ColourUtils.ColorName(this, "SandyBrown", 244, 164, 96));
      var1.add(new ColourUtils.ColorName(this, "SeaGreen", 46, 139, 87));
      var1.add(new ColourUtils.ColorName(this, "SeaShell", 255, 245, 238));
      var1.add(new ColourUtils.ColorName(this, "Sienna", 160, 82, 45));
      var1.add(new ColourUtils.ColorName(this, "Silver", 192, 192, 192));
      var1.add(new ColourUtils.ColorName(this, "SkyBlue", 135, 206, 235));
      var1.add(new ColourUtils.ColorName(this, "SlateBlue", 106, 90, 205));
      var1.add(new ColourUtils.ColorName(this, "SlateGray", 112, 128, 144));
      var1.add(new ColourUtils.ColorName(this, "Snow", 255, 250, 250));
      var1.add(new ColourUtils.ColorName(this, "SpringGreen", 0, 255, 127));
      var1.add(new ColourUtils.ColorName(this, "SteelBlue", 70, 130, 180));
      var1.add(new ColourUtils.ColorName(this, "Tan", 210, 180, 140));
      var1.add(new ColourUtils.ColorName(this, "Teal", 0, 128, 128));
      var1.add(new ColourUtils.ColorName(this, "Thistle", 216, 191, 216));
      var1.add(new ColourUtils.ColorName(this, "Tomato", 255, 99, 71));
      var1.add(new ColourUtils.ColorName(this, "Turquoise", 64, 224, 208));
      var1.add(new ColourUtils.ColorName(this, "Violet", 238, 130, 238));
      var1.add(new ColourUtils.ColorName(this, "Wheat", 245, 222, 179));
      var1.add(new ColourUtils.ColorName(this, "White", 255, 255, 255));
      var1.add(new ColourUtils.ColorName(this, "WhiteSmoke", 245, 245, 245));
      var1.add(new ColourUtils.ColorName(this, "Yellow", 255, 255, 0));
      var1.add(new ColourUtils.ColorName(this, "YellowGreen", 154, 205, 50));
      return var1;
   }

   public int colorToHex(Color var1) {
      return Integer.decode(String.valueOf((new StringBuilder()).append("0x").append(Integer.toHexString(var1.getRGB()).substring(2))));
   }

   public static int toRGBA(double var0, double var2, double var4, double var6) {
      return toRGBA((float)var0, (float)var2, (float)var4, (float)var6);
   }

   public static int toRGBA(int var0, int var1, int var2, int var3) {
      return (var0 << 16) + (var1 << 8) + (var2 << 0) + (var3 << 24);
   }

   public static int toRGBA(float var0, float var1, float var2, float var3) {
      return toRGBA((int)(var0 * 255.0F), (int)(var1 * 255.0F), (int)(var2 * 255.0F), (int)(var3 * 255.0F));
   }

   public String getColorNameFromColor(Color var1) {
      return this.getColorNameFromRgb(var1.getRed(), var1.getGreen(), var1.getBlue());
   }

   public static class Colors {
      public static final int DARK_RED = ColourUtils.toRGBA(64, 0, 0, 255);
      public static final int RAINBOW = Integer.MIN_VALUE;
      public static final int GREEN = ColourUtils.toRGBA(0, 255, 0, 255);
      public static final int ORANGE = ColourUtils.toRGBA(255, 128, 0, 255);
      public static final int RED = ColourUtils.toRGBA(255, 0, 0, 255);
      public static final int YELLOW = ColourUtils.toRGBA(255, 255, 0, 255);
      public static final int PURPLE = ColourUtils.toRGBA(163, 73, 163, 255);
      public static final int WHITE = ColourUtils.toRGBA(255, 255, 255, 255);
      public static final int BLUE = ColourUtils.toRGBA(0, 0, 255, 255);
      public static final int GRAY = ColourUtils.toRGBA(127, 127, 127, 255);
      public static final int BLACK = ColourUtils.toRGBA(0, 0, 0, 255);
   }

   public class ColorName {
      public String name;
      public int b;
      public int g;
      final ColourUtils this$0;
      public int r;

      public int computeMSE(int var1, int var2, int var3) {
         return ((var1 - this.r) * (var1 - this.r) + (var2 - this.g) * (var2 - this.g) + (var3 - this.b) * (var3 - this.b)) / 3;
      }

      public String getName() {
         return this.name;
      }

      public ColorName(ColourUtils var1, String var2, int var3, int var4, int var5) {
         this.this$0 = var1;
         this.r = var3;
         this.g = var4;
         this.b = var5;
         this.name = var2;
      }

      public int getG() {
         return this.g;
      }

      public int getB() {
         return this.b;
      }

      public int getR() {
         return this.r;
      }
   }
}
