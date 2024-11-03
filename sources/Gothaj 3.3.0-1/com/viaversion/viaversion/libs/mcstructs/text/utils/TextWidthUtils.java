package com.viaversion.viaversion.libs.mcstructs.text.utils;

import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public class TextWidthUtils {
   private static float[] charWidths = null;

   private static void loadCharWidths() {
      if (charWidths == null) {
         InputStream is = TextUtils.class.getResourceAsStream("/mcstructs/text/charwidths.bin");
         if (is == null) {
            throw new IllegalStateException("Could not find charwidths.bin");
         }

         try (GZIPInputStream gis = new GZIPInputStream(is)) {
            charWidths = new float[gis.read() << 24 | gis.read() << 16 | gis.read() << 8 | gis.read()];

            for (int i = 0; i < charWidths.length; i++) {
               charWidths[i] = (float)gis.read();
            }
         } catch (IOException var14) {
            throw new RuntimeException("Failed to read char widths", var14);
         }
      }
   }

   public static float[] getCharWidths() {
      loadCharWidths();
      return charWidths;
   }

   public static float getCharWidth(char c, float boldOffset, boolean bold) {
      loadCharWidths();
      return c > charWidths.length ? 0.0F : charWidths[c] + (bold ? boldOffset : 0.0F);
   }

   public static float getComponentWidth(ATextComponent component) {
      loadCharWidths();
      return getComponentWidth(component, charWidths, 1.0F);
   }

   public static float getComponentWidth(ATextComponent component, float[] widths) {
      return getComponentWidth(component, widths, 1.0F);
   }

   public static float getComponentWidth(ATextComponent component, float[] widths, float boldOffset) {
      float[] width = new float[]{0.0F};
      component.forEach(comp -> {
         char[] chars = comp.asSingleString().toCharArray();

         for (char c : chars) {
            width[0] += c >= widths.length ? 0.0F : widths[c];
         }

         if (comp.getStyle().isBold()) {
            width[0] += boldOffset * (float)chars.length;
         }
      });
      return width[0];
   }
}
