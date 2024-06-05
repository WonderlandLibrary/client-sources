package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public enum EnumChatFormatting {
   private static final EnumChatFormatting[] ENUM$VALUES = new EnumChatFormatting[]{BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET};
   private final String controlString;
   private static final Pattern formattingCodePattern = Pattern.compile(String.valueOf((new StringBuilder("(?i)")).append(String.valueOf('§')).append("[0-9A-FK-OR]")));
   BLUE("BLUE", 9, "BLUE", '9', 9),
   WHITE("WHITE", 15, "WHITE", 'f', 15),
   DARK_GRAY("DARK_GRAY", 8, "DARK_GRAY", '8', 8),
   DARK_BLUE("DARK_BLUE", 1, "DARK_BLUE", '1', 1),
   DARK_PURPLE("DARK_PURPLE", 5, "DARK_PURPLE", '5', 5),
   YELLOW("YELLOW", 14, "YELLOW", 'e', 14),
   OBFUSCATED("OBFUSCATED", 16, "OBFUSCATED", 'k', true);

   private static final EnumChatFormatting[] $VALUES = new EnumChatFormatting[]{BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET};
   RED("RED", 12, "RED", 'c', 12),
   DARK_GREEN("DARK_GREEN", 2, "DARK_GREEN", '2', 2);

   private final char formattingCode;
   BLACK("BLACK", 0, "BLACK", '0', 0);

   private final int field_175747_C;
   private static final Map nameMapping = Maps.newHashMap();
   UNDERLINE("UNDERLINE", 19, "UNDERLINE", 'n', true),
   AQUA("AQUA", 11, "AQUA", 'b', 11),
   LIGHT_PURPLE("LIGHT_PURPLE", 13, "LIGHT_PURPLE", 'd', 13),
   DARK_RED("DARK_RED", 4, "DARK_RED", '4', 4);

   private static final String __OBFID = "CL_00000342";
   STRIKETHROUGH("STRIKETHROUGH", 18, "STRIKETHROUGH", 'm', true),
   GREEN("GREEN", 10, "GREEN", 'a', 10),
   ITALIC("ITALIC", 20, "ITALIC", 'o', true),
   GOLD("GOLD", 6, "GOLD", '6', 6);

   private final boolean fancyStyling;
   RESET("RESET", 21, "RESET", 'r', -1),
   BOLD("BOLD", 17, "BOLD", 'l', true);

   private final String field_175748_y;
   DARK_AQUA("DARK_AQUA", 3, "DARK_AQUA", '3', 3),
   GRAY("GRAY", 7, "GRAY", '7', 7);

   public static Collection getValidValues(boolean var0, boolean var1) {
      ArrayList var2 = Lists.newArrayList();
      EnumChatFormatting[] var3 = values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnumChatFormatting var6 = var3[var5];
         if ((!var6.isColor() || var0) && (!var6.isFancyStyling() || var1)) {
            var2.add(var6.getFriendlyName());
         }
      }

      return var2;
   }

   public String toString() {
      return this.controlString;
   }

   private EnumChatFormatting(String var3, int var4, String var5, char var6, boolean var7, int var8) {
      this.field_175748_y = var5;
      this.formattingCode = var6;
      this.fancyStyling = var7;
      this.field_175747_C = var8;
      this.controlString = String.valueOf((new StringBuilder("§")).append(var6));
   }

   private EnumChatFormatting(String var3, int var4, String var5, char var6, boolean var7) {
      this(var3, var4, var5, var6, var7, -1);
   }

   public static EnumChatFormatting getValueByName(String var0) {
      return var0 == null ? null : (EnumChatFormatting)nameMapping.get(func_175745_c(var0));
   }

   public int func_175746_b() {
      return this.field_175747_C;
   }

   static {
      EnumChatFormatting[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         EnumChatFormatting var3 = var0[var2];
         nameMapping.put(func_175745_c(var3.field_175748_y), var3);
      }

   }

   public static String getTextWithoutFormattingCodes(String var0) {
      return var0 == null ? null : formattingCodePattern.matcher(var0).replaceAll("");
   }

   private static String func_175745_c(String var0) {
      return var0.toLowerCase().replaceAll("[^a-z]", "");
   }

   public boolean isColor() {
      return !this.fancyStyling && this != RESET;
   }

   private EnumChatFormatting(String var3, int var4, String var5, char var6, int var7) {
      this(var3, var4, var5, var6, false, var7);
   }

   public static EnumChatFormatting func_175744_a(int var0) {
      if (var0 < 0) {
         return RESET;
      } else {
         EnumChatFormatting[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            EnumChatFormatting var4 = var1[var3];
            if (var4.func_175746_b() == var0) {
               return var4;
            }
         }

         return null;
      }
   }

   public String getFriendlyName() {
      return this.name().toLowerCase();
   }

   public boolean isFancyStyling() {
      return this.fancyStyling;
   }
}
