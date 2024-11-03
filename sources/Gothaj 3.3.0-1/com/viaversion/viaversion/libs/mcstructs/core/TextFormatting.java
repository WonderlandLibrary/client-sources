package com.viaversion.viaversion.libs.mcstructs.core;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;

public class TextFormatting {
   public static final Map<String, TextFormatting> ALL = new LinkedHashMap<>();
   public static final Map<String, TextFormatting> COLORS = new LinkedHashMap<>();
   public static final Map<String, TextFormatting> FORMATTINGS = new LinkedHashMap<>();
   public static final char COLOR_CHAR = '§';
   public static final TextFormatting BLACK = new TextFormatting("black", '0', 0);
   public static final TextFormatting DARK_BLUE = new TextFormatting("dark_blue", '1', 170);
   public static final TextFormatting DARK_GREEN = new TextFormatting("dark_green", '2', 43520);
   public static final TextFormatting DARK_AQUA = new TextFormatting("dark_aqua", '3', 43690);
   public static final TextFormatting DARK_RED = new TextFormatting("dark_red", '4', 11141120);
   public static final TextFormatting DARK_PURPLE = new TextFormatting("dark_purple", '5', 11141290);
   public static final TextFormatting GOLD = new TextFormatting("gold", '6', 16755200);
   public static final TextFormatting GRAY = new TextFormatting("gray", '7', 11184810);
   public static final TextFormatting DARK_GRAY = new TextFormatting("dark_gray", '8', 5592405);
   public static final TextFormatting BLUE = new TextFormatting("blue", '9', 5592575);
   public static final TextFormatting GREEN = new TextFormatting("green", 'a', 5635925);
   public static final TextFormatting AQUA = new TextFormatting("aqua", 'b', 5636095);
   public static final TextFormatting RED = new TextFormatting("red", 'c', 16733525);
   public static final TextFormatting LIGHT_PURPLE = new TextFormatting("light_purple", 'd', 16733695);
   public static final TextFormatting YELLOW = new TextFormatting("yellow", 'e', 16777045);
   public static final TextFormatting WHITE = new TextFormatting("white", 'f', 16777215);
   public static final TextFormatting OBFUSCATED = new TextFormatting("obfuscated", 'k');
   public static final TextFormatting BOLD = new TextFormatting("bold", 'l');
   public static final TextFormatting STRIKETHROUGH = new TextFormatting("strikethrough", 'm');
   public static final TextFormatting UNDERLINE = new TextFormatting("underline", 'n');
   public static final TextFormatting ITALIC = new TextFormatting("italic", 'o');
   public static final TextFormatting RESET = new TextFormatting("reset", 'r');
   private final TextFormatting.Type type;
   private final int ordinal;
   private final String name;
   private final char code;
   private final int rgbValue;

   @Nullable
   public static TextFormatting getByOrdinal(int ordinal) {
      return ALL.values().stream().filter(formatting -> formatting.ordinal == ordinal).findFirst().orElse(null);
   }

   @Nullable
   public static TextFormatting getByName(String name) {
      return ALL.get(name.toLowerCase());
   }

   @Nullable
   public static TextFormatting getByCode(char code) {
      for (TextFormatting formatting : ALL.values()) {
         if (formatting.getCode() == code) {
            return formatting;
         }
      }

      return null;
   }

   @Nullable
   public static TextFormatting parse(String s) {
      if (s.startsWith("#")) {
         try {
            return new TextFormatting(Integer.parseInt(s.substring(1), 16));
         } catch (NumberFormatException var2) {
            return null;
         }
      } else {
         return getByName(s);
      }
   }

   public static TextFormatting getClosestFormattingColor(int rgb) {
      int r = rgb >> 16 & 0xFF;
      int g = rgb >> 8 & 0xFF;
      int b = rgb & 0xFF;
      TextFormatting closest = null;
      int closestDistance = Integer.MAX_VALUE;

      for (TextFormatting color : COLORS.values()) {
         int colorR = color.getRgbValue() >> 16 & 0xFF;
         int colorG = color.getRgbValue() >> 8 & 0xFF;
         int colorB = color.getRgbValue() & 0xFF;
         int distance = (r - colorR) * (r - colorR) + (g - colorG) * (g - colorG) + (b - colorB) * (b - colorB);
         if (distance < closestDistance) {
            closest = color;
            closestDistance = distance;
         }
      }

      return closest;
   }

   private TextFormatting(String name, char code, int rgbValue) {
      this.type = TextFormatting.Type.COLOR;
      this.ordinal = ALL.size();
      this.name = name;
      this.code = code;
      this.rgbValue = rgbValue;
      ALL.put(name, this);
      COLORS.put(name, this);
   }

   private TextFormatting(String name, char code) {
      this.type = TextFormatting.Type.FORMATTING;
      this.ordinal = ALL.size();
      this.name = name;
      this.code = code;
      this.rgbValue = -1;
      ALL.put(name, this);
      FORMATTINGS.put(name, this);
   }

   public TextFormatting(int rgbValue) {
      this.type = TextFormatting.Type.RGB;
      this.ordinal = -1;
      this.name = "RGB_COLOR";
      this.code = 0;
      this.rgbValue = rgbValue & 16777215;
   }

   public boolean isColor() {
      return TextFormatting.Type.COLOR.equals(this.type) || TextFormatting.Type.RGB.equals(this.type);
   }

   public boolean isFormattingColor() {
      return TextFormatting.Type.COLOR.equals(this.type);
   }

   public boolean isRGBColor() {
      return TextFormatting.Type.RGB.equals(this.type);
   }

   public boolean isFormatting() {
      return TextFormatting.Type.FORMATTING.equals(this.type);
   }

   public int getOrdinal() {
      return this.ordinal;
   }

   public String getName() {
      return this.name;
   }

   public char getCode() {
      return this.code;
   }

   public int getRgbValue() {
      return this.rgbValue;
   }

   public String toLegacy() {
      return String.valueOf('§') + this.code;
   }

   public String serialize() {
      return TextFormatting.Type.RGB.equals(this.type) ? "#" + String.format("%06X", this.rgbValue) : this.name;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         TextFormatting that = (TextFormatting)o;
         return this.code == that.code && this.rgbValue == that.rgbValue && this.type == that.type && Objects.equals(this.name, that.name);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.type, this.name, this.code, this.rgbValue);
   }

   @Override
   public String toString() {
      return "TextFormatting{type=" + this.type + ", name='" + this.name + '\'' + ", code=" + this.code + ", rgbValue=" + this.rgbValue + "}";
   }

   private static enum Type {
      COLOR,
      FORMATTING,
      RGB;
   }
}
