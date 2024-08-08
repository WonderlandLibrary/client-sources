package com.example.editme.util.color;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.util.text.TextFormatting;

public class ColorTextFormatting {
   public static Map colourEnumMap = new HashMap() {
      {
         this.put(TextFormatting.BLACK, ColorTextFormatting.ColourEnum.BLACK);
         this.put(TextFormatting.DARK_BLUE, ColorTextFormatting.ColourEnum.DARK_BLUE);
         this.put(TextFormatting.DARK_GREEN, ColorTextFormatting.ColourEnum.DARK_GREEN);
         this.put(TextFormatting.DARK_AQUA, ColorTextFormatting.ColourEnum.DARK_AQUA);
         this.put(TextFormatting.DARK_RED, ColorTextFormatting.ColourEnum.DARK_RED);
         this.put(TextFormatting.DARK_PURPLE, ColorTextFormatting.ColourEnum.DARK_PURPLE);
         this.put(TextFormatting.GOLD, ColorTextFormatting.ColourEnum.GOLD);
         this.put(TextFormatting.GRAY, ColorTextFormatting.ColourEnum.GRAY);
         this.put(TextFormatting.DARK_GRAY, ColorTextFormatting.ColourEnum.DARK_GRAY);
         this.put(TextFormatting.BLUE, ColorTextFormatting.ColourEnum.BLUE);
         this.put(TextFormatting.GREEN, ColorTextFormatting.ColourEnum.GREEN);
         this.put(TextFormatting.AQUA, ColorTextFormatting.ColourEnum.AQUA);
         this.put(TextFormatting.RED, ColorTextFormatting.ColourEnum.RED);
         this.put(TextFormatting.LIGHT_PURPLE, ColorTextFormatting.ColourEnum.LIGHT_PURPLE);
         this.put(TextFormatting.YELLOW, ColorTextFormatting.ColourEnum.YELLOW);
         this.put(TextFormatting.WHITE, ColorTextFormatting.ColourEnum.WHITE);
      }
   };
   public static Map toTextMap = new HashMap() {
      {
         this.put(ColorTextFormatting.ColourCode.BLACK, TextFormatting.BLACK);
         this.put(ColorTextFormatting.ColourCode.DARK_BLUE, TextFormatting.DARK_BLUE);
         this.put(ColorTextFormatting.ColourCode.DARK_GREEN, TextFormatting.DARK_GREEN);
         this.put(ColorTextFormatting.ColourCode.DARK_AQUA, TextFormatting.DARK_AQUA);
         this.put(ColorTextFormatting.ColourCode.DARK_RED, TextFormatting.DARK_RED);
         this.put(ColorTextFormatting.ColourCode.DARK_PURPLE, TextFormatting.DARK_PURPLE);
         this.put(ColorTextFormatting.ColourCode.GOLD, TextFormatting.GOLD);
         this.put(ColorTextFormatting.ColourCode.GRAY, TextFormatting.GRAY);
         this.put(ColorTextFormatting.ColourCode.DARK_GRAY, TextFormatting.DARK_GRAY);
         this.put(ColorTextFormatting.ColourCode.BLUE, TextFormatting.BLUE);
         this.put(ColorTextFormatting.ColourCode.GREEN, TextFormatting.GREEN);
         this.put(ColorTextFormatting.ColourCode.AQUA, TextFormatting.AQUA);
         this.put(ColorTextFormatting.ColourCode.RED, TextFormatting.RED);
         this.put(ColorTextFormatting.ColourCode.LIGHT_PURPLE, TextFormatting.LIGHT_PURPLE);
         this.put(ColorTextFormatting.ColourCode.YELLOW, TextFormatting.YELLOW);
         this.put(ColorTextFormatting.ColourCode.WHITE, TextFormatting.WHITE);
      }
   };

   public static enum ColourEnum {
      private static final ColorTextFormatting.ColourEnum[] $VALUES = new ColorTextFormatting.ColourEnum[]{BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE};
      LIGHT_PURPLE(new Color(255, 85, 255)),
      RED(new Color(255, 85, 85)),
      GOLD(new Color(255, 170, 0)),
      DARK_PURPLE(new Color(170, 0, 170)),
      DARK_GREEN(new Color(0, 170, 0));

      public Color colorLocal;
      WHITE(new Color(255, 255, 255)),
      DARK_AQUA(new Color(0, 170, 170)),
      GRAY(new Color(170, 170, 170)),
      DARK_RED(new Color(170, 0, 0)),
      BLACK(new Color(0, 0, 0)),
      GREEN(new Color(85, 255, 85)),
      AQUA(new Color(85, 225, 225)),
      DARK_BLUE(new Color(0, 0, 170)),
      BLUE(new Color(85, 85, 255)),
      YELLOW(new Color(255, 255, 85)),
      DARK_GRAY(new Color(85, 85, 85));

      private ColourEnum(Color var3) {
         this.colorLocal = var3;
      }
   }

   public static enum ColourCode {
      AQUA,
      DARK_RED,
      DARK_GRAY,
      GREEN;

      private static final ColorTextFormatting.ColourCode[] $VALUES = new ColorTextFormatting.ColourCode[]{BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE};
      DARK_PURPLE,
      DARK_GREEN,
      BLUE,
      RED,
      YELLOW,
      WHITE,
      GRAY,
      LIGHT_PURPLE,
      DARK_AQUA,
      BLACK,
      GOLD,
      DARK_BLUE;
   }
}
