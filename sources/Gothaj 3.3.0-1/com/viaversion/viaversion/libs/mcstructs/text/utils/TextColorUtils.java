package com.viaversion.viaversion.libs.mcstructs.text.utils;

import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.Style;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;

public class TextColorUtils {
   public static ATextComponent gradient(String s, TextFormatting... colors) {
      if (colors.length == 0) {
         return new StringComponent(s);
      } else if (colors.length == 1) {
         return new StringComponent(s).setStyle(new Style().setFormatting(colors[0]));
      } else {
         ATextComponent out = new StringComponent("");
         float[] fractions = new float[colors.length];

         for (int i = 0; i < colors.length; i++) {
            fractions[i] = (float)i / (float)(colors.length - 1);
         }

         char[] chars = s.toCharArray();

         for (int i = 0; i < chars.length; i++) {
            float progress = (float)i / (float)(chars.length - 1);
            int fromI = 0;
            int toI = 0;

            for (int j = 0; j < colors.length - 1; j++) {
               if (progress >= fractions[j] && progress <= fractions[j + 1]) {
                  fromI = j;
                  toI = j + 1;
                  break;
               }
            }

            float ratio = (progress - fractions[fromI]) / (fractions[toI] - fractions[fromI]);
            int rgb = interpolate(colors[fromI], colors[toI], ratio);
            out.append(new StringComponent(String.valueOf(chars[i])).setStyle(new Style().setColor(rgb)));
         }

         return out;
      }
   }

   public static ATextComponent rainbow(String s) {
      return gradient(
         s,
         new TextFormatting(16711680),
         new TextFormatting(16776960),
         new TextFormatting(65280),
         new TextFormatting(65535),
         new TextFormatting(255),
         new TextFormatting(16711935),
         new TextFormatting(16711680)
      );
   }

   private static int interpolate(TextFormatting from, TextFormatting to, float ratio) {
      int ar = from.getRgbValue() >> 16 & 0xFF;
      int ag = from.getRgbValue() >> 8 & 0xFF;
      int ab = from.getRgbValue() & 0xFF;
      int br = to.getRgbValue() >> 16 & 0xFF;
      int bg = to.getRgbValue() >> 8 & 0xFF;
      int bb = to.getRgbValue() & 0xFF;
      int r = (int)((float)ar + (float)(br - ar) * ratio);
      int g = (int)((float)ag + (float)(bg - ag) * ratio);
      int b = (int)((float)ab + (float)(bb - ab) * ratio);
      return r << 16 | g << 8 | b;
   }
}
